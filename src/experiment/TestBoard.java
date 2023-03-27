package experiment;
import java.util.*;

/**
 * TestBoard
 * The test game board initialization; reads in the game setup and layout, populates the game board, creates adjacency lists, etc. This class also implements the movement algorithm.
 * DATE: 3/26/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
public class TestBoard {
	private TestBoardCell[][] grid; // grid of cells
	private Set<TestBoardCell> targets; // all valid cells to move to
	private Set<TestBoardCell> visited; // visited cells
	public final static int COLS = 4; // 4 x 4 game board
	public final static int ROWS = 4;

	/**
	 * TestBoard()
     * Set up the game board with new board cells. Then, create an adjacency list for every cell.
     */
    public TestBoard() { 
		targets = new HashSet<TestBoardCell>(); // allocate space for our sets and board
    	visited = new HashSet<TestBoardCell>();
    	grid = new TestBoardCell[ROWS][COLS]; 
    	for (int i = 0; i < ROWS; i++) {
    		for (int j = 0; j < COLS; j++) {
    			TestBoardCell testCell = new TestBoardCell(i, j);
    			grid[i][j] = testCell; // fill grid with standard cells
    		}
    	}
    	for (int i = 0; i < ROWS; i++) { // create an adjacency list for every cell
    		for (int j = 0; j < COLS; j++) {
    			if (i != 0) { // if not at top of board
    				grid[i][j].addAdjacency(grid[i - 1][j]);
    			}
    			if (i != ROWS - 1) { // if not at bottom of board
    				grid[i][j].addAdjacency(grid[i + 1][j]);
    			}
    			if (j != 0) { // if not at very left of board
    				grid[i][j].addAdjacency(grid[i][j - 1]);
    			}
    			if (j != COLS - 1) { // if not at very right of board
    				grid[i][j].addAdjacency(grid[i][j + 1]);
    			}
    		}
    	}
    }

	/**
	 * calcTargets()
     * Calculate all valid targets to move to.
	 * 
	 * @param startCell The starting cell to examine as it pertains to the pathLength.
	 * @param pathLength The roll/how many moves we have.
     */
    public void calcTargets(TestBoardCell startCell, int pathLength) {
    	visited.add(startCell); // can never move back to the start cell
		findAllTargets(startCell, pathLength); // call helper function
    }
    
	/**
	 * findAllTargets()
     * Find all valid targets to move to.
	 * 
	 * @param startCell The starting cell to examine as it pertains to the pathLength.
	 * @param pathLength The roll/how many moves we have.
     */
    private void findAllTargets(TestBoardCell startCell, int pathLength) {
    	for (TestBoardCell adjCell : startCell.getAdjList()) { // all adjacent cells
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

	/**
	 * getCell()
     * Get a cell from the grid.
	 * 
	 * @param row The requested row.
	 * @param col The requested column.
	 * @return Returns the TestBoardCell from that location in the grid.
     */
    public TestBoardCell getCell(int row, int col) {
        return grid[row][col]; // returns the cell at the given parameters
    }

	/**
	 * getTargets()
     * Return the targets list from calcTargets().
	 * 
	 * @return Returns targets.
     */
    public Set<TestBoardCell> getTargets() {
        return targets; // gets targets created by calcTargets()
    }    
}
