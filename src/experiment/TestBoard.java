package experiment;
import java.util.*;

public class TestBoard {
    ArrayList<ArrayList<TestBoardCell>> board;

    public TestBoard() {
//    	board = new ArrayList<ArrayList<TestBoardCell>>();
//    	for(int i = 0; i < 4; i ++) {
//    		for(int j = 0; j < 4; j ++) {
//        		board.get(i).set(j, new TestBoardCell(i, j));
//        	}
//    	}
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
