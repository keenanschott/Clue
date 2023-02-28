package experiment;
import java.util.*;

public class TestBoard {
	private TestBoardCell[][] grid; // grid of cells
	private Set<TestBoardCell> targets; // targets
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;

    public TestBoard() { // empty constructor as prescribed
    	grid = new TestBoardCell[4][4]; // for testing
    	targets = new HashSet<TestBoardCell>();
    	visited = new HashSet<TestBoardCell>();
    }

    public void calcTargets(TestBoardCell startCell, int pathLength) { // function stub
    }

    public TestBoardCell getCell(int row, int col) {
        return grid[row][col]; // returns the cell at the given parameters
    }

    public Set<TestBoardCell> getTargets() {
        return targets; // gets targets last created by calcTargets()
    }
}
