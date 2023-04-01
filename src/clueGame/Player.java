package clueGame;

import java.util.ArrayList;

public abstract class Player {
    private String name;
    private String color;
    private int row;
    private int column;
    private ArrayList<Card> hand;

    public Player(String inName, String inColor, int inRow, int inColumn) {
        super();
        name = inName;
        color = inColor;
        row = inRow;
        column = inColumn;
        hand = new ArrayList<Card>();
    }

    public void updateHand(Card card) {
        hand.add(card);
    }

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

}
