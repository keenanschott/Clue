package clueGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Board
 * A part of Clue Init to set up and populate a game board.
 * @author Keenan Schott
 * @author Finn Burns
 */
public class Board {
    private BoardCell[][] grid; // grid of cells
    private int numRows;
    private int numColumns;
    private String layoutConfigFile; // layout file
    private String setupConfigFile; // setup file
    private Map<Character,Room> roomMap; // maps characters to rooms
    private static Board theInstance = new Board(); // Singleton Pattern
    // these are not in the UML on canvas, but still needed
    private Set<BoardCell> targets; // all valid cells to move to
	private Set<BoardCell> visited; // visited cells

	/**
     * Constructor (see Singleton Pattern).
     */
    private Board() {
        super();
    }
    
	/**
     * Constructor supplement (see Singleton Pattern).
     */
    public static Board getInstance() {
        return theInstance;
    }

	/**
     * Set up the game board with new board cells. Then, create an adjacency list for every cell.
     */
    public void initialize() {
		targets = new HashSet<BoardCell>(); // allocate space for our sets and map
    	visited = new HashSet<BoardCell>();
		roomMap = new HashMap<>();
		try {
			loadSetupConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		
		/*
		1. Walkways only connect to adjacent walkways

		2. Walkways with doors will also connect to the room center the door points to.

		3. The cell that represents the Room (i.e. connects to walkway) is the cell with a second character of ‘*’ 
		(no other cells in a room should have adjacencies).

		4.Room center cells ONLY connect to 1) door walkways that enter the room and 2) 
		another room center cell if there is a secret passage connecting.

		*/
    	for (int i = 0; i < numRows; i++) { // create an adjacency list for every cell
    		for (int j = 0; j < numColumns; j++) {
				if (grid[i][j].getInitial() == 'W') { // handling conditions 1 and 2
					// check if walkway in each direction 
						// same code as adjacencyDefault but do && grid @ whatever == 'W'
					if (grid[i][j].getDoorDirection() != DoorDirection.NONE) {
						// add adjacency to room Center cell of Room that door is on
							// IDEA SO FAR: use door direction of the cell
								// .getRoom().getCenterCell()
					}
				}
				if (grid[i][j].getCenter() == true) { // handling condition 3
					//grid[i][j].addAdjacency(WALKWAY ENTERING GRID[i][j];
					if (grid[i][j].getSecretPassage() != '\u0000') { // checking if its secret passage (\u0000 is null val)
						// set adjacency to room center cell connected to grid[i][j]
					}
				}
				//adjacencyDefault(i, j); // is this a good idea? the conditions kinda alter what we populate adjacency list with
    		}
    	}
    }

	public void adjacencyDefault(int i, int j) {
		if (i != 0) { // if not at top of board
			grid[i][j].addAdjacency(grid[i - 1][j]);
		}
		if (i != numRows - 1) { // if not at bottom of board
			grid[i][j].addAdjacency(grid[i + 1][j]);
		}
		if (j != 0) { // if not at very left of board
			grid[i][j].addAdjacency(grid[i][j - 1]);
		}
		if (j != numColumns - 1 && ) { // if not at very right of board
			grid[i][j].addAdjacency(grid[i][j + 1]);
		}
	}

	public void setConfigFiles(String layoutCSV, String setupTXT) {
		layoutConfigFile = "data/" + layoutCSV;
		setupConfigFile = "data/" + setupTXT;
	}

    public void loadSetupConfig() throws BadConfigFormatException {
        File file = new File(setupConfigFile);
		String currentLine;
		String[] lineArray;
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				currentLine = sc.nextLine();
				lineArray = currentLine.split(",");
				if (lineArray.length == 3) {
					if (lineArray[0].equals("Room") || lineArray[0].equals("Space")) {
						Room newRoom = new Room(lineArray[1].trim(), null, null);
						roomMap.put(lineArray[2].trim().charAt(0), newRoom);
					}
					else {
						throw new BadConfigFormatException();
					}
				}
				else {
					if (!lineArray[0].substring(0, 2).equals("//")) {
						throw new BadConfigFormatException();
					}
				}
			}
			sc.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

    public void loadLayoutConfig() throws BadConfigFormatException {
        ArrayList<String[]> allLinesLayout = new ArrayList<String[]>();
		File file = new File(layoutConfigFile);
		String currentLine;
		String[] lineArray;
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				currentLine = sc.next();
				lineArray = currentLine.split(",");
				allLinesLayout.add(lineArray);
			}
			numRows = allLinesLayout.size();
			numColumns = allLinesLayout.get(0).length; // TODO - detect bad characters
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		grid = new BoardCell[numRows][numColumns]; 
		int rowCounter = 0;
		int colCounter = 0;
		for (String[] row : allLinesLayout) {
			for (String cell : row) {
				if (cell.length() < 1 || cell.length() > 2) {
					throw new BadConfigFormatException("Layout file contains configuration errors!");
				} else {
					BoardCell newCell = new BoardCell(rowCounter, colCounter, cell.charAt(0));
					if (roomMap.containsKey(cell.charAt(0))) {
						if(cell.length() == 2) {
							Room changer = roomMap.get(cell.charAt(0));
							if(cell.charAt(1) == '#') {
								newCell.setLabel(true);
								changer.setLabelCell(newCell);
							} else if (cell.charAt(1) == '*') {
								newCell.setCenter(true);
								changer.setCenterCell(newCell);
							} else if (cell.charAt(0) == 'W') {
								newCell.setDoorDirection(cell.charAt(1));
							} else {
								newCell.setSecretPassage(cell.charAt(1));
							}
						}
					} else {
						throw new BadConfigFormatException("Layout file specifies a room that is not in the legend!");
					}
					grid[rowCounter][colCounter] = newCell; // fill grid with standard cells
					colCounter++;
				}
			}
			rowCounter++;
			if (colCounter != numColumns) {
				throw new BadConfigFormatException("Layout file that does not have the same number of columns for each row!");
			}
			colCounter = 0;			
		}
    }

			/**
     * Calculate all valid targets to move to.
	 * 
	 * @param startCell The starting cell to examine as it pertains to the pathLength.
	 * @param pathLength The roll/how many moves we have.
     */
    public void calcTargets(BoardCell startCell, int pathLength) {
    	visited.add(startCell); // can never move back to the start cell
		findAllTargets(startCell, pathLength); // call helper function
    }
    
	/**
     * Find all valid targets to move to.
	 * 
	 * @param startCell The starting cell to examine as it pertains to the pathLength.
	 * @param pathLength The roll/how many moves we have.
     */
    private void findAllTargets(BoardCell startCell, int pathLength) {
    	for (BoardCell adjCell : startCell.getAdjList()) { // all adjacent cells
    		if (!visited.contains(adjCell) && !adjCell.getIsOccupied()) { // if not in visited and not occupied
    			visited.add(adjCell);
    			if (pathLength == 1 || adjCell.getIsRoom()) { // if no more moves or at a room cell
    				targets.add(adjCell); // add to targets
    			} else {
    				findAllTargets(adjCell, pathLength - 1); // call recursively with one less move
    			}
    			visited.remove(adjCell);
    		}
    	}
    }

	public Room getRoom(char roomType) {
		return roomMap.get(roomType); // TODO: implement in later assignment
	}	
	
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial()); // TODO: implement in later assignment
	}	
	public int getNumRows() {
		return numRows; // TODO: implement in later assignment
	}

	public int getNumColumns() {
		return numColumns; // TODO: implement in later assignment
	}

	/**
     * Get a cell from the grid.
	 * 
	 * @param row The requested row.
	 * @param col The requested column.
	 * @return Returns the BoardCell from that location in the grid.
     */
	public BoardCell getCell(int row, int col) {
		return grid[row][col]; // returns the cell at the given parameters
	}

}
