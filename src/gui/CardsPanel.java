package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

/**
 * CardsPanel
 * The right-hand side cards control panel; displays the cards in the human
 * player's hand and the seen cards for all card types (people, rooms, and
 * weapons).
 * DATE: 4/10/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class CardsPanel extends JPanel {
    // outer control panel and all three nested panel
    private JPanel peoplePanel;
    private JPanel roomsPanel;
    private JPanel weaponsPanel;
    // people components
    private JLabel peopleHand;
    private JTextField peopleHandCards;
    private JLabel peopleSeen;
    private JTextField peopleSeenCards;
    // rooms components
    private JLabel roomsHand;
    private JTextField roomsHandCards;
    private JLabel roomsSeen;
    private JTextField roomsSeenCards;
    // weapons components
    private JLabel weaponsHand;
    private JTextField weaponsHandCards;
    private JLabel weaponsSeen;
    private JTextField weaponsSeenCards;
    private static Color bgColor;

    /**
     * CardsPanel()
     * Constructor for the panel, it does 90% of the work.
     */
    public CardsPanel() {
        // initialize all instance variables (see above)
        peoplePanel = new JPanel();
        roomsPanel = new JPanel();
        weaponsPanel = new JPanel();
        // container panel
        setLayout(new GridLayout(3, 0)); // set gameControlPanel layout and other layouts
        Border tempBorder = new LineBorder(Color.BLACK);
        TitledBorder cardsPanelBorder = new TitledBorder(tempBorder, "Known Cards");
        cardsPanelBorder.setTitleJustification(TitledBorder.CENTER);
        cardsPanelBorder.setTitlePosition(TitledBorder.TOP);
        setBorder(cardsPanelBorder);
        // three nested panels
        // people
        TitledBorder peoplePanelBorder = new TitledBorder(tempBorder, "People");
        peoplePanelBorder.setTitleJustification(TitledBorder.LEFT);
        peoplePanelBorder.setTitlePosition(TitledBorder.TOP);
        peoplePanel.setBorder(peoplePanelBorder);
        add(peoplePanel, BorderLayout.NORTH);
        // people components
        peopleHand = new JLabel("In Hand:");
        peopleHandCards = new JTextField("None");
        peopleHandCards.setBackground(Color.WHITE);
        peopleHandCards.setEditable(false);
        peopleSeen = new JLabel("Seen:");
        peopleSeenCards = new JTextField("None");
        peopleSeenCards.setBackground(Color.WHITE);
        peopleSeenCards.setEditable(false);
        peoplePanel.setLayout(new GridLayout(0, 1));
        peoplePanel.add(peopleHand); // add to peoplePanel
        peoplePanel.add(peopleHandCards);
        peoplePanel.add(peopleSeen);
        peoplePanel.add(peopleSeenCards);
        // rooms
        TitledBorder roomsPanelBorder = new TitledBorder(tempBorder, "Rooms");
        roomsPanelBorder.setTitleJustification(TitledBorder.LEFT);
        roomsPanelBorder.setTitlePosition(TitledBorder.TOP);
        roomsPanel.setBorder(roomsPanelBorder);
        add(roomsPanel, BorderLayout.CENTER);
        // rooms components
        roomsHand = new JLabel("In Hand:");
        roomsHandCards = new JTextField("None");
        roomsHandCards.setBackground(Color.WHITE);
        roomsHandCards.setEditable(false);
        roomsSeen = new JLabel("Seen:");
        roomsSeenCards = new JTextField("None");
        roomsSeenCards.setBackground(Color.WHITE);
        roomsSeenCards.setEditable(false);
        roomsPanel.setLayout(new GridLayout(0, 1));
        roomsPanel.add(roomsHand); // add to roomsPanels
        roomsPanel.add(roomsHandCards);
        roomsPanel.add(roomsSeen);
        roomsPanel.add(roomsSeenCards);
        // weapons
        TitledBorder weaponsPanelBorder = new TitledBorder(tempBorder, "Weapons");
        weaponsPanelBorder.setTitleJustification(TitledBorder.LEFT);
        weaponsPanelBorder.setTitlePosition(TitledBorder.TOP);
        weaponsPanel.setBorder(weaponsPanelBorder);
        add(weaponsPanel, BorderLayout.SOUTH);
        // weapons components
        weaponsHand = new JLabel("In Hand:");
        weaponsHandCards = new JTextField("None");
        weaponsHandCards.setBackground(Color.WHITE);
        weaponsHandCards.setEditable(false);
        weaponsSeen = new JLabel("Seen:");
        weaponsSeenCards = new JTextField("None");
        weaponsSeenCards.setBackground(Color.WHITE);
        weaponsSeenCards.setEditable(false);
        weaponsPanel.setLayout(new GridLayout(0, 1));
        weaponsPanel.add(weaponsHand); // add to weaponsPanel
        weaponsPanel.add(weaponsHandCards);
        weaponsPanel.add(weaponsSeen);
        weaponsPanel.add(weaponsSeenCards);
    }

    /**
     * generateNewFieldHand()
     * Generates a new text field for the hand.
     * 
     * @param outerPanel The panel to insert the new text field into.
     * @param name       The name to go in the text field.
     */
    private void generateNewFieldHand(JPanel outerPanel, String name) {
        JTextField newField = new JTextField(name);
        newField.setBackground(Color.WHITE);
        newField.setEditable(false);
        outerPanel.add(newField); // add new field to outer panel
    }

    /**
     * generateNewFieldSeen()
     * Generates a new text field for seen cards.
     * 
     * @param color      The color to set the new text field to.
     * @param outerPanel The panel to insert the new text field into.
     * @param name       The name to go in the text field.
     */
    private void generateNewFieldSeen(Color color, JPanel outerPanel, String name) {
        JTextField newField = new JTextField(name);
        newField.setBackground(color);
        newField.setEditable(false);
        outerPanel.add(newField); // add new field to outer panel
        outerPanel.revalidate(); // revalidate the outer panel
    }

    /**
     * addHand()
     * Populates the hand text fields.
     * 
     * @param human The human player.
     */
    public void addHand(Player human) {
        for (Card currentCard : human.getHand()) { // for all of the cards in the human player's hand
            if (currentCard.getType() == CardType.PERSON) { // person
                if (peopleHandCards.getText().equals("None")) { // first occurrence
                    peopleHandCards.setText(currentCard.getName()); // change text
                } else {
                    peoplePanel.removeAll(); // reconstruct panel
                    peoplePanel.add(peopleHand);
                    peoplePanel.add(peopleHandCards);
                    generateNewFieldHand(peoplePanel, currentCard.getName()); // generate new text field
                    peoplePanel.add(peopleSeen);
                    peoplePanel.add(peopleSeenCards);
                }
            } else if (currentCard.getType() == CardType.ROOM) { // room
                if (roomsHandCards.getText().equals("None")) { // first occurrence
                    roomsHandCards.setText(currentCard.getName()); // change text
                } else {
                    roomsPanel.removeAll(); // reconstruct panel
                    roomsPanel.add(roomsHand);
                    roomsPanel.add(roomsHandCards);
                    generateNewFieldHand(roomsPanel, currentCard.getName()); // generate new text field
                    roomsPanel.add(roomsSeen);
                    roomsPanel.add(roomsSeenCards);
                }
            } else { // weapon
                if (weaponsHandCards.getText().equals("None")) { // first occurrence
                    weaponsHandCards.setText(currentCard.getName()); // change text
                } else {
                    weaponsPanel.removeAll(); // reconstruct panel
                    weaponsPanel.add(weaponsHand);
                    weaponsPanel.add(weaponsHandCards);
                    generateNewFieldHand(weaponsPanel, currentCard.getName()); // generate new text field
                    weaponsPanel.add(weaponsSeen);
                    weaponsPanel.add(weaponsSeenCards);
                }
            }
        }
    }

    /**
     * addSeen()
     * Populates the seen text fields.
     * 
     * @param computer A computer player.
     */
    private void addSeen(ComputerPlayer computer) {
        for (Card currentCard : computer.getHand()) { // for all of the cards in the computer player's hand
            if (currentCard.getType() == CardType.PERSON) { // person
                if (peopleSeenCards.getText().equals("None")) { // first occurrence
                    peopleSeenCards.setText(currentCard.getName()); // change text
                    peopleSeenCards.setBackground(computer.getColor()); // change background color
                } else {
                    generateNewFieldSeen(computer.getColor(), peoplePanel, currentCard.getName()); // generate a new field
                }
            } else if (currentCard.getType() == CardType.ROOM) { // room
                if (roomsSeenCards.getText().equals("None")) { // first occurrence
                    roomsSeenCards.setText(currentCard.getName()); // change text
                    roomsSeenCards.setBackground(computer.getColor()); // change background color
                } else {
                    generateNewFieldSeen(computer.getColor(), roomsPanel, currentCard.getName()); // generate a new field
                }
            } else { // weapon
                if (weaponsSeenCards.getText().equals("None")) { // first occurrence
                    weaponsSeenCards.setText(currentCard.getName()); // change text
                    weaponsSeenCards.setBackground(computer.getColor()); // change background color
                } else {
                    generateNewFieldSeen(computer.getColor(), weaponsPanel, currentCard.getName()); // generate a new field
                }
            }
        }
    }

    /**
     * addSeen()
     * Populates the seen text fields.
     * 
     * @param computer A computer player.
     * @param bcGolor The target color.
     */
    public void addSeen(Card evidence, Color bgColor) {
        if (evidence.getType() == CardType.PERSON) { // person
            if (peopleSeenCards.getText().equals("None")) { // first occurrence
                peopleSeenCards.setText(evidence.getName()); // change text
                peopleSeenCards.setBackground(bgColor); // change background color
            } else {
                generateNewFieldSeen(bgColor, peoplePanel, evidence.getName()); // generate a new field
            }
        } else if (evidence.getType() == CardType.ROOM) { // room
            if (roomsSeenCards.getText().equals("None")) { // first occurrence
                roomsSeenCards.setText(evidence.getName()); // change text
                roomsSeenCards.setBackground(bgColor); // change background color
            } else {
                generateNewFieldSeen(bgColor, roomsPanel, evidence.getName()); // generate a new field
            }
        } else { // weapon
            if (weaponsSeenCards.getText().equals("None")) { // first occurrence
                weaponsSeenCards.setText(evidence.getName()); // change text
                weaponsSeenCards.setBackground(bgColor); // change background color
            } else {
                generateNewFieldSeen(bgColor, weaponsPanel, evidence.getName()); // generate a new field
            }
        }
    }

    public static Color getBgColor() {
        return bgColor;
    }

    public static void setBgColor(Color bgColor) {
        CardsPanel.bgColor = bgColor;
    }

    /**
     * main()
     * Main to test the panel.
     * 
     * @param args The list of arguments.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(); // create the frame
        CardsPanel panel = new CardsPanel(); // create the panel
        frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(250, 600); // size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        // test filling in the datas
        ArrayList<Card> tempHand = new ArrayList<Card>(); // create and fill a temporary hand
        tempHand.add(new Card("Captain Nemo", CardType.PERSON));
        tempHand.add(new Card("Anchor", CardType.WEAPON));
        tempHand.add(new Card("Propeller", CardType.WEAPON));
        HumanPlayer test1 = new HumanPlayer("Captain Haddock", "cyan", 0, 18); // create temporary human player
        test1.setHand(tempHand); // test hand creation
        panel.addHand(test1);
        // test for seen cards
        // no need to create fully fledged calls to clueGame, make temporary computer
        // players and read from their hand to test GUI for this instance only
        if (true) { // change to false to not see this stage of GUI testing
            ComputerPlayer test2 = new ComputerPlayer("Richard Parker", "green", 0, 7); // new computer player hand
            tempHand = new ArrayList<Card>();
            tempHand.add(new Card("Captain Haddock", CardType.PERSON));
            tempHand.add(new Card("Galley", CardType.ROOM));
            tempHand.add(new Card("Harpoon", CardType.WEAPON));
            test2.setHand(tempHand);
            panel.addSeen(test2); // add to seen for testing
            ComputerPlayer test3 = new ComputerPlayer("Patrick Star", "orange", 24, 25); // new computer player hand
            tempHand = new ArrayList<Card>();
            tempHand.add(new Card("Richard Parker", CardType.PERSON));
            tempHand.add(new Card("Swimming Pool", CardType.ROOM));
            tempHand.add(new Card("Captain's Quarters", CardType.ROOM));
            test3.setHand(tempHand);
            panel.addSeen(test3); // add to seen for testing
            ComputerPlayer test4 = new ComputerPlayer("Owen Chase", "yellow", 24, 18); // new computer player hand
            tempHand = new ArrayList<Card>();
            tempHand.add(new Card("Patrick Star", CardType.PERSON));
            tempHand.add(new Card("Sun Deck", CardType.ROOM));
            tempHand.add(new Card("Engine Room", CardType.ROOM));
            test4.setHand(tempHand);
            panel.addSeen(test4); // add to seen for testing
            ComputerPlayer test5 = new ComputerPlayer("Captain Ahab", "magenta", 24, 8); // new computer player hand
            tempHand = new ArrayList<Card>();
            tempHand.add(new Card("Owen Chase", CardType.PERSON));
            tempHand.add(new Card("Bridge", CardType.ROOM));
            tempHand.add(new Card("Dining Hall", CardType.ROOM));
            test5.setHand(tempHand);
            panel.addSeen(test5); // add to seen for testing
            ComputerPlayer test6 = new ComputerPlayer("Captain Nemo", "pink", 17, 0); // new computer player hand
            tempHand = new ArrayList<Card>();
            tempHand.add(new Card("Head", CardType.ROOM));
            tempHand.add(new Card("Cutlass", CardType.WEAPON));
            tempHand.add(new Card("Speargun", CardType.WEAPON));
            test6.setHand(tempHand);
            panel.addSeen(test6); // add to seen for testing
        }
        frame.setVisible(true); // make it visible
    }
}