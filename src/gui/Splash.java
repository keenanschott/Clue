package gui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import clueGame.Board;

/**
 * Splash
 * The game's opening pop up.
 * DATE: 4/18/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class Splash extends JOptionPane {
    private JLabel label; // label to add to option pane

    /**
     * Splash()
     * Constructor for pop up.
     */
    public Splash() {
        label = new JLabel("<html><center>You are " + Board.getInstance().getPlayersList().get(0).getName()
                + ".<br>Can you find the solution before the Computer players?"); // display human name in pop up
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * display()
     * Display the pop up splash screen.
     * 
     * @param frame The outer frame to center content in.
     */
    public void display(ClueGame frame) {
        JOptionPane.showMessageDialog(frame, label, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * main()
     * Main to test the panel.
     * 
     * @param args The list of arguments.
     */
    public static void main(String[] args) {
        // obtain board and initialize
        Board testBoard = Board.getInstance();
        testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        testBoard.initialize();
        testBoard.deal();
        // initialize outer frame and pop up
        ClueGame testFrame = new ClueGame();
        Splash testSplash = new Splash();
        // display
        testFrame.setVisible(true);
        testSplash.display(testFrame);
    }
}
