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
        badPerson = new Card("badPerson", CardType.PERSON); // create dummy cards for our tests
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
        // after all that changing, check original is correct   
        assertEquals(board.checkAccusation(test), true); 
    }

    // ensure proper disproval of suggestion when player has matching card/s or no matching card
    @Test
    public void testDisproveSuggestion() {
        HumanPlayer test = (HumanPlayer)board.getPlayersList().get(0); // get "PlayerName1", simply for the sake of testing
        Card randomCard = test.getHand().get(0); // get a random card in the player's hand for comparison
        // if player has no matching cards, null is returned (as of now, ComputerPlayer and HumanPlayer disproveSuggestion() are the exact same)
        Solution badSolution = new Solution(badRoom, badPerson, badWeapon); // bad suggestion, null should be returned
        assertEquals(test.disproveSuggestion(badSolution), null);
        // if player has only one matching card it should be returned
        // set badSolution accordingly
        if (randomCard.getType() == CardType.PERSON) {
            badSolution = new Solution(badRoom, randomCard, badWeapon); 
        } else if (randomCard.getType() == CardType.WEAPON) {
            badSolution = new Solution(badRoom, badPerson, randomCard); 
        } else {
            badSolution = new Solution(randomCard, badPerson, badWeapon); 
        }   
        assertEquals(test.disproveSuggestion(badSolution), randomCard); // randomCard3 should be returned
        // if players has >1 matching card, returned card should be chosen randomly
        // set hand and suggestion to something known
        test.getHand().set(0, badPerson);
        test.getHand().set(1, badWeapon);
        test.getHand().set(2, badRoom);
        badSolution = new Solution(badRoom, badPerson, badWeapon); // all 3 matching
        int person = 0, weapon = 0, room = 0;
        for (int i = 0; i < 1000; i++) { // prove all three occur at least 250 times
            Card currentCard = test.disproveSuggestion(badSolution);
            if (currentCard.equals(badPerson)) {
                person++;
            } else if (currentCard.equals(badRoom)) {
                room++;
            } else if (currentCard.equals(badWeapon)) {
                weapon++;
            } else {
                assertEquals(true, false); // something bad happened if this occurs
            }
        }
        assertEquals(person > 250, true); // does it occur randomly at an acceptable frequency?
        assertEquals(room > 250, true);
        assertEquals(weapon > 250, true);
    }
    
    @Test
    public void testHandleSuggestion() {
        // no one can disprove suggestion
        // suggestion only suggesting player can disprove
        // suggestion only human can disprove 
        // suggestion two players can disprove
    }
}
