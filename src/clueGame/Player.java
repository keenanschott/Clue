package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Player
 * Game player object; players have a name, color, position on the board, and a current hand that they possess. They have the ability
 * to draw a card through the updateHand function.
 * DATE: 3/31/2023
 * @author Keenan Schott    
 * @author Finn Burns
 */
public abstract class Player {
    private String name;
    private String color;
    private int row;
    private int column;
    private ArrayList<Card> hand;
    private Set<Card> seenCards;

    /**
     * Player(String inName, String inColor, int inRow, int inColumn)
     * Constructor
     * @param inName
     * @param inColor
     * @param inRow
     * @param inColumn
     */
    public Player(String inName, String inColor, int inRow, int inColumn) {
        super();
        name = inName;
        color = inColor;
        row = inRow;
        column = inColumn;
        hand = new ArrayList<Card>();
        seenCards = new HashSet<Card>();
    }

    /**
     * updateHand(Card card)
     * update players hand
     * @param card
     */
    public void updateHand(Card card) {
        hand.add(card);
    }

    /**
     * updateSeen(Card card)
     * update seen set
     * @param seenCard 
     */
    public void updateSeen(Card seenCard) {
        seenCards.add(seenCard);
    }

    /**
     * UPDATE
     * UPDATE
     */
    abstract public Card disproveSuggestion(Solution suggestion);

    /**
     * getName()
     * simple getter function for name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * getColor()
     * simple getter function for color
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     * getRow()
     * simple getter function for player's row
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     * getColumn()
     * simple getter function for player's column
     * @return
     */
    public int getColumn() {
        return column;
    }

    /**
     * getHand()
     * simple getter function for player's hand
     * @return
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    public Set<Card> getSeenCards() {
        return seenCards;
    }
}
