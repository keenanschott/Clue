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
        Room currentRoom = board.getRoom(board.getCell(2, 2)); // get the room we're currently in
        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayer("PlayerName2");
        testPlayer.setLocation(2, 2); // set to middle of the room
        Solution testSuggestion = testPlayer.createSuggestion(board.getRoom(board.getCell(testPlayer.getRow(), testPlayer.getColumn()))); // create suggestion based off of current room
        assertEquals(board.getRoom(testSuggestion.getRoom()), currentRoom);
        // if only one weapon/person not seen, it's selected
        Set<Card> currentSeenCards = testPlayer.getSeenCards();
        int i = 0;
        currentSeenCards = new HashSet<Card>();
        Card targetCard = new Card(null, null);
        for (Card card : board.getDeck()) {
            if (card.getType() == CardType.WEAPON && i < 5) { // include all weapons except "WeaponName6"
                currentSeenCards.add(card);
                i++;
            } else if (i == 5 && card.getType() == CardType.WEAPON) {
                targetCard = card;
            }
        }
        assertEquals(testPlayer.createSuggestion(currentRoom).getWeapon(), targetCard);
        // if multiple weapons not seen, one is randomly selected
        
        // if multiple persons not seen, one is randomly selected



        board.getPlayer("PlayerName2").setLocation(7, 0); // return to original location
    }

    @Test
    public void testSelectTargets() {
        // if no rooms in list, select randomly
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
            assertTrue(trackTargets.get(key) > 150); // sample value of 150, depends on dice roll and number of potential targets for even split
        }
        
        // if room in list that has not been seen, select it
        testPlayer = (ComputerPlayer)board.getPlayer("PlayerName3");
        assertEquals(testPlayer.selectTarget(2), board.getCell(2, 25));

        // if room in list that has been seen, each target (room included) select randomly
        

    }

}
