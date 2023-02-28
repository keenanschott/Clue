package experiment;
import java.util.*;

public class TestBoard {

    public TestBoard() { // empty constructor as prescribed
    }

    public void calcTargets(TestBoardCell startCell, int pathLength) { // function stub
    }

    public TestBoardCell getCell(int row, int col) {
        return new TestBoardCell(0, 0); // returns the cell at the given parameters
    }

    public Set<TestBoardCell> getTargets() {
        return new HashSet<TestBoardCell>(); // gets targets last created by calcTargets()
    }
}
