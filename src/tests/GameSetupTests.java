package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.DoorDirection;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Room;

public class GameSetupTests {
    private static Board board; // test board for a given test

    @BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instances
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files 
		board.initialize();
        board.deal();
	}

    @Test
    public void testSolution() {
        // solution randomly chosen
        // need to ensure we have a card of each type
        assertEquals(board.getTheAnswer().getPerson().getType(), CardType.PERSON);
        assertEquals(board.getTheAnswer().getWeapon().getType(), CardType.WEAPON);
        assertEquals(board.getTheAnswer().getRoom().getType(), CardType.ROOM);
    }

    @Test
    public void testPlayers() {
        // test players list for size and containment
        assertTrue(board.getPlayersList().size() == 6);
        assertTrue(board.getPlayersList().get(0).equals(new HumanPlayer("PlayerName1", "Red", 18, 0)));
        //assertTrue(board.getPlayersList().get(1).equals(new ComputerPlayer("PlayerName2", "Green", 7, 0)));
    }


}
