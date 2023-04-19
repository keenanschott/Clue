package gui;

import javax.swing.JFrame;

import clueGame.Board;
import java.awt.BorderLayout;

/**
 * ClueGame
 * The game controller; merges all of the GUI features into a single frame to be
 * displayed to the user. Will eventually handle game logic and flow.
 * DATE: 4/18/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class ClueGame extends JFrame {
    private CardsPanel rightPanel; // the cards panel
    private GameControlPanel bottomPanel; // the control panel

    /**
     * ClueGame()
     * Constructor for the game, initializes all panels.
     */
    public ClueGame() {
        super("Clue Game - CSCI306");
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
        setSize(1240, 1000); // set arbitrary size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // splash screen
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
        // game stuff
        Board testBoard = Board.getInstance(); // get the instance
        testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        // initialize the board
        testBoard.initialize();
        testBoard.deal();
        // make new game frame and make it visible
        ClueGame gameFrame = new ClueGame();
        gameFrame.setVisible(true);
        // display splash screen
        Splash popUp = new Splash();
        popUp.display(gameFrame);
        // initialize game and listeners
        testBoard.initializeGame(gameFrame);
    }
}
