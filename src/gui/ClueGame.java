package gui;

import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import clueGame.Board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

// make class static at some point
public class ClueGame extends JFrame {
    public ClueGame() {
        super("Clue Game");
    }

    public void initialize() {
        //JPanel centerBoard = Board.getInstance();
        CardsPanel rightPanel = new CardsPanel();
        GameControlPanel bottomPanel = new GameControlPanel();
        //setLayout(new Border(2,1));
        add(rightPanel, BorderLayout.EAST);
        //add(centerBoard, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        //setContentPane(rightPanel);
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        ClueGame gameFrame = new ClueGame();
        gameFrame.initialize();
        //gameFrame.setContentPane(gameFrame.getRightPanel());
        gameFrame.setVisible(true);
    }
}
