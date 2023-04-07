package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;

/**
 * ComputerAITest
 * A series of tests to examine the computer's AI processes as they relate to
 * suggestion creation and movement.
 * DATE: 4/4/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class ComputerAITest {
    private static Board board; // test board for a given test

    @BeforeEach
    public void setUp() {
        // Board is singleton, get the only instances
        board = Board.getInstance();
        // set the file names
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        // Initialize will load config files
        board.initialize();
        board.deal();
    }

    // ensure the computer's created suggestions pass our criteria (see below)
    @Test
    public void testCreateSuggestion() {
        // room matches current location
        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayer("Richard Parker");
        testPlayer.setLocation(2, 2); // set to middle of the room
        Room currentRoom = board.getRoom(board.getCell(2, 2)); // get the room we're currently in
        Solution testSuggestion = testPlayer
                .createSuggestion(board.getRoom(board.getCell(testPlayer.getRow(), testPlayer.getColumn()))); // create
                                                                                                              // suggestion
                                                                                                              // based
                                                                                                              // off of
                                                                                                              // current
                                                                                                              // room
        assertEquals(board.getRoom(testSuggestion.getRoom()), currentRoom); // assert suggestion room is equal to
                                                                            // current room
        // if only one weapon/person not seen, it's selected
        Set<Card> currentSeenCards = new HashSet<Card>(); // create a new set of seen cards
        int i = 0;
        Card targetCard = new Card(null, null); // create weapon card to be missing
        for (Card card : board.getDeck()) {
            if (card.getType() == CardType.WEAPON && i < 5) { // include all weapons except "Poison"
                currentSeenCards.add(card);
                i++;
            } else if (i == 5 && card.getType() == CardType.WEAPON) {
                targetCard = card; // "Poison"
            }
        }
        testPlayer.setSeenCards(currentSeenCards); // set seen cards
        assertEquals(testPlayer.createSuggestion(currentRoom).getWeapon(), targetCard); // suggestion should contain
                                                                                        // "Poison"
        currentSeenCards = new HashSet<Card>(); // create a new set of seen cards
        i = 0;
        targetCard = new Card(null, null); // create person card to be missing
        for (Card card : board.getDeck()) {
            if (card.getType() == CardType.PERSON && i < 5) { // include all people except "Captain Nemo"
                currentSeenCards.add(card);
                i++;
            } else if (i == 5 && card.getType() == CardType.PERSON) {
                targetCard = card; // "Captain Nemo"
            }
        }
        testPlayer.setSeenCards(currentSeenCards); // set seen cards
        assertEquals(testPlayer.createSuggestion(currentRoom).getPerson(), targetCard); // suggestion should contain
                                                                                        // "Captain Nemo"
        // if multiple weapons not seen, one is randomly selected
        ArrayList<Card> twoCards = new ArrayList<Card>();
        currentSeenCards = new HashSet<Card>(); // create a new set of seen cards
        i = 0;
        targetCard = new Card(null, null); // create weapon cards to be missing
        for (Card card : board.getDeck()) {
            if (card.getType() == CardType.WEAPON && i < 4) { // include all weapons except "Propeller" and "Poison"
                currentSeenCards.add(card);
                i++;
            } else if (i == 4 && card.getType() == CardType.WEAPON) {
                twoCards.add(card); // "Propeller" and "Poison"
            }
        }
        testPlayer.setSeenCards(currentSeenCards); // set seen cards
        int five = 0, six = 0; // counters
        for (int j = 0; j < 1000; j++) {
            targetCard = testPlayer.createSuggestion(currentRoom).getWeapon();
            if (targetCard.equals(twoCards.get(0))) {
                five++; // "Propeller"
            } else if ((targetCard.equals(twoCards.get(1)))) {
                six++; // "Poison"
            } else {
                assertEquals(true, false);
            }
        }
        assertTrue(five > 400); // assert "Propeller" appears more than 400 times
        assertTrue(six > 400); // assert "Poison" appears more than 400 times
        // if multiple persons not seen, one is randomly selected
        twoCards = new ArrayList<Card>();
        currentSeenCards = new HashSet<Card>(); // create a new set of seen cards
        i = 0;
        targetCard = new Card(null, null); // create person cards to be missing
        for (Card card : board.getDeck()) {
            if (card.getType() == CardType.PERSON && i < 4) { // include all weapons except "Captain Ahab" and "Captain
                                                              // Nemo"
                currentSeenCards.add(card);
                i++;
            } else if (i == 4 && card.getType() == CardType.PERSON) {
                twoCards.add(card); // "Captain Ahab" and "Captain Nemo"
            }
        }
        testPlayer.setSeenCards(currentSeenCards); // set seen cards
        five = 0;
        six = 0; // counters
        for (int j = 0; j < 1000; j++) {
            targetCard = testPlayer.createSuggestion(currentRoom).getPerson();
            if (targetCard.equals(twoCards.get(0))) {
                five++; // "Captain Ahab"
            } else if ((targetCard.equals(twoCards.get(1)))) {
                six++; // "Captain Nemo"
            } else {
                assertEquals(true, false);
            }
        }
        assertTrue(five > 400); // assert "Captain Ahab" appears more than 400 times
        assertTrue(six > 400); // assert "Captain Nemo" appears more than 400 times
    }

    // ensure the computer's movement algorithm prioritizes unseen rooms
    @Test
    public void testSelectTargets() {
        // if no rooms in list, select randomly
        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayer("Richard Parker"); // copy over player
        board.calcTargets(board.getCell(testPlayer.getRow(), testPlayer.getColumn()), 1); // no target from (0, 7) that
                                                                                          // is a room with a roll of 1
        Map<BoardCell, Integer> trackTargets = new Hashtable<>(); // map to track targets and occurences for random
                                                                  // confirmation
        Iterator<BoardCell> iterator = board.getTargets().iterator(); // iterate over set of targets
        BoardCell sampleTarget;
        while (iterator.hasNext()) {
            trackTargets.put(iterator.next(), 0); // populate trackTargets with each possible target and a count of 0
        }
        for (int i = 0; i < 1000; i++) {
            sampleTarget = testPlayer.selectTarget(1); // choose a target
            trackTargets.put(sampleTarget, trackTargets.get(sampleTarget) + 1); // add 1 to target for each occurrence
        }
        for (BoardCell key : trackTargets.keySet()) {
            assertTrue(trackTargets.get(key) > 400); // two possible targets; each should generate at least 400
        }
        // if room in list that has not been seen, select it
        Set<Card> currentSeenCards = new HashSet<Card>(); // reset seen cards to empty
        testPlayer = (ComputerPlayer) board.getPlayer("Richard Parker");
        board.calcTargets(board.getCell(testPlayer.getRow(), testPlayer.getColumn()), 3); // (2, 2) is nearest room
        testPlayer.setSeenCards(currentSeenCards); // no seen cards
        assertEquals(testPlayer.selectTarget(3), board.getCell(2, 2)); // should be (2, 2)
        currentSeenCards.clear();
        testPlayer = (ComputerPlayer) board.getPlayer("Owen Chase"); // check a different player
        board.calcTargets(board.getCell(testPlayer.getRow(), testPlayer.getColumn()), 6); // (21, 22) is nearest room
        testPlayer.setSeenCards(currentSeenCards); // no seen cards
        assertEquals(testPlayer.selectTarget(6), board.getCell(21, 22)); // should be (21, 22)
        currentSeenCards.clear();
        // if room in list that has been seen, each target (room included) selected
        // randomly
        currentSeenCards.add(new Card("Bridge", CardType.ROOM)); // add the bridge card to seen cards
        trackTargets.clear();
        testPlayer = (ComputerPlayer) board.getPlayer("Richard Parker");
        testPlayer.setSeenCards(currentSeenCards);
        board.calcTargets(board.getCell(testPlayer.getRow(), testPlayer.getColumn()), 3); // (2, 2) is the nearest
                                                                                          // reachable room, but other
                                                                                          // targets should be chosen
                                                                                          // randomly since it's been
                                                                                          // seen
        iterator = board.getTargets().iterator();
        while (iterator.hasNext()) {
            trackTargets.put(iterator.next(), 0); // populate trackTargets with each possible target and a count of 0
        }
        for (int i = 0; i < 1000; i++) {
            sampleTarget = testPlayer.selectTarget(3); // choose a target
            trackTargets.put(sampleTarget, trackTargets.get(sampleTarget) + 1); // add 1 to target
        }
        for (BoardCell key : trackTargets.keySet()) {
            assertTrue(trackTargets.get(key) > 150); // five possible targets over 1000 simulations; each should be
                                                     // above 150
        }
    }
}
