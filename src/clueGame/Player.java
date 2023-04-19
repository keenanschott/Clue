package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.*;
import java.lang.reflect.Field;

/**
 * Player
 * Game player object; players have a name, color, position on the board, seen
 * cards, and a current hand that they possess. They have the ability
 * to draw a card through the updateHand function.
 * DATE: 4/10/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public abstract class Player {
    private String name; 
    private String color; // TODO: implement color as a color object and not string method.
    private int row;
    private int column;
    private ArrayList<Card> hand;
    private Set<Card> seenCards;

    /**
     * Player()
     * Paramaterized constructor.
     * 
     * @param inName   The input name.
     * @param inColor  The input color.
     * @param inRow    The input row.
     * @param inColumn The input column.
     */
    public Player(String inName, String inColor, int inRow, int inColumn) {
        super();
        name = inName;
        color = inColor;
        row = inRow;
        column = inColumn;
        hand = new ArrayList<Card>(); // allocate space
        seenCards = new HashSet<Card>();
    }

    /**
     * draw()
     * Draw the player token, which is a circle, and color code it accordingly to
     * each player.
     * 
     * @param g      The Graphics object.
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @param width  The cell width.
     * @param height The cell height.
     */
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color color; // color object to store color we retrieve from player data
        try {
            Field field = Class.forName("java.awt.Color").getField(getColor()); // get color from String
            color = (Color) field.get(null);
        } catch (Exception e) { // if not valid color
            color = null; // failed to convert; return null
        }
        g.setColor(Color.BLACK); // border color
        g.drawOval(x, y, width, height); // draw oval with radii width and height
        g.setColor(color); // filler color
        g.fillOval(x, y, width, height);
    }

    /**
     * updateHand()
     * Update a player's hand.
     * 
     * @param card The card to add.
     */
    public void updateHand(Card card) {
        hand.add(card);
    }

    /**
     * updateSeen()
     * Update seen cards set.
     * 
     * @param seenCard The card to be seen.
     */
    public void updateSeen(Card seenCard) {
        seenCards.add(seenCard);
    }

    /**
     * disproveSuggestion()
     * The method to use when disproving a suggestion.
     * 
     * @param suggestion The suggestion to be disproven.
     */
    abstract public Card disproveSuggestion(Solution suggestion);

    // all getters and setterss
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Set<Card> getSeenCards() {
        return seenCards;
    }

    public void setHand(ArrayList<Card> inHand) {
        hand = inHand;
    }

    public void setSeenCards(Set<Card> seen) {
        seenCards = seen;
    }

    public void setLocation(int row, int col) {
        this.row = row;
        this.column = col;
    }
}
