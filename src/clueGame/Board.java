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
		ArrayList<String[]> allLinesLayout = new ArrayList<String[]>();
		File file = new File(layoutConfigFile);
		String currentString;
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				String[] currentLine;
				currentString = sc.next();
				currentLine = currentString.split(",");
				allLinesLayout.add(currentLine);
			}
			numRows = allLinesLayout.size();
			numColumns = allLinesLayout.get(0).length; // TODO - detect bad characters
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}






		grid = new BoardCell[numRows][numColumns]; 
    	for (int i = 0; i < numRows; i++) {
    		for (int j = 0; j < numColumns; j++) {
    			BoardCell testCell = new BoardCell(i, j);
    			grid[i][j] = testCell; // fill grid with standard cells
    		}
    	}
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
    	targets = new HashSet<BoardCell>(); // allocate space for our sets
    	visited = new HashSet<BoardCell>();
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
		return new Room("...", new BoardCell(0,0), new BoardCell(0,0)); // TODO: implement in later assignment
	}	
	
	public Room getRoom(BoardCell cell) {
		return new Room("...", new BoardCell(0,0), new BoardCell(0,0)); // TODO: implement in later assignment
	}	
	public int getNumRows() {
		return 0; // TODO: implement in later assignment
	}

	public int getNumColumns() {
		return 0; // TODO: implement in later assignment
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
	}
}
