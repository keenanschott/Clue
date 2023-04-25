package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import clueGame.Board;
import clueGame.Solution;

public class AccusationDialog extends JDialog {
    JLabel leftOne;
    JLabel leftTwo;
    JLabel leftThree;
    JButton leftFour;
    JComboBox<String> rightOne;
    JComboBox<String> rightTwo;
    JComboBox<String> rightThree;
    JButton rightFour;

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
                dispose();
            }
        });
        leftFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Solution suggestion = new Solution((String)rightOne.getSelectedItem(), (String)rightTwo.getSelectedItem(), (String)rightThree.getSelectedItem());
                JLabel label = null;
                if (Board.getInstance().getTheAnswer().equals(suggestion)) {
                    label = new JLabel("<html><center>Congratulations, that's correct! You win!"); 
                } else {
                    label = new JLabel("<html><center>Sorry, not correct! You lose!");
                }
                label.setHorizontalAlignment(SwingConstants.CENTER);
                JOptionPane.showMessageDialog(Board.getInstance(), label, "Message", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
    }

    /**
     * testing main 
     * @param args
     */
    public static void main(String[] args) {
        AccusationDialog test = new AccusationDialog();
        test.setSize(500, 500);
        test.setVisible(true);
    }
}
