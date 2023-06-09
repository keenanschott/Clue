package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import clueGame.Board;
import clueGame.Solution;

/**
 * AccusationDialog
 * The game's dialog for a human player's accusation.
 * DATE: 4/25/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class AccusationDialog extends JDialog {
    JLabel leftOne;
    JLabel leftTwo;
    JLabel leftThree;
    JButton leftFour;
    JComboBox<String> rightOne;
    JComboBox<String> rightTwo;
    JComboBox<String> rightThree;
    JButton rightFour;

    /**
     * AccusationDialog()
     * Constructor for the dialog, it does 90% of the work.
     */
    public AccusationDialog() {
        super();
        setTitle("Make an Accusation");
        setLayout(new GridLayout(4, 5));
        leftOne = new JLabel("Room");
        leftTwo = new JLabel("Person");
        leftThree = new JLabel("Weapon");
        leftFour = new JButton("Submit");
        rightOne = new JComboBox<>(Board.getInstance().getRoomCards().toArray(new String[0]));
        rightTwo = new JComboBox<>(Board.getInstance().getPeopleCards().toArray(new String[0]));
        rightThree = new JComboBox<>(Board.getInstance().getWeaponCards().toArray(new String[0]));
        rightOne.setEditable(false);
        rightFour = new JButton("Cancel");
        add(leftOne, BorderLayout.WEST);
        add(rightOne, BorderLayout.EAST);
        add(leftTwo, BorderLayout.WEST);
        add(rightTwo, BorderLayout.EAST);
        add(leftThree, BorderLayout.WEST);
        add(rightThree, BorderLayout.EAST);
        add(leftFour, BorderLayout.WEST);
        add(rightFour, BorderLayout.EAST);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rightFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // "Cancel" button; close window
            }
        });
        leftFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Solution suggestion = new Solution((String)rightOne.getSelectedItem(), (String)rightTwo.getSelectedItem(), (String)rightThree.getSelectedItem());
                JLabel label = null;
                if (Board.getInstance().getTheAnswer().equals(suggestion)) {
                    label = new JLabel("<html><center>Congratulations, that's correct! You win!"); // won the game 
                } else {
                    label = new JLabel("<html><center>Sorry, not correct! You lose!"); // lose the game
                }
                label.setHorizontalAlignment(SwingConstants.CENTER);
                JOptionPane.showMessageDialog(Board.getInstance(), label, "Message", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0); // close program
            }
        });
    }

    /**
     * main()
     * Main to test the panel.
     * 
     * @param args The list of arguments.
     */
    public static void main(String[] args) {
        Board testBoard = Board.getInstance(); // initialize board
        testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        testBoard.initialize();
        testBoard.deal();
        AccusationDialog test = new AccusationDialog();
        test.setSize(500, 500);
        test.setVisible(true);
    }
}
