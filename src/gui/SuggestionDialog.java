package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Room;
import clueGame.Solution;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * SuggestionDialog
 * The game's dialog for a human player's suggestion.
 * DATE: 4/25/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
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
                dispose(); // "Cancel" button functionality
            }
        });
        leftFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Solution suggestion = new Solution(rightOne.getText(), (String)rightTwo.getSelectedItem(), (String)rightThree.getSelectedItem());
                if (!suggestion.getPerson().equals(new Card(Board.getInstance().getCurrentPlayer().getName(), CardType.PERSON))) {
                    Board.getInstance().moveSuggestedPlayer(Board.getInstance().getCurrentPlayer(), suggestion); // move suggested player
                }
                Card evidence = Board.getInstance().handleSuggestion(Board.getInstance().getCurrentPlayer(), suggestion);
                // guess is concatenation of person name, room, and weapon, and we pass in color of that player
                ClueGame.getBottomPanel().setGuess(suggestion.getPerson().getName() + ", " + suggestion.getRoom().getName() + ", " + suggestion.getWeapon().getName(), Board.getInstance().getCurrentPlayer().getColor());
                if (evidence == null) {
                    ClueGame.getBottomPanel().setGuessResultColor(null);
                    ClueGame.getBottomPanel().setGuessResultText("No new clue");
                // display guess result as evidence
                } else {
                    ClueGame.getBottomPanel().setGuessResultText(evidence.getName());
                }
                // update seen
                ArrayList<Card> seenCopy = new ArrayList<Card>(Board.getInstance().getCurrentPlayer().getSeenCards());
                if (!seenCopy.contains(evidence) && evidence != null) {
                    ClueGame.getRightPanel().addSeen(evidence, CardsPanel.getBgColor());
                }
                Board.getInstance().getCurrentPlayer().getSeenCards().add(evidence);
                dispose(); // close window
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
        SuggestionDialog test = new SuggestionDialog(new Room("Kitchen", null, null));
        test.setSize(500, 500);
        test.setVisible(true);
    }   
}
