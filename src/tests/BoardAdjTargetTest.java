package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// (2,2) Center of Bridge w/ Secret Passage to Landing Pad
		Set<BoardCell> testList = board.getAdjList(2, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(2,7)));
		assertTrue(testList.contains(board.getCell(23,30)));

		// (21,13) Center Dining Hall
		testList = board.getAdjList(21, 13);
		assertEquals(2,testList.size());
		assertTrue(testList.contains(board.getCell(21,9)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		// (18,3)
		Set<BoardCell> testList = board.getAdjList(18,3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(21, 2)));
		assertTrue(testList.contains(board.getCell(18, 2)));
		assertTrue(testList.contains(board.getCell(18, 4)));
		assertTrue(testList.contains(board.getCell(17, 3)));

		// (20,25)
		testList = board.getAdjList(20, 25);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(19, 25)));
		assertTrue(testList.contains(board.getCell(21, 25)));
		assertTrue(testList.contains(board.getCell(20, 28)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// (18,8)
		Set<BoardCell> testList = board.getAdjList(18,8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(18,7)));
		assertTrue(testList.contains(board.getCell(18,9)));
		assertTrue(testList.contains(board.getCell(17,8)));
		assertTrue(testList.contains(board.getCell(17,9)));

		// (7,17)
		testList = board.getAdjList(7,17);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(6,17)));
		assertTrue(testList.contains(board.getCell(7,16)));
		assertTrue(testList.contains(board.getCell(7,18)));

		// (7,25)
		testList = board.getAdjList(7,25);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(6,25)));
		assertTrue(testList.contains(board.getCell(7,24)));
		assertTrue(testList.contains(board.getCell(7,26)));
	}
	
	
	// Tests out of room center, 1, 2 and 3
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInGalley() { // (14,2)
		// test a roll of 1
		board.calcTargets(board.getCell(14,2), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		// test a roll of 2
		board.calcTargets(board.getCell(14,2), 2);
		targets = board.getTargets();
		assertEquals(2, targets.size());

		// test a roll of 3
		board.calcTargets(board.getCell(14,2), 3);
		targets = board.getTargets();
		assertEquals(2, targets.size());

	}
	
	@Test
	public void testTargetsInLandingPad() { // (20,28)
		// test roll of 1
		board.calcTargets(board.getCell(20, 28), 1);
		Set<BoardCell> targets = board.getTargets();
		// test roll of 2
		board.calcTargets(board.getCell(20, 28), 2);
		targets = board.getTargets();
		// test roll of 3
		board.calcTargets(board.getCell(20, 28), 3);
		targets = board.getTargets();
		
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() { // (5,19)
		// test roll of 1
		board.calcTargets(board.getCell(5, 19), 1);
		Set<BoardCell> targets = board.getTargets();
		// test roll of 3
		board.calcTargets(board.getCell(5, 19), 3);
		targets = board.getTargets();
		// test roll of 4
		board.calcTargets(board.getCell(5, 19), 4);
		targets = board.getTargets();
	}

	@Test
	public void testTargetsInWalkway1() { // (24,8)
		// test roll of 1
		board.calcTargets(board.getCell(24, 8), 1);
		Set<BoardCell> targets = board.getTargets();
		// test roll of 3
		board.calcTargets(board.getCell(24, 8), 3);
		targets = board.getTargets();
		// test roll of 4
		board.calcTargets(board.getCell(24, 8), 4);
		targets = board.getTargets();
	}

	@Test
	public void testTargetsInWalkway2() { // (9,13)
		// test roll of 1
		board.calcTargets(board.getCell(9, 13), 1);
		Set<BoardCell> targets = board.getTargets();
		// test roll of 3
		board.calcTargets(board.getCell(9, 13), 3);
		targets = board.getTargets();
		// test roll of 4
		board.calcTargets(board.getCell(9, 13), 4);
		targets = board.getTargets();
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() { 
		// (21,7) - make sure player can't move through a door occupied by another player
		// (3,24) - room center cell is occupied, want to 
		// (10,14) - nearby walkway occupied 
	}
}

/*
 * only walkway adjacent (18,8) (7,17) (7,25)
 * rooms/doors (2,2) (20,25) (18,3)
 * test targets (9,13) (5,19) (24,8) (14,2) (20,28)
 * occupied spaces (10,14) (3,24) (21,7)
 * door directions (20,19) (10,10)
 */