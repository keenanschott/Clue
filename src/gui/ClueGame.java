package gui;

import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

import java.awt.BorderLayout;
import java.awt.GridLayout;

// make class static at some point
public class ClueGame extends JFrame {
    public ClueGame() {
        super("Clue Game - CSCI306");
    }

    public void initialize() {
        Board centerBoard = Board.getInstance();
        CardsPanel rightPanel = new CardsPanel();

        ArrayList<Card> tempHand = new ArrayList<Card>(); // create and fill a temporary hand
        tempHand.add(new Card("Captain Nemo", CardType.PERSON));
        tempHand.add(new Card("Anchor", CardType.WEAPON));
        tempHand.add(new Card("Propeller", CardType.WEAPON));
        HumanPlayer test1 = new HumanPlayer("Captain Haddock", "cyan", 0, 18); // create temporary human player
        test1.setHand(tempHand); // test hand creation
        rightPanel.addHand(test1);


        GameControlPanel bottomPanel = new GameControlPanel();

        bottomPanel.setTurn(new ComputerPlayer("Richard Parker", "green", 0, 0), 5);
        bottomPanel.setGuess("I have no guess!");
        bottomPanel.setGuessResult("So you have nothing?");


        //setLayout(new GridLayout(2,1));
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerBoard, BorderLayout.CENTER);
        //setContentPane(rightPanel);
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        Board testBoard = Board.getInstance();
        testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        // Initialize will load config files
        testBoard.initialize();
        testBoard.deal();
        ClueGame gameFrame = new ClueGame();
        gameFrame.initialize();
        //gameFrame.setContentPane(gameFrame.getRightPanel());
        gameFrame.setVisible(true);
    }
}
