package tests;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
    TestBoard board; // test board for a given test

    @BeforeEach
    public void setUp() {
        board = new TestBoard(); // set up an empty test board for each test
    }

    @Test
    public void testAdjacency() {   
        TestBoardCell cell = board.getCell(0,0); // observed cell at (0, 0)
        Set<TestBoardCell> testList = cell.getAdjList();
        Assert.assertTrue(testList.contains(board.getCell(1, 0))); 
        Assert.assertTrue(testList.contains(board.getCell(0, 1)));
        Assert.assertEquals(2, testList.size()); // (0, 0) adjacent to (1, 0) and (0, 1)
        cell = board.getCell(2,2); // observed cell now at (2, 2)
        testList = cell.getAdjList();
        Assert.assertTrue(testList.contains(board.getCell(2, 3)));
        Assert.assertTrue(testList.contains(board.getCell(2, 1)));
        Assert.assertTrue(testList.contains(board.getCell(3, 2)));
        Assert.assertTrue(testList.contains(board.getCell(1, 2)));
        Assert.assertEquals(4, testList.size()); // (2, 2) adjacent to all four cardinal directions
    }

    @Test 
    public void testTargetsNormal() {
    	TestBoardCell cell = board.getCell(0,0); // observed cell at (0, 0)
        board.calcTargets(cell, 2); // roll a 2
        Set<TestBoardCell> targets = board.getTargets();
        Assert.assertEquals(3, targets.size()); 
        Assert.assertTrue(targets.contains(board.getCell(0, 2))); // cells two moves away
        Assert.assertTrue(targets.contains(board.getCell(1, 1)));
        Assert.assertTrue(targets.contains(board.getCell(2, 0)));
        Assert.assertFalse(targets.contains(board.getCell(1, 0))); // these cells should not be present; would require to move back to a previously visited square
        Assert.assertFalse(targets.contains(board.getCell(0, 1)));
        Assert.assertFalse(targets.contains(board.getCell(0, 0)));
        }
    
    @Test
    public void testTargetsRoom() {
    	board.getCell(2, 0).setRoom(true); // a prison of room cells
    	board.getCell(2, 1).setRoom(true);
    	board.getCell(2, 2).setRoom(true);
    	board.getCell(1, 2).setRoom(true);
    	board.getCell(0, 2).setRoom(true);
    	TestBoardCell cell = board.getCell(0, 0); // observed cell at (0, 0)
    	board.calcTargets(cell, 3); // roll a 3
    	Set<TestBoardCell> targets = board.getTargets();
    	Assert.assertEquals(6, targets.size());
    	Assert.assertTrue(targets.contains(board.getCell(0, 1))); 
        Assert.assertTrue(targets.contains(board.getCell(0, 2))); 
        Assert.assertTrue(targets.contains(board.getCell(1, 0))); 
    	Assert.assertTrue(targets.contains(board.getCell(1, 2))); 
    	Assert.assertTrue(targets.contains(board.getCell(2, 0))); 
        Assert.assertTrue(targets.contains(board.getCell(2, 1))); 
    }
    
    @Test
    public void testTargetsOccupied() {
    	board.getCell(3, 2).setOccupied(true);
        board.getCell(0, 3).setOccupied(true);
        board.getCell(2, 1).setOccupied(true);
    	TestBoardCell cell = board.getCell(3, 3); // observed cell at (3, 3)
    	board.calcTargets(cell, 3); // roll a 3
    	Set<TestBoardCell> targets = board.getTargets();
    	Assert.assertEquals(1, targets.size());
    	Assert.assertTrue(targets.contains(board.getCell(1, 2))); // only valid cell; (3, 2) is occupied
    }
    
    @Test
    public void testTargetsMixed() {
    	board.getCell(0, 1).setOccupied(true); // mixed bag of rooms and occupation
    	board.getCell(0, 2).setOccupied(true);
    	board.getCell(0, 3).setRoom(true);
    	board.getCell(1, 1).setRoom(true);
    	board.getCell(2, 1).setRoom(true);
    	TestBoardCell cell = board.getCell(2, 2); // observed cell at (2, 2)
    	board.calcTargets(cell, 6); // roll a 6
    	Set<TestBoardCell> targets = board.getTargets();
        System.out.println(targets);
        Assert.assertEquals(5, targets.size()); // valid cells that are not rooms below; I drew it out
    	Assert.assertTrue(targets.contains(board.getCell(0, 0)));
    	Assert.assertTrue(targets.contains(board.getCell(0, 3)));
    	Assert.assertTrue(targets.contains(board.getCell(1, 1)));
        Assert.assertTrue(targets.contains(board.getCell(2, 1)));
        Assert.assertTrue(targets.contains(board.getCell(3, 1)));
    }
}