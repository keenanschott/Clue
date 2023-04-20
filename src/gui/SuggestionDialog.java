package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import clueGame.Room;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

public class SuggestionDialog extends JDialog {
    JLabel leftOne;
    JLabel leftTwo;
    JLabel leftThree;
    JButton leftFour;
    JTextField rightOne;
    JComboBox<Integer> rightTwo;
    JComboBox<Integer> rightThree;
    JButton rightFour;

    public SuggestionDialog(Room currentRoom) {
        super();
        Integer[] testArray = {1,2,3,4};

        setTitle("Make a Suggestion");
        setLayout(new GridLayout(4, 5));
        leftOne = new JLabel("Current room");
        leftTwo = new JLabel("Person");
        leftThree = new JLabel("Weapon");
        leftFour = new JButton("Submit");
        rightOne = new JTextField(currentRoom.getName());
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
        SuggestionDialog test = new SuggestionDialog(new Room("Kitchen", null, null));
        test.setSize(500, 500);
        test.setVisible(true);
    }
    
}
