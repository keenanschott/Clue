package gui;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import java.awt.BorderLayout;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private int playerTurn = 0;
    private boolean nextPlayer = false;

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

        bottomPanel.getTopFour().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerTurn++;
                bottomPanel.revalidate();
                nextPlayer = true;
            }
        });

        // all all three to the frame
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerBoard, BorderLayout.CENTER);
        setSize(1400, 1000); // set arbitrary size
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

    public int getPlayerTurn() {
        return playerTurn;
    }

    public boolean getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(boolean nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    /**
     * main()
     * Main to test the frame and eventually control game logic.
     * 
     * @param args The list of arguments.
     */
    public static void main(String[] args) {
        Random random = new Random();



        // game stuff
        Board testBoard = Board.getInstance(); // get the instance
        testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        // initialize the board
        testBoard.initialize();
        testBoard.deal();
        // make new game frame and make it visible
        ClueGame gameFrame = new ClueGame();
        // add information
        gameFrame.getRightPanel().addHand(testBoard.getPlayersList().get(gameFrame.getPlayerTurn() % testBoard.getPlayersList().size()));
        gameFrame.setVisible(true);
        // display splash screen
        JLabel label = new JLabel("<html><center>You are " + testBoard.getPlayersList().get(0).getName() + ".<br>Can you find the solution before the Computer players?");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(gameFrame, label, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE); // TODO: put in Constructor once game logic completed
        gameFrame.getBottomPanel().setGuess("I have no guess!");
        gameFrame.getBottomPanel().setGuessResult("So you have nothing?");
        while(true) {
            gameFrame.setNextPlayer(false);
            int currentRoll = random.nextInt(1, 7);
            while (gameFrame.getNextPlayer() == false) {
                gameFrame.getBottomPanel().setTurn(testBoard.getPlayersList().get(gameFrame.getPlayerTurn() % testBoard.getPlayersList().size()), currentRoll);
            }
        }
    }
}
