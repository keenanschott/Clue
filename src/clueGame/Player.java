package clueGame;

public abstract class Player {
    private String name;
    private String color;
    private int row;
    private int column;
    private Card[] hand;

    public Player(String inName, String inColor, int inRow, int inColumn) {
        super();
        name = inName;
        color = inColor;
        row = inRow;
        column = inColumn;
    }

    public void updateHand(Card card) {

    }
}
