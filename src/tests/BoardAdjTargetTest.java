package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

/**
 * BoardAdjTargetTest
 * A part of Clue Board to test and examine the movement algorithm as it pertains to the game board.
 * DATE: 3/8/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
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
	// These cells are BRIGHT BLUE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// (2,2) Center of Bridge w/ Secret Passage to Landing Pad
		Set<BoardCell> testList = board.getAdjList(2, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(2,7)));
		assertTrue(testList.contains(board.getCell(20,28)));

		// (21,13) Center Dining Hall
		testList = board.getAdjList(21, 13);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(21,9)));
		assertTrue(testList.contains(board.getCell(18,17)));
		assertFalse(testList.contains(board.getCell(21,14))); // ensure it does not move to a generic room cell
	}

	// Ensure door locations include their rooms and also additional walkways
	// These cells are BRIGHT BLUE on the planning spreadsheet
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
	// These tests are DARK ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// (18,8)
		Set<BoardCell> testList = board.getAdjList(18,8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(18,7)));
		assertTrue(testList.contains(board.getCell(18,9)));
		assertTrue(testList.contains(board.getCell(17,8)));
		assertTrue(testList.contains(board.getCell(19,8)));

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
	
	// Tests out of the Galley's room center
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInGalley() { 
		// (14,2)
		// test a roll of 1
		board.calcTargets(board.getCell(14,2), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size()); // three walkways from the room
		assertTrue(targets.contains(board.getCell(5,5)));
		assertTrue(targets.contains(board.getCell(14,13)));
		assertTrue(targets.contains(board.getCell(17,6)));
		targets.clear();

		// test a roll of 2
		board.calcTargets(board.getCell(14,2), 2);
		targets = board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(5,4)));
		assertTrue(targets.contains(board.getCell(15,13)));
		assertTrue(targets.contains(board.getCell(18,6)));
		targets.clear();

		// test a roll of 3
		board.calcTargets(board.getCell(14,2), 3);
		targets = board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(6,6)));
		assertTrue(targets.contains(board.getCell(12,13)));
		assertTrue(targets.contains(board.getCell(17,8)));
	}
	
	// Tests out of the Landing Pad's room center
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInLandingPad() { 
		// (20,28)
		// test roll of 1
		board.calcTargets(board.getCell(20, 28), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(2,2)));
		assertTrue(targets.contains(board.getCell(20,25)));
		assertTrue(targets.contains(board.getCell(15,28)));
		targets.clear();

		// test roll of 2
		board.calcTargets(board.getCell(20, 28), 2);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(2,2)));
		assertTrue(targets.contains(board.getCell(21,25)));
		assertTrue(targets.contains(board.getCell(15,29)));
		targets.clear();

		// test roll of 3
		board.calcTargets(board.getCell(20, 28), 3);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(2,2)));
		assertTrue(targets.contains(board.getCell(22,25)));
		assertTrue(targets.contains(board.getCell(15,30)));		
	}

	// Tests out of a doorway
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() { 
		// (18,17) and (5,19)
		// test roll of 1
		board.calcTargets(board.getCell(18, 17), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(21,13)));
		assertTrue(targets.contains(board.getCell(17,17)));
		assertTrue(targets.contains(board.getCell(18,16)));
		assertTrue(targets.contains(board.getCell(18,18)));
		targets.clear();

		// test roll of 2
		board.calcTargets(board.getCell(5, 19), 2);
		targets = board.getTargets();
		assertEquals(7,targets.size());
		assertTrue(targets.contains(board.getCell(3,24)));
		assertTrue(targets.contains(board.getCell(3,19)));
		assertTrue(targets.contains(board.getCell(4,18)));
		assertTrue(targets.contains(board.getCell(5,17)));
		assertTrue(targets.contains(board.getCell(6,18)));
		assertTrue(targets.contains(board.getCell(7,19)));
		assertTrue(targets.contains(board.getCell(6,20)));
		targets.clear();

		// test roll of 3
		board.calcTargets(board.getCell(5, 19), 3);
		targets = board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(3,24)));
		assertTrue(targets.contains(board.getCell(2,19)));
		assertTrue(targets.contains(board.getCell(3,18)));
		assertTrue(targets.contains(board.getCell(4,17)));
		assertTrue(targets.contains(board.getCell(5,16)));
		assertTrue(targets.contains(board.getCell(6,17)));
		assertTrue(targets.contains(board.getCell(7,18)));
		assertTrue(targets.contains(board.getCell(7,20)));
		assertTrue(targets.contains(board.getCell(6,21)));
		assertTrue(targets.contains(board.getCell(5,18)));
		assertTrue(targets.contains(board.getCell(4,19)));
		assertTrue(targets.contains(board.getCell(6,19)));
	}

	// Tests out of a walkway
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInWalkway1() { 
		// (24,8)
		// test roll of 1
		board.calcTargets(board.getCell(24, 8), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3,targets.size());
		assertTrue(targets.contains(board.getCell(24,7)));
		assertTrue(targets.contains(board.getCell(23,8)));
		assertTrue(targets.contains(board.getCell(24,9)));
		targets.clear();

		// test roll of 2
		board.calcTargets(board.getCell(24, 8), 2);
		targets = board.getTargets();
		assertEquals(3,targets.size());
		assertTrue(targets.contains(board.getCell(23,7)));
		assertTrue(targets.contains(board.getCell(22,8)));
		assertTrue(targets.contains(board.getCell(23,9)));
		targets.clear();

		// test roll of 3
		board.calcTargets(board.getCell(24, 8), 3);
		targets = board.getTargets();
		assertEquals(6,targets.size());
		assertTrue(targets.contains(board.getCell(22,7)));
		assertTrue(targets.contains(board.getCell(21,8)));
		assertTrue(targets.contains(board.getCell(22,9)));
		assertTrue(targets.contains(board.getCell(24,7)));
		assertTrue(targets.contains(board.getCell(23,8)));
		assertTrue(targets.contains(board.getCell(24,9)));
	}

	// Tests out of a walkway
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInWalkway2() { 
		// (9,13)
		// test roll of 1
		board.calcTargets(board.getCell(9, 13), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(9, 12)));	
		assertTrue(targets.contains(board.getCell(8, 13)));	
		assertTrue(targets.contains(board.getCell(9,14)));
		assertTrue(targets.contains(board.getCell(10,13)));
		targets.clear();

		// test roll of 2
		board.calcTargets(board.getCell(9, 13), 2);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(11, 13)));	
		assertTrue(targets.contains(board.getCell(10, 14)));	
		assertTrue(targets.contains(board.getCell(8,14)));
		assertTrue(targets.contains(board.getCell(7,13)));
		assertTrue(targets.contains(board.getCell(8,12)));
		assertTrue(targets.contains(board.getCell(10,12)));
		targets.clear();

		// test roll of 3
		board.calcTargets(board.getCell(9, 13), 3);
		targets = board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(7,12)));
		assertTrue(targets.contains(board.getCell(7,14)));
		assertTrue(targets.contains(board.getCell(8,13)));
		assertTrue(targets.contains(board.getCell(9,12)));
		assertTrue(targets.contains(board.getCell(9,14)));
		assertTrue(targets.contains(board.getCell(10,11)));
		assertTrue(targets.contains(board.getCell(10,13)));
		assertTrue(targets.contains(board.getCell(11,12)));
		assertTrue(targets.contains(board.getCell(12, 13)));	
		assertTrue(targets.contains(board.getCell(11, 14)));	
	}

	// Tests are LIGHT BLUE on the planning spreadsheet
	// Occupied cells are RED on the planning spreadsheet
	@Test
	public void testTargetsOccupied() { 
		// (21,7) - make sure player can't move through a door occupied by another player
		board.getCell(21,7).setOccupied(true);
		board.calcTargets(board.getCell(24, 8), 4);
		board.getCell(21,7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(5, targets.size());
		assertFalse(targets.contains(board.getCell(21,7))); // ensure targets does not contain the doorway
		targets.clear();
		
		// (3,24) - room center cell is occupied
		board.getCell(3, 24).setOccupied(true);
		board.calcTargets(board.getCell(5, 19), 1);
		board.getCell(3, 24).setOccupied(false);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(3, 24))); // room centers, even if occupied, can still be moved to per the rules
		assertTrue(targets.contains(board.getCell(4, 19)));	
		assertTrue(targets.contains(board.getCell(6,19)));
		assertTrue(targets.contains(board.getCell(5,18)));
		targets.clear();

		// (10,14) - nearby walkway occupied 
		board.getCell(10,14).setOccupied(true);
		board.calcTargets(board.getCell(9,13), 2);
		board.getCell(10,14).setOccupied(false);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertFalse(targets.contains(board.getCell(10,14))); // ensure targets does not contain occupied cell
		assertTrue(targets.contains(board.getCell(11,13)));
		assertTrue(targets.contains(board.getCell(10,12)));
		assertTrue(targets.contains(board.getCell(8,12)));
		assertTrue(targets.contains(board.getCell(8,14)));
		assertTrue(targets.contains(board.getCell(7,13)));
	}
}