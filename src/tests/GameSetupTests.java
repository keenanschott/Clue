package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;

/**
 * GameSetupTests
 * A series of tests to examine the player initialization as it pertains to
 * solution generation, the deck of cards, and the hand of each player.
 * DATE: 3/31/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
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

    // ensure the randomly chosen solution passes our initial checks
    @Test
    public void testSolution() {
        // solution randomly chosen
        // need to ensure we have a card of each type
        assertEquals(board.getTheAnswer().getPerson().getType(), CardType.PERSON);
        assertEquals(board.getTheAnswer().getWeapon().getType(), CardType.WEAPON);
        assertEquals(board.getTheAnswer().getRoom().getType(), CardType.ROOM);
    }

    // ensure the players list is populated correctly with the corresponding players
    @Test
    public void testPlayers() {
        // test players list for size and containment
        assertTrue(board.getPlayersList().size() == 6);
        // test first player
        assertTrue(board.getPlayersList().get(0).getName().equals("Captain Haddock"));
        assertTrue(board.getPlayersList().get(0).getRow() == 0);
        assertTrue(board.getPlayersList().get(0).getColumn() == 18);
        // test other players
        assertTrue(board.getPlayersList().get(1).getName().equals("Richard Parker"));
        assertTrue(board.getPlayersList().get(1).getRow() == 0);
        assertTrue(board.getPlayersList().get(1).getColumn() == 7);
        assertTrue(board.getPlayersList().get(2).getName().equals("Patrick Star"));
        assertTrue(board.getPlayersList().get(2).getRow() == 24);
        assertTrue(board.getPlayersList().get(2).getColumn() == 25);
        assertTrue(board.getPlayersList().get(3).getName().equals("Owen Chase"));
        assertTrue(board.getPlayersList().get(3).getRow() == 24);
        assertTrue(board.getPlayersList().get(3).getColumn() == 18);
        assertTrue(board.getPlayersList().get(4).getName().equals("Captain Ahab"));
        assertTrue(board.getPlayersList().get(4).getRow() == 24);
        assertTrue(board.getPlayersList().get(4).getColumn() == 8);
        assertTrue(board.getPlayersList().get(5).getName().equals("Captain Nemo"));
        assertTrue(board.getPlayersList().get(5).getRow() == 17);
        assertTrue(board.getPlayersList().get(5).getColumn() == 0);
        // test a few colors
        assertTrue(board.getPlayersList().get(0).getColor().equals("cyan"));
        assertTrue(board.getPlayersList().get(2).getColor().equals("orange"));
        // test for five computer players and one human player
        int human = 0, computer = 0;
        for (Player player : board.getPlayersList()) {
            if (player instanceof HumanPlayer) {
                human++;
            } else {
                computer++;
            }
        }
        assertEquals(human, 1);
        assertEquals(computer, 5);
    }

    // ensure the deck is of correct size and is populated correctly>>>>>>>
    // e47437e04655c315fd2a3b23b4cf786b291200fb
    @Test
    public void testDeck() {
        // test deck size
        assertEquals(board.getDeck().size(), 21);
        // test deck contains a card of each type
        assertTrue(board.getDeck().get(15).equals(new Card("Harpoon", CardType.WEAPON)));
        assertTrue(board.getDeck().get(0).equals(new Card("Galley", CardType.ROOM)));
        assertTrue(board.getDeck().get(10).equals(new Card("Richard Parker", CardType.PERSON)));
    }

    // ensure the hands of each player are roughly equal in length and do not
    // contain the solution, are unique, etc.
    @Test
    public void testHands() {
        // ensure each player has three cards in their hand
        for (Player test : board.getPlayersList()) {
            assertEquals(test.getHand().size(), 3);
        }
        // test that each card in every hand is unique and not dealt out twice or more,
        // to different players, etc.
        // assert that there's eight room cards, five weapon cards, and five people
        // cards
        int people = 0, rooms = 0, weapons = 0;
        ArrayList<Card> testUniqueness = new ArrayList<Card>();
        for (Player testPlayer : board.getPlayersList()) {
            for (Card testCard : testPlayer.getHand()) {
                assertFalse(testUniqueness.contains(testCard));
                testUniqueness.add(testCard); // continue checking for uniqueness
                // keep a counter
                if (testCard.getType() == CardType.PERSON) {
                    assertFalse(testCard.equals(board.getTheAnswer().getPerson())); // assert that no hand has the
                                                                                    // answer person
                    people++;
                } else if (testCard.getType() == CardType.WEAPON) {
                    assertFalse(testCard.equals(board.getTheAnswer().getWeapon())); // assert that no hand has the
                                                                                    // answer weapon
                    weapons++;
                } else {
                    assertFalse(testCard.equals(board.getTheAnswer().getRoom())); // assert that no hand has the answer
                                                                                  // room
                    rooms++;
                }
            }
        }
        // check our counters
        assertEquals(people, 5);
        assertEquals(rooms, 8);
        assertEquals(weapons, 5);
    }
}