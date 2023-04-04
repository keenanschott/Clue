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
    public void testCheckAccusation() {
        Card correctPerson = board.getTheAnswer().getPerson(); // save correct person
        Card correctRoom = board.getTheAnswer().getRoom(); // save correct room
        Card correctWeapon = board.getTheAnswer().getWeapon(); // save correct weapon
        // check the correct solution
        Solution test = new Solution(correctRoom, correctPerson, correctWeapon);
        assertEquals(board.checkAccusation(test), true);
        // solution with wrong person
        test.setPerson(badPerson);
        assertEquals(board.checkAccusation(test), false);
        test.setPerson(correctPerson);
        // solution with wrong weapon
        test.setWeapon(badWeapon);
        assertEquals(board.checkAccusation(test), false);
        test.setWeapon(correctWeapon);
        // solution with wrong room
        test.setRoom(badRoom);
        assertEquals(board.checkAccusation(test), false);
        test.setRoom(correctRoom);
        // after all that changing, check original is still correct   
        assertEquals(board.checkAccusation(test), true); 
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
