package tests;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

/**
 * BoardTestsExp
 * A series of tests to examine the test movement algorithm and test adjacency list creation algorithm as it pertains to the initialization of the test game board.
 * DATE: 3/26/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
public class BoardTestsExp {
    private TestBoard board; // test board for a given test

    /**
     * setUp()
     * Set up the game board before each test.
     */
    @BeforeEach
    public void setUp() {
        board = new TestBoard(); // set up an empty test board for each test
    }

    /**
     * testAdjacency()
     * Test that the adjacency list feature is working properly with two different scenarios.
     */
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

    /**
     * testTargetsNormal()
     * Test that calcTargets is working properly with one scenario and no special considerations.
     */
    @Test 
    public void testTargetsNormal() {
    	TestBoardCell cell = board.getCell(0,0); // observed cell at (0, 0)
        board.calcTargets(cell, 2); // roll a 2
        Set<TestBoardCell> targets = board.getTargets();
        Assert.assertEquals(3, targets.size()); 
        Assert.assertTrue(targets.contains(board.getCell(0, 2))); // all cells two moves away
        Assert.assertTrue(targets.contains(board.getCell(1, 1)));
        Assert.assertTrue(targets.contains(board.getCell(2, 0)));
        Assert.assertFalse(targets.contains(board.getCell(1, 0))); // these cells should not be present; would require to move back to a previously visited square
        Assert.assertFalse(targets.contains(board.getCell(0, 1)));
        Assert.assertFalse(targets.contains(board.getCell(0, 0)));
    }
    
    /**
     * testTargetsRoom()
     * Test that calcTargets is working properly with one scenario and room cells present.
     */
    @Test
    public void testTargetsRoom() {
    	board.getCell(2, 0).setIsRoom(true); // a prison of room cells
    	board.getCell(2, 1).setIsRoom(true);
    	board.getCell(2, 2).setIsRoom(true);
    	board.getCell(1, 2).setIsRoom(true);
    	board.getCell(0, 2).setIsRoom(true);
    	TestBoardCell cell = board.getCell(0, 0); // observed cell at (0, 0)
    	board.calcTargets(cell, 3); // roll a 3
    	Set<TestBoardCell> targets = board.getTargets();
    	Assert.assertEquals(6, targets.size()); // six valid rooms
    	Assert.assertTrue(targets.contains(board.getCell(0, 1)));
        Assert.assertTrue(targets.contains(board.getCell(0, 2))); 
        Assert.assertTrue(targets.contains(board.getCell(1, 0))); 
    	Assert.assertTrue(targets.contains(board.getCell(1, 2))); 
    	Assert.assertTrue(targets.contains(board.getCell(2, 0))); 
        Assert.assertTrue(targets.contains(board.getCell(2, 1))); 
    }
    
    /**
     * testTargetsOccupied()
     * Test that calcTargets is working properly with one scenario and occupied cells present.
     */
    @Test
    public void testTargetsOccupied() {
    	board.getCell(3, 2).setIsOccupied(true); 
        board.getCell(0, 3).setIsOccupied(true);
        board.getCell(2, 1).setIsOccupied(true);
    	TestBoardCell cell = board.getCell(3, 3); // observed cell at (3, 3)
    	board.calcTargets(cell, 3); // roll a 3
    	Set<TestBoardCell> targets = board.getTargets();
    	Assert.assertEquals(1, targets.size());
    	Assert.assertTrue(targets.contains(board.getCell(1, 2))); // only valid cell; other valid cells occupied
    }
    
    /**
     * testTargetsMixed()
     * Test that calcTargets is working properly with one scenario and a mix of occupied and room cells present.
     */
    @Test
    public void testTargetsMixed() {
    	board.getCell(0, 1).setIsOccupied(true); // mixed bag of room and occupied cells
    	board.getCell(0, 2).setIsOccupied(true);
    	board.getCell(0, 3).setIsRoom(true);
    	board.getCell(1, 1).setIsRoom(true);
    	board.getCell(2, 1).setIsRoom(true);
    	TestBoardCell cell = board.getCell(2, 2); // observed cell at (2, 2)
    	board.calcTargets(cell, 6); // roll a 6
    	Set<TestBoardCell> targets = board.getTargets();
        Assert.assertEquals(6, targets.size()); // valid cells below; draw out to visualize or see spreadsheet in data
    	Assert.assertTrue(targets.contains(board.getCell(0, 0)));
    	Assert.assertTrue(targets.contains(board.getCell(0, 3)));
    	Assert.assertTrue(targets.contains(board.getCell(1, 1)));
        Assert.assertTrue(targets.contains(board.getCell(2, 1)));
        Assert.assertTrue(targets.contains(board.getCell(3, 1)));
        Assert.assertTrue(targets.contains(board.getCell(2, 0)));
    }
}