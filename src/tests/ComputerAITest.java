package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;
public class ComputerAITest {
    private static Board board;
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
    public void testCreateSuggestion() {
        // room matches current location 
        board.getPlayer("PlayerName2").setLocation(4, 2);
        ComputerPlayer testPlayer = (ComputerPlayer)board.getPlayer("PlayerName2");
        Solution testSuggestion = testPlayer.createSuggestion(board.getRoom(board.getCell(testPlayer.getRow(), testPlayer.getColumn())));
        assertEquals(testSuggestion.getRoom(), board.getRoom(board.getCell(testPlayer.getRow(), testPlayer.getColumn())));

        // if only one weapon/person not seen

        // if multiple weapons not seen, one is randomly selected

        // if multiple persons not seen, one is randomly selected
    }

    @Test
    public void testSelectTargets() {
        // if no rooms in list, select randomly
        
        // if room in list that has not been seen, select it

        // if room in list that has been seen, each target (room included) select randomly


    }

}
