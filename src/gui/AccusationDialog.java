package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class AccusationDialog extends JDialog {
    JLabel leftOne;
    JLabel leftTwo;
    JLabel leftThree;
    JButton leftFour;
    JComboBox<Integer> rightOne;
    JComboBox<Integer> rightTwo;
    JComboBox<Integer> rightThree;
    JButton rightFour;

    public AccusationDialog() {
        super();
        Integer[] testArray = {1,2,3,4};
        setTitle("Make an Accusation");
        setLayout(new GridLayout(4, 5));
        leftOne = new JLabel("Current room");
        leftTwo = new JLabel("Person");
        leftThree = new JLabel("Weapon");
        leftFour = new JButton("Submit");
        rightOne = new JComboBox<>(testArray);
        rightTwo = new JComboBox<>(testArray);
        rightThree = new JComboBox<>(testArray);
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
    }

    public static void main(String[] args) {
        AccusationDialog test = new AccusationDialog();
        test.setSize(500, 500);
        test.setVisible(true);
    }
}
