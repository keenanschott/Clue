package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

/**
 * FileInitTests
 * A series of tests to examine the file initialization as it pertains to the
 * cell and room identifiers along with the room Map.
 * DATE: 3/26/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class FileInitTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 31; // size of our board

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test.
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few others
		assertEquals("Galley", board.getRoom('G').getName());
		assertEquals("Swimming Pool", board.getRoom('P').getName());
		assertEquals("Captain's Quarters", board.getRoom('C').getName());
		assertEquals("Sun Deck", board.getRoom('S').getName());
		assertEquals("Walkway", board.getRoom('W').getName());
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// two cells that are not a doorway.
	@Test
	public void FourDoorDirections() {
		BoardCell cell = board.getCell(6, 29);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(8, 21);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(15, 28);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		cell = board.getCell(21, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		// test that walkways are not doors
		cell = board.getCell(0, 8);
		assertFalse(cell.isDoorway());
	}

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(19, numDoors); // we have 19 doors total
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRooms() {
		// just test a standard room location
		BoardCell cell = board.getCell(9, 23);
		Room room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Engine Room");
		assertFalse(cell.isLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());

		// this is a label cell to test
		cell = board.getCell(6, 10);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Sun Deck");
		assertTrue(cell.isLabel());
		assertTrue(room.getLabelCell() == cell);

		// this is a room center cell to test
		cell = board.getCell(3, 11);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Sun Deck");
		assertTrue(cell.isRoomCenter());
		assertTrue(room.getCenterCell() == cell);

		// this is a secret passage test
		cell = board.getCell(0, 0);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Bridge");
		assertTrue(cell.getSecretPassage() == 'L');

		// test a walkway
		cell = board.getCell(5, 0);
		room = board.getRoom(cell);
		// Note for our purposes, walkways and closets are rooms
		assertTrue(room != null);
		assertEquals(room.getName(), "Walkway");
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());

		// test a closet
		cell = board.getCell(4, 0);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Unused");
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());
	}
}
