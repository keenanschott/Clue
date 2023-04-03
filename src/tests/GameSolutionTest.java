package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class GameSolutionTest {
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

    // ensure accusations are handled correctly (correct solution and solution (weapon,room,person cases))
    @Test
    public void testAccusation() {
        // correct solution
        // solution with wrong person
        // solution with wrong weapon
        // solution with wrong room
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
