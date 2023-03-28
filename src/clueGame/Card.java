package clueGame;

public class Card {
    private String cardName;
    private CardType type;

    public Card(String name, CardType cardType) {
        super();
        cardName = name;
        type = cardType;
    }

    public boolean equals(Card target) {
        if (this.cardName == target.cardName) {
            return true;
        }
        return false;
    }
}
