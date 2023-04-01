package clueGame;

public class Card {
    private String cardName;
    private CardType type;

    public Card(String name, CardType cardType) {
        super();
        cardName = name;
        type = cardType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        Card c = (Card) o;
        return (c.getType() == getType() && c.getName().equals(getName()));
    }

    public CardType getType() {
        return type;
    }

    public String getName() {
        return cardName;
    }
}
