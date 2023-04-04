package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;
public class ComputerAITest {
    private static Board board;
    @BeforeEach
	public void setUp() {
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
        Room currentRoom = board.getRoom(board.getCell(2, 2)); // get the room we're currently in
        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayer("PlayerName2");
        ArrayList<Card> originalHand = new ArrayList<Card>();
        originalHand.addAll(testPlayer.getHand()); // save original hand
        testPlayer.getHand().clear(); // since hands are randomly generated, ensure there are no conflicts
        testPlayer.setLocation(2, 2); // set to middle of the room
        Solution testSuggestion = testPlayer.createSuggestion(board.getRoom(board.getCell(testPlayer.getRow(), testPlayer.getColumn()))); // create suggestion based off of current room
        assertEquals(board.getRoom(testSuggestion.getRoom()), currentRoom); // assert suggestion room is equal to current room
        // if only one weapon/person not seen, it's selected
        Set<Card> currentSeenCards = new HashSet<Card>(); // create a new set of seen cards
        int i = 0;
        Card targetCard = new Card(null, null); // create weapon card to be missing
        for (Card card : board.getDeck()) {
            if (card.getType() == CardType.WEAPON && i < 5) { // include all weapons except "WeaponName6"
                currentSeenCards.add(card);
                i++;
            } else if (i == 5 && card.getType() == CardType.WEAPON) {
                targetCard = card; // "WeaponName6"
            }
        }
        testPlayer.setSeenCards(currentSeenCards); // set seen cards
        assertEquals(testPlayer.createSuggestion(currentRoom).getWeapon(), targetCard); // suggestion should contain "WeaponName6"
        currentSeenCards = new HashSet<Card>(); // create a new set of seen cards
        i = 0;
        targetCard = new Card(null, null); // create person card to be missing
        for (Card card : board.getDeck()) {
            if (card.getType() == CardType.PERSON && i < 5) { // include all people except "PlayerName6"
                currentSeenCards.add(card);
                i++;
            } else if (i == 5 && card.getType() == CardType.PERSON) {
                targetCard = card; // "PlayerName6"
            }
        }
        testPlayer.setSeenCards(currentSeenCards); // set seen cards
        assertEquals(testPlayer.createSuggestion(currentRoom).getPerson(), targetCard); // suggestion should contain "PlayerName6"
        // if multiple weapons not seen, one is randomly selected
        ArrayList<Card> twoCards = new ArrayList<Card>();   
        currentSeenCards = new HashSet<Card>(); // create a new set of seen cards
        i = 0;
        targetCard = new Card(null, null); // create weapon cards to be missing
        for (Card card : board.getDeck()) {
            if (card.getType() == CardType.WEAPON && i < 4) { // include all weapons except "WeaponName5" and "WeaponName6"
                currentSeenCards.add(card);
                i++;
            } else if (i == 4 && card.getType() == CardType.WEAPON) {
                twoCards.add(card); // "WeaponName5" and "WeaponName6"
            }
        }
        testPlayer.setSeenCards(currentSeenCards); // set seen cards
        int five = 0, six = 0; // counters
        for (int j = 0; j < 1000; j++) {
            targetCard = testPlayer.createSuggestion(currentRoom).getWeapon();
            if (targetCard.equals(twoCards.get(0))) {
                five++; // "WeaponName5"
            } else if ((targetCard.equals(twoCards.get(1)))) {
                six++; // "WeaponName6"
            } else {
                assertEquals(true, false);
            }
        }
        assertTrue(five > 400); // assert "WeaponName5" appears more than 400 times
        assertTrue(six > 400); // assert "WeaponName6" appears more than 400 times
        // if multiple persons not seen, one is randomly selected
        twoCards = new ArrayList<Card>();   
        currentSeenCards = new HashSet<Card>(); // create a new set of seen cards
        i = 0;
        targetCard = new Card(null, null); // create person cards to be missing
        for (Card card : board.getDeck()) {
            if (card.getType() == CardType.PERSON && i < 4) { // include all weapons except "PlayerName5" and "PlayerName6"
                currentSeenCards.add(card);
                i++;
            } else if (i == 4 && card.getType() == CardType.PERSON) {
                twoCards.add(card); // "PlayerName5" and "PlayerName6"
            }
        }
        testPlayer.setSeenCards(currentSeenCards); // set seen cards
        five = 0; six = 0; // counters
        for (int j = 0; j < 1000; j++) {
            targetCard = testPlayer.createSuggestion(currentRoom).getPerson();
            if (targetCard.equals(twoCards.get(0))) {
                five++; // "PlayerName5"
            } else if ((targetCard.equals(twoCards.get(1)))) {
                six++; // "PlayerName6"
            } else {
                assertEquals(true, false);
            }
        }
        assertTrue(five > 400); // assert "PlayerName5" appears more than 400 times
        assertTrue(six > 400); // assert "PlayerName6" appears more than 400 times
        // seen cards has garbage in it, but doesn't matter for further testing
        board.getPlayer("PlayerName2").setHand(originalHand);
        board.getPlayer("PlayerName2").setLocation(0, 7); // return to original location for further testing
    }

    @Test
    public void testSelectTargets() {
        ComputerPlayer testPlayer = (ComputerPlayer)board.getPlayer("PlayerName2"); // copy over player
        board.calcTargets(board.getCell(testPlayer.getRow(),testPlayer.getColumn()), 1); // no target from 7,0 that is room
        Map<BoardCell,Integer> trackTargets = new Hashtable<>(); // map to track targets and occurences for random confirmation
        Iterator<BoardCell> iterator = board.getTargets().iterator(); // iterate over set of targets 
        BoardCell sampleTarget; 
        while (iterator.hasNext()) {
            trackTargets.put(iterator.next(),0); // populate trackTargets with each possible target and a count of 0
        }
        for (int i = 0; i < 1000; i++) {
            sampleTarget = testPlayer.selectTarget(1); // choose a target
            trackTargets.put(sampleTarget, trackTargets.get(sampleTarget) + 1); // add 1 to target
        }
        for (BoardCell key : trackTargets.keySet()) {
            assertTrue(trackTargets.get(key) > 400); // sample value of 150, depends on dice roll and number of potential targets for even split
        }
        

    }

}
