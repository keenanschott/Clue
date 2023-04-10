package gui;

import java.awt.LayoutManager;

import javax.swing.JFrame;

import clueGame.Board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class ClueGame extends JFrame {
    private CardsPanel rightPanel = new CardsPanel();
    private GameControlPanel bottomPanel = new GameControlPanel();
    int height;
    int width;

    public ClueGame() {
        super();

        setLayout(new GridLayout(0, 1));
        add(rightPanel, BorderLayout.EAST);
        rightPanel.setVisible(true);
        add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(rightPanel);
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public CardsPanel getRightPanel() {
        return rightPanel;
    }

    public GameControlPanel getBottomPanel() {
        return bottomPanel;
    }

    
    public static void main(String[] args) {
        ClueGame gameFrame = new ClueGame();
        gameFrame.setContentPane(gameFrame.getRightPanel());
        gameFrame.setVisible(true);
        Board board = Board.getInstance();
    }
}
