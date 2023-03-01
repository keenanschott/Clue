package clueGame;
import java.util.*;

public class Board {
    private BoardCell[][] grid;
    private int numRows;
    private int numColumns;
    private String layoutConfigFile;
    private String setupConfigFile;
    private Map<Character,Room> roomMap;
    private static Board theInstance = new Board();

    // these are not in the UML on canvas, but still needed
    private Set<BoardCell> targets; // all valid cells to move to
	private Set<BoardCell> visited; // visited cells

    private Board() {
        super();
    }
    
    public static Board getInstance() {
        return theInstance;
    }

    public void initialize() {
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
    				grid[i][j].addAdj(grid[i - 1][j]);
    			}
    			if (i != numRows - 1) { // if not at bottom of board
    				grid[i][j].addAdj(grid[i + 1][j]);
    			}
    			if (j != 0) { // if not at very left of board
    				grid[i][j].addAdj(grid[i][j - 1]);
    			}
    			if (j != numColumns - 1) { // if not at very right of board
    				grid[i][j].addAdj(grid[i][j + 1]);
    			}
    		}
    	}
    	targets = new HashSet<BoardCell>(); // allocate space for our sets
    	visited = new HashSet<BoardCell>();
    }

    public void loadSetupConfig() {
        // implement in later assignment
    }

    public void loadLayoutConfig() {
        // implement in later assignment
    }
}
