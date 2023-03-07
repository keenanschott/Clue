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
    // these are not in the UML on canvas, but still needed for adjacency testing
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
		targets = new HashSet<BoardCell>(); // allocate space for our sets
    	visited = new HashSet<BoardCell>();
		try {
			loadSetupConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace(); // consider changing this to something else
		}
		try {
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace(); // consider changing this to something else
		}
		createAdj(grid);
    }

	private void createAdj(BoardCell[][] grid) {
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
				else if (grid[i][j].getSecretPassage() != '\u0000') { // checking if its secret passage (\u0000 is null val)
						// set adjacency to room center cell connected to grid[i][j]
						roomMap.get(grid[i][j].getInitial()).getCenterCell().addAdjacency(roomMap.get(grid[i][j].getSecretPassage()).getCenterCell());
				}
				else if ((grid[i][j].getIsRoom() && !grid[i][j].isRoomCenter() || grid[i][j].isLabel() || grid[i][j].getInitial() == 'X' || grid[i][j].isRoomCenter())) {
					// no adjacency
				}
				else {
					if (i != 0) {
						grid[i][j].addAdjacency(grid[i - 1][j]);
					}
					if (i != numRows - 1) {
						grid[i][j].addAdjacency(grid[i + 1][j]);
					}
					if (j != 0) {	
						grid[i][j].addAdjacency(grid[i][j - 1]);
					}
					if (j != numColumns - 1) {
						grid[i][j].addAdjacency(grid[i][j + 1]);
					}
				}
			}	
    	}
	}

	/**
     * Connect instance variables to requested input files.
	 * 
	 * @param layoutCSV The requested layout CSV file.
	 * @param setupTXT The requested setup text file. 
     */
	public void setConfigFiles(String layoutCSV, String setupTXT) {
		layoutConfigFile = "data/" + layoutCSV; // our files are stored in a data folder
		setupConfigFile = "data/" + setupTXT;
	}

	/**
     * Load the setup file and check for validity.
	 * 
	 * @throws BadConfigFormatException
     */
    public void loadSetupConfig() throws BadConfigFormatException {
		roomMap = new HashMap<Character,Room>(); // allocate space for our map
        File file = new File(setupConfigFile); // file object
		String currentLine; // current line
		String[] lineArray; // array of words in current line
		try {
			Scanner sc = new Scanner(file); // open
			while (sc.hasNext()) {
				currentLine = sc.nextLine(); // get entire line
				lineArray = currentLine.split(", "); // split by comma
				if (lineArray.length == 3 && (lineArray[0].equals("Room") || lineArray[0].equals("Space"))) { // valid line
					Room newRoom = new Room(lineArray[1], null, null); // do not center or label yet
					roomMap.put(lineArray[2].charAt(0), newRoom); // put into map
				}
				else {
					if (!lineArray[0].substring(0, 2).equals("//")) { // if not a comment
						throw new BadConfigFormatException();
					}
				}
			}
			sc.close();	// close
		} catch (FileNotFoundException e) {
			e.printStackTrace(); // consider changing this to something else
		}
    }

	/**
     * Load the layout file and check for validity.
	 * 
	 * @throws BadConfigFormatException
     */
    public void loadLayoutConfig() throws BadConfigFormatException {
        ArrayList<String[]> allLinesLayout = new ArrayList<String[]>(); // new ArrayList of Strings
		File file = new File(layoutConfigFile); // file object
		String currentLine; // current line
		String[] lineArray; // array of words in current line
		try {
			Scanner sc = new Scanner(file); // open
			while (sc.hasNext()) {
				currentLine = sc.nextLine();
				lineArray = currentLine.split(",");
				allLinesLayout.add(lineArray);
			}
			numRows = allLinesLayout.size(); // number of rows is equal to size of allLinesLayout
			numColumns = allLinesLayout.get(0).length; // number of columns is equal to size of any entry in allLinesLayout
			sc.close(); // close
		} catch (FileNotFoundException e) {
			e.printStackTrace(); // consider changing this to something else
		}
		// fill the grid given allLinesLayout
		fillGrid(allLinesLayout);
    }

	/**
     * Fill the grid given a two-dimensional array of Strings.
	 * 
	 * @param layout Contains all cells as strings. 
	 * @throws BadConfigFormatException
     */
	private void fillGrid(ArrayList<String[]> layout) throws BadConfigFormatException {
		grid = new BoardCell[numRows][numColumns]; 
		int rowCounter = 0; // count rows
		int colCounter = 0; // count columns
		for (String[] row : layout) {
			for (String cell : row) { // for a given cell String
				if (cell.length() < 1 || cell.length() > 2) {
					throw new BadConfigFormatException("Layout file contains configuration errors!"); // invalid length of String
				} else {
					BoardCell newCell = new BoardCell(rowCounter, colCounter, cell.charAt(0)); // new cell
					if (roomMap.containsKey(cell.charAt(0))) {
						if(cell.length() == 2) { // if a special cell
							Room changeRoom = roomMap.get(cell.charAt(0)); // temporary Room object to change what's in the map
							if(cell.charAt(1) == '#') { // label cell
								newCell.setLabel(true);
								changeRoom.setLabelCell(newCell);
							} else if (cell.charAt(1) == '*') { // center cell
								newCell.setCenter(true);
								changeRoom.setCenterCell(newCell);
							} else if (cell.charAt(0) == 'W') { // doorway cell
								newCell.setDoorDirection(cell.charAt(1));
							} else {
								newCell.setSecretPassage(cell.charAt(1)); // only remaining special cell option
							}
						}
					} else {
						throw new BadConfigFormatException("Layout file specifies a room that is not in the legend!"); // not in setup file
					}
					grid[rowCounter][colCounter] = newCell; // fill grid with newCell
					colCounter++; // next column, same row
				}
			}
			rowCounter++; // next row
			if (colCounter != numColumns) {
				throw new BadConfigFormatException("Layout file that does not have the same number of columns for each row!"); // columns count mismatched in layout file
			}
			colCounter = 0; // reset column count to start from left
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
		return roomMap.get(roomType); // return a room by character input
	}	
	
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial()); // return a room by cell input
	}	

	public int getNumRows() {
		return numRows; // return the number of rows
	}

	public int getNumColumns() {
		return numColumns; // return the number of columns
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList(); // call BoardCell's getAdjList()
	}

	public Set<BoardCell> getTargets() {
		return targets; // return target cells
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
