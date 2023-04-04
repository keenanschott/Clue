package tests;

import static org.junit.Assert.assertEquals;

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
        Card originalCard1 = test.getHand().get(1); // save original cards
        Card originalCard2 = test.getHand().get(2);
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
        assertEquals(test.disproveSuggestion(badSolution), randomCard); // randomCard should be returned
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
        test.getHand().set(0, randomCard); // preserve instance state for further testing
        test.getHand().set(1, originalCard1);
        test.getHand().set(2, originalCard2);
    }
    
    @Test
    public void testHandleSuggestion() {
        // get a random origin player
        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayersList().get(1); // get "PlayerName2", simply for the sake of testing
        // if suggestion no one can disprove returns null
        Solution badSolution = new Solution(badRoom, badPerson, badWeapon); // bad suggestion, null should be returned
        assertEquals(board.handleSuggestion(testPlayer, badSolution), null);
        // suggestion only suggesting player can disprove returns null
        badSolution.setRoom(testPlayer.getHand().get(0)); // set badSolution room to be an arbitrary Card in the origin player's hand
        assertEquals(board.handleSuggestion(testPlayer, badSolution), null); // should still be null
        // suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
        badSolution.setRoom(badRoom); // reset badSolution
        Card randomCard = board.getPlayersList().get(0).getHand().get(0); // save card
        board.getPlayersList().get(0).getHand().set(0, badRoom); // change only the human player's hand to include the disproven Card
        assertEquals(board.handleSuggestion(testPlayer, badSolution), badRoom); // assert that a computer player can have that Card returned
        board.getPlayersList().get(0).getHand().set(0, randomCard); // return card
        // suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
        ComputerPlayer nextPlayer1 = (ComputerPlayer) board.getPlayersList().get(2); // "PlayerName3"
        Card randomCard1 = board.getPlayersList().get(2).getHand().get(0); // save card
        ComputerPlayer nextPlayer2 = (ComputerPlayer) board.getPlayersList().get(5); // "PlayerName6"
        Card randomCard2 = board.getPlayersList().get(5).getHand().get(0); // save card
        nextPlayer1.getHand().set(0, badPerson); // set hands to contain badPerson
        nextPlayer2.getHand().set(0, badPerson);
        testPlayer.getHand().set(0, badPerson);
        badSolution = new Solution(badRoom, badPerson, badWeapon);
        assertEquals(board.handleSuggestionTestReturn((Player)testPlayer, badSolution), nextPlayer1); // after testPlayer comes nextPlayer1, ensure nextPlayer1 returns the proof
        testPlayer = (ComputerPlayer) board.getPlayersList().get(3); // get "PlayerName4", simply for the sake of testing
        board.getPlayersList().get(1).getHand().set(0, randomCard); // return card for further testing
        randomCard = board.getPlayersList().get(3).getHand().get(0);
        testPlayer.getHand().set(0, badPerson);
        assertEquals(board.handleSuggestionTestReturn((Player)testPlayer, badSolution), nextPlayer1); // now, after testPlayer comes nextPlayer2, ensure nextPlayer2 returns the proof
        board.getPlayersList().get(3).getHand().set(0, randomCard); // return card for further testing
        board.getPlayersList().get(2).getHand().set(0, randomCard1); // return card for further testing
        board.getPlayersList().get(5).getHand().set(0, randomCard2); // return card for further testing
    }
}
