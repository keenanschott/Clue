package tests;
import static org.junit.Assert.*;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assert.assertTrue(testList.contains(board.getCell(1, 0)));
        Assert.assertTrue(testList.contains(board.getCell(0, 1)));
        Assert.assertTrue(testList.contains(board.getCell(0, 0)));
        Assert.assertTrue(testList.contains(board.getCell(3, 3)));
        Assert.assertTrue(testList.contains(board.getCell(2, 3)));
        Assert.assertTrue(testList.contains(board.getCell(3, 0)));
    }

    @Test 
    public void testTargetsNormal() {
    	TestBoardCell cell = board.getCell(0,0);
        board.calcTargets(cell, 3);
        Set<TestBoardCell> targets = board.getTargets();
        Assert.assertEquals();
        }

}
