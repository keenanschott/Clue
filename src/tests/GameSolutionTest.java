package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class GameSolutionTest {
    private static Board board;
    private static Card badPerson;
    private static Card badWeapon;
    private static Card badRoom;

    @BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instances
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files 
		board.initialize();
        board.deal();

        badPerson = new Card("badPerson", CardType.PERSON);
        badWeapon = new Card("badWeapon", CardType.WEAPON);
        badRoom = new Card("badRoom", CardType.ROOM);
	}

    // ensure accusations are handled correctly (correct solution and solution (weapon,room,person cases))
    @Test
    public void testAccusation() {
        Solution test;
        // correct solution
        test = board.getTheAnswer();
        assertEquals(board.checkAccusation(test), true);
        // solution with wrong person
        test.setPerson(badPerson);
        assertEquals(board.checkAccusation(test), false); // assertionError
        // solution with wrong weapon
        test = board.getTheAnswer();
        test.setWeapon(badPerson);
        assertEquals(board.checkAccusation(test), false);
        // solution with wrong room
        test = board.getTheAnswer();
        test.setRoom(badRoom);
        assertEquals(board.checkAccusation(test), false);    
    }

    // ensure proper disproval of suggestion when player has matching card/s or no matching card
    @Test
    public void testDisproveSuggestion() {
        // player has one matching card
        // player has multiple matching cards
        // if player has no matching cards 
    }
    
    @Test
    public void testHandleSuggestion() {
        // no one can disprove suggestion
        // suggestion only suggesting player can disprove
        // suggestion only human can disprove 
        // suggestion two players can disprove
    }
}
