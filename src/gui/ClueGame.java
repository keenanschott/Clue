package gui;

import java.util.ArrayList;
import javax.swing.JFrame;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import java.awt.BorderLayout;

/**
 * ClueGame
 * The game controller; merges all of the GUI features into a single frame to be
 * displayed to the user. Will eventually handle game logic and flow.
 * DATE: 4/10/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class ClueGame extends JFrame {
    private CardsPanel rightPanel;
    private GameControlPanel bottomPanel;

    /**
     * ClueGame()
     * Constructor for the game, initializes all panels.
     */
    public ClueGame() {
        super("Clue Game - CSCI306"); // TODO: make class static at some point?
        // the board panel
        Board centerBoard = Board.getInstance();
        // the cards panel
        rightPanel = new CardsPanel();
        // the game control panel
        bottomPanel = new GameControlPanel();
        // all all three to the frame
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerBoard, BorderLayout.CENTER);
        setSize(1400, 1000); // set arbitrary size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // all getters and setters
    public CardsPanel getRightPanel() {
        return rightPanel;
    }

    public GameControlPanel getBottomPanel() {
        return bottomPanel;
    }

    /**
     * main()
     * Main to test the frame and eventually control game logic.
     * 
     * @param args The list of arguments.
     */
    public static void main(String[] args) {
        Board testBoard = Board.getInstance(); // get the instance
        testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        // initialize the board
        testBoard.initialize();
        testBoard.deal();
        // make new game frame and make it visible
        ClueGame gameFrame = new ClueGame();
        // add filler information
        ArrayList<Card> tempHand = new ArrayList<Card>(); // create and fill a temporary hand
        tempHand.add(new Card("Captain Nemo", CardType.PERSON));
        tempHand.add(new Card("Anchor", CardType.WEAPON));
        tempHand.add(new Card("Propeller", CardType.WEAPON));
        HumanPlayer test1 = new HumanPlayer("Captain Haddock", "cyan", 0, 18); // create temporary human player
        test1.setHand(tempHand); // test hand creation
        gameFrame.getRightPanel().addHand(test1);
        gameFrame.getBottomPanel().setTurn(new ComputerPlayer("Richard Parker", "green", 0, 0), 5);
        gameFrame.getBottomPanel().setGuess("I have no guess!");
        gameFrame.getBottomPanel().setGuessResult("So you have nothing?");
        gameFrame.setVisible(true);
    }
}
