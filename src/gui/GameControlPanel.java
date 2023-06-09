package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import clueGame.ComputerPlayer;
import clueGame.Player;

/**
 * GameControlPanel
 * The bottom game control panel; displays whose turn it is, what the roll is,
 * what the guess is, what the guess result is, and buttons for making an
 * accusation or moving to the next player.
 * DATE: 4/10/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class GameControlPanel extends JPanel {
    // top panel
    private JPanel topPanel;
    // first top panel and its components
    private JPanel topOne;
    private JLabel topOneLabel;
    private JTextField topOneText;
    // second top panel and its components
    private JPanel topTwo;
    private JLabel topTwoLabel;
    private JTextField topTwoText;
    // third top panel and its components
    private JButton topThree;
    // fourth top panel and its components
    private JButton topFour;
    // bottom panel
    private JPanel bottomPanel;
    // first bottom panel and its components
    private JPanel bottomOne;
    private JTextField bottomOneText;
    // second bottom panel and its components
    private JPanel bottomTwo;
    private JTextField bottomTwoText;

    /**
     * GameControlPanel()
     * Constructor for the panel, it does 90% of the work.
     */
    public GameControlPanel() {
        // initialize all instance variables (see above)
        topPanel = new JPanel();
        topOne = new JPanel();
        topOneLabel = new JLabel("Whose turn?");
        topOneText = new JTextField();
        topTwo = new JPanel();
        topTwoLabel = new JLabel("Roll:");
        topTwoText = new JTextField();
        topThree = new JButton();
        topFour = new JButton();
        bottomPanel = new JPanel();
        bottomOne = new JPanel();
        bottomOneText = new JTextField();
        bottomTwo = new JPanel();
        bottomTwoText = new JTextField();
        // set layouts
        setLayout(new GridLayout(2, 0));
        topPanel.setLayout(new GridLayout(1, 4));
        topOne.setLayout(new GridLayout(2, 0));
        topTwo.setLayout(new GridLayout(0, 2));
        bottomPanel.setLayout(new GridLayout(0, 2));
        bottomOne.setLayout(new GridLayout(1, 0));
        bottomTwo.setLayout(new GridLayout(1, 0));
        // topOne details
        topOneText.setEditable(false);
        topOneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topOne.add(topOneLabel, BorderLayout.NORTH);
        topOne.add(topOneText, BorderLayout.SOUTH);
        // topTwo details
        topTwoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topTwoText.setEditable(false);
        topTwo.add(topTwoLabel, BorderLayout.WEST);
        topTwo.add(topTwoText, BorderLayout.EAST);
        // topThree details
        topThree.setText("Make Accustation");
        // topFour details
        topFour.setText("NEXT!");
        // add all of them to topPanel
        topPanel.add(topOne, BorderLayout.WEST);
        topPanel.add(topTwo, BorderLayout.AFTER_LAST_LINE);
        topPanel.add(topThree, BorderLayout.AFTER_LAST_LINE);
        topPanel.add(topFour, BorderLayout.EAST);
        // temporary border
        Border tempBorder = new LineBorder(Color.BLACK);
        // bottomOne details
        bottomOneText.setEditable(false);
        TitledBorder bottomOneBorder = new TitledBorder(tempBorder, "Guess");
        bottomOneBorder.setTitleJustification(TitledBorder.LEFT);
        bottomOneBorder.setTitlePosition(TitledBorder.TOP);
        bottomOne.setBorder(bottomOneBorder);
        bottomOne.add(bottomOneText, BorderLayout.CENTER);
        // bottomTwo details
        bottomTwoText.setEditable(false);
        TitledBorder bottomTwoBorder = new TitledBorder(tempBorder, "Guess Result");
        bottomTwoBorder.setTitleJustification(TitledBorder.LEFT);
        bottomTwoBorder.setTitlePosition(TitledBorder.TOP);
        bottomTwo.setBorder(bottomTwoBorder);
        bottomTwo.add(bottomTwoText, BorderLayout.CENTER);
        // add all of them to bottomPanel
        bottomPanel.add(bottomOne);
        bottomPanel.add(bottomTwo);
        // add topPanel and bottomPanel to gameControlPanel
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * setTurn()
     * Set the player, background color, and roll.
     * 
     * @param inPlayer The input ComputerPlayer.
     * @param roll     The dice roll.
     */
    public void setTurn(Player inPlayer, int roll) {
        topOneText.setText(inPlayer.getName()); // set player text and color
        topOneText.setBackground(inPlayer.getColor());
        topTwoText.setText(Integer.toString(roll)); // set roll text
    }

    /**
     * reset()
     * Reset all fields.
     */
    public void reset() {
        bottomOneText.setText(null); 
        bottomOneText.setBackground(null);
        bottomTwoText.setText(null);
        bottomTwoText.setBackground(null);
    }    

    /**
     * setGuess()
     * Set the guess text field and color.
     * 
     * @param inString The input guess.
     * @param bgColor The input color.
     */
    public void setGuess(String inString, Color bgColor) {
        bottomOneText.setText(inString); // set text
        bottomOneText.setBackground(bgColor);
    }

    /**
     * setGuessResultText()
     * Set the guess result text field.
     * 
     * @param inString The input guess result.
     */
    public void setGuessResultText(String inString) {
        bottomTwoText.setText(inString); // set text
    }

    /**
     * setGuessResultColor()
     * Set the guess result field color.
     * 
     * @param bgColor The target color.
     */
    public void setGuessResultColor(Color bgColor) {
        bottomTwoText.setBackground(bgColor);
    }  

    /**
     * getTopThree()
     * @return topThree JButton; accusation button.
     */
    public JButton getTopThree() {
        return topThree;
    }
    /**
     * getTopFour()
     * Return the "NEXT!" button to add listeners to game flow logic elsewhere. 
     */
    public JButton getTopFour() {
        return topFour;
    }

    /**
     * main()
     * Main to test the panel.
     * 
     * @param args The list of arguments.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(); // create the frame
        GameControlPanel panel = new GameControlPanel(); // create the panel
        frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(750, 180); // size the frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // allow it to close
        frame.setVisible(true); // make it visible
        // test filling in the data
        panel.setTurn(new ComputerPlayer("Richard Parker", "green", 0, 0), 5);
        panel.setGuess("I have no guess!", null);
        panel.setGuessResultColor(null);
        panel.setGuessResultText("So you have nothing?");
    }
}