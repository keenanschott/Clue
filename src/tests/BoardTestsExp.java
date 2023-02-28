package tests;
import static org.junit.Assert.assertEquals;
import java.util.Set;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
    TestBoard board;

    @BeforeEach
    public void setUp() {
        board = new TestBoard();
    }

    @Test
    public void testAdjacency() {   
        TestBoardCell cell = board.getCell(0,0);
        Set<TestBoardCell> testList = cell.getAdjList();
        Assert.assertTrue();
    }

    @Test 
    public void testTargetCreation() {

    }

}
