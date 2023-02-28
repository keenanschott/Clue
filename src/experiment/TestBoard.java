package experiment;
import java.util.*;

public class TestBoard {
    ArrayList<ArrayList<TestBoardCell>> board;

    public TestBoard() {

    }

    public void calcTargets(TestBoardCell startCell, int pathLength) {

    }


    public TestBoardCell getCell(int row, int col) {
        return board.get(row).get(col);
    }

    public Set<TestBoardCell> getTargets() {
        Set<TestBoardCell> testCell = new HashSet<TestBoardCell>();
        return testCell;
    }
}
