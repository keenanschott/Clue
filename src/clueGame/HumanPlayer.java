package clueGame;

import java.util.ArrayList;
import java.util.Random;

/**
 * HumanPlayer
 * Extension of the Player class with room for later human player functionality implementation. 
 * DATE: 4/4/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
public class HumanPlayer extends Player {

    /**
     * HumanPlayer()
     * At the moment, inheriting constructor behavior from parent class Player.
     * 
     * @param inName
     * @param inColor
     * @param inRow
     * @param inColumn
     */
    public HumanPlayer(String inName, String inColor, int inRow, int inColumn) {
        super(inName, inColor, inRow, inColumn);
    }

    /**
     * disproveSuggestion()
     * Disprove a suggestion.
     * 
     * @param suggestion The suggestion to disprove.
     * @return The card that disproves the suggestion.
     */
    @Override
    public Card disproveSuggestion(Solution suggestion) {
        ArrayList<Card> matching = new ArrayList<Card>(); // allocate space for matching list
        for (Card currentCard : getHand()) { // all match conditions
            if (currentCard.getType() == CardType.PERSON && currentCard.equals(suggestion.getPerson())) {
                matching.add(currentCard);
            } else if (currentCard.getType() == CardType.ROOM && currentCard.equals(suggestion.getRoom())) {
                matching.add(currentCard);
            } else if (currentCard.getType() == CardType.WEAPON && currentCard.equals(suggestion.getWeapon())) {
                matching.add(currentCard);
            }
        }
        if (matching.size() == 1) {
            updateSeen(matching.get(0)); // add to seen
            return matching.get(0);
        } else if (matching.size() > 1) {
            Random random = new Random();
            int randomInt = random.nextInt(matching.size()); // randomly choose out of matching cards
            updateSeen(matching.get(randomInt)); // add to seen
            return matching.get(randomInt);
        }
        return null; // otherwise, return null
    }
}
