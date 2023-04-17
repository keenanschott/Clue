package gui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import clueGame.Board;

public class Splash extends JOptionPane {
    private JLabel label;

    public Splash() {
        Board testBoard = Board.getInstance();
        label = new JLabel("<html><center>You are " + testBoard.getPlayersList().get(0).getName() + ".<br>Can you find the solution before the Computer players?");
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void display(ClueGame frame) {
        JOptionPane.showMessageDialog(frame, label, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        Board testBoard = Board.getInstance();
        testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        testBoard.initialize();
        testBoard.deal();
        ClueGame testFrame = new ClueGame();
        Splash testSplash = new Splash();
        testSplash.display(testFrame);
    }
}
