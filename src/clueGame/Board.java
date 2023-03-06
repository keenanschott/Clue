package clueGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.RootPaneContainer;

/**
 * Board
 * A part of Clue Init to set up and populate a game board.
 * @author Keenan Schott
 * @author Finn Burns
 */
public class Board {
    private BoardCell[][] grid; // grid of cells
    private int numRows; // TODO: remove this later when we implement setConfigFiles()
    private int numColumns; // TODO: remove this later when we implement setConfigFiles()
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
		File file = new File(setupConfigFile);
		targets = new HashSet<BoardCell>(); // allocate space for our sets
    	visited = new HashSet<BoardCell>();
		roomMap = new HashMap<>();
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
						System.out.println("Invalid cell type");
					}
				}
				else {
					System.out.println("Invalid line or comment, handle later.");
				}
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}



		ArrayList<String[]> allLinesLayout = new ArrayList<String[]>();
		file = new File(layoutConfigFile);
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
					System.out.println("BAD - really BAD!");
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
					}
					grid[rowCounter][colCounter] = newCell; // fill grid with standard cells
					colCounter++;
				}
			}
			rowCounter++;
			colCounter = 0;			
		}
		
		// for (int i = 0; i < numRows; i++) {
		// 	for (int j = 0; j < numColumns; j++) {
		// 		System.out.println(grid[i][j]);
		// 	}
		// }
		// roomMap.forEach((key, value) -> System.out.println(key + " : " + value));



		// everything above this line is gucci, need to fix adjacency


    	for (int i = 0; i < numRows; i++) { // create an adjacency list for every cell
    		for (int j = 0; j < numColumns; j++) {
    			if (i != 0) { // if not at top of board
    				grid[i][j].addAdjacency(grid[i - 1][j]);
    			}
    			if (i != numRows - 1) { // if not at bottom of board
    				grid[i][j].addAdjacency(grid[i + 1][j]);
    			}
    			if (j != 0) { // if not at very left of board
    				grid[i][j].addAdjacency(grid[i][j - 1]);
    			}
    			if (j != numColumns - 1) { // if not at very right of board
    				grid[i][j].addAdjacency(grid[i][j + 1]);
    			}
    		}
    	}
    }

	public void setConfigFiles(String layoutCSV, String setupTXT) {
		layoutConfigFile = "data/" + layoutCSV;
		setupConfigFile = "data/" + setupTXT;
	}

    public void loadSetupConfig() {
        // TODO: implement in later assignment
    }

    public void loadLayoutConfig() {
        // TODO: implement in later assignment
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

	public static void main(String[] args) {
		Board board = new Board();
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
		board.initialize();
	}
}
