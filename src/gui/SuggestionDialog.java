package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.runner.Computer;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Room;
import clueGame.Solution;
import clueGame.Board;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SuggestionDialog extends JDialog {
    JLabel leftOne;
    JLabel leftTwo;
    JLabel leftThree;
    JButton leftFour;
    JTextField rightOne;
    JComboBox<String> rightTwo;
    JComboBox<String> rightThree;
    JButton rightFour;

    public SuggestionDialog(Room currentRoom) {
        super();
        setTitle("Make a Suggestion");
        setLayout(new GridLayout(4, 5));
        leftOne = new JLabel("Current room");
        leftTwo = new JLabel("Person");
        leftThree = new JLabel("Weapon");
        leftFour = new JButton("Submit");
        rightOne = new JTextField(currentRoom.getName()); 
        rightTwo = new JComboBox<>(Board.getInstance().getPeopleCards().toArray(new String[0]));
        rightThree = new JComboBox<>(Board.getInstance().getWeaponCards().toArray(new String[0]));
        rightOne.setEditable(false);
        rightFour = new JButton("Cancel");
        rightFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
            
        });
        leftFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Solution suggestion = new Solution(rightOne.getText(), (String)rightTwo.getSelectedItem(), (String)rightThree.getSelectedItem());
                Card evidence = Board.getInstance().handleSuggestion(Board.getInstance().getCurrentPlayer(), suggestion);
                ClueGame.getBottomPanel().setGuess(suggestion.getPerson().getName() + ", " + suggestion.getRoom().getName() + ", " + suggestion.getWeapon().getName(), Board.getInstance().getCurrentPlayer().getColor());
                if (evidence == null) {
                    ClueGame.getBottomPanel().setGuessResultColor(null);
                    ClueGame.getBottomPanel().setGuessResultText("No new clue");
                } else {
                    ClueGame.getBottomPanel().setGuessResultText(evidence.getName());
                }
                ArrayList<Card> seenCopy = new ArrayList<Card>(Board.getInstance().getCurrentPlayer().getSeenCards());
                if (!seenCopy.contains(evidence) && evidence != null) {
                    ClueGame.getRightPanel().addSeen(evidence, CardsPanel.getBgColor());
                }
                Board.getInstance().getCurrentPlayer().getSeenCards().add(evidence);
                dispose();
            }
        });
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
