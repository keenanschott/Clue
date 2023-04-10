package gui;

import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clueGame.Board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

// make class static at some point
public class ClueGame extends JFrame {
    private CardsPanel rightPanel;
    private GameControlPanel bottomPanel;

    public ClueGame() {
        super("Clue Game");
        JPanel centerBoard = new JPanel();
        rightPanel = new CardsPanel();
        bottomPanel = new GameControlPanel();
        setLayout(new GridLayout(2,1));
        add(rightPanel);
        add(centerBoard);
        add(bottomPanel);
        //setContentPane(rightPanel);
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        ClueGame gameFrame = new ClueGame();
        //gameFrame.setContentPane(gameFrame.getRightPanel());
        gameFrame.setVisible(true);
    }
}
