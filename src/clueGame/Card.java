package clueGame;

/**
 * Card
 * A singular card; has a name and a type which constitute the hands of all of the players.
 * DATE: 3/31/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
public class Card {
    private String cardName; // the name associated with the card
    private CardType cardType; // the card type

    /**
	 * Card()
     * Constructor.
     * 
     * @param name The requested card name.
     * @param cardType The requested card type.
     */
    public Card(String name, CardType type) {
        super();
        cardName = name;
        cardType = type;
    }

    /**
	 * equals()
     * Compare two Card instances.
	 * 
	 * @param o The comparison object.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card)) {
            return false; // if not a Card instance; false
        }
        Card c = (Card) o; // cast
        return (c.getType() == getType() && c.getName().equals(getName())); // compare instance variables
    }

    // all getters and setters
    public CardType getType() {
        return cardType;
    }

    public String getName() {
        return cardName;
    }
}