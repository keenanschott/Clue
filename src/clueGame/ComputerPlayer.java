package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * ComputerPlayer
 * Extension of the Player class with room for later computer functionality implementation. 
 * DATE: 4/4/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
public class ComputerPlayer extends Player {

    /**
     * ComputerPlayer()
     * At the moment, inheriting constructor behavior from parent class Player.
     * 
     * @param inName
     * @param inColor
     * @param inRow
     * @param inColumn
     */
    public ComputerPlayer(String inName, String inColor, int inRow, int inColumn) {
        super(inName, inColor, inRow, inColumn);
    }
    
    /**
     * createSuggestion()
     * The algorithm for AI suggestion creation.
     * 
     * @param currentRoom The room the computer is in upon suggestion creation.
     * @return The suggested solution.
     */
    public Solution createSuggestion(Room currentRoom) {
        Solution newSuggestion = new Solution(); // new solution
        Board board = Board.getInstance(); // access the board instance
        newSuggestion.setRoom(new Card(board.getRoom(board.getCell(getRow(), getColumn())).getName(), CardType.ROOM)); // set room to current room
        ArrayList<Card> deckCopy = new ArrayList<Card>();
        deckCopy.addAll(board.getDeck()); // deep copy the deck
        boolean weapon = false, person = false; // necessary initializations
        Random random = new Random();
        int randomInt;
        Card randomCard;
        while (weapon == false || person == false) {
            randomInt = random.nextInt(deckCopy.size());
            randomCard = deckCopy.get(randomInt); // get a random card and then remove it from the deck
            deckCopy.remove(randomInt);
            if (weapon == false && randomCard.getType() == CardType.WEAPON && !getSeenCards().contains(randomCard)) { // if no weapon card found yet and seen cards does not contain the card
                newSuggestion.setWeapon(randomCard);
                weapon = true;
            } else if (person == false && randomCard.getType() == CardType.PERSON && !getSeenCards().contains(randomCard)) { // if no person card found yet and seen cards does not contain the card
                newSuggestion.setPerson(randomCard);
                person = true;
            }
        }
        return newSuggestion; // return complete suggestion
    }

    /*
     * selectTarget()
     * The algorithm for AI target selection.
     * 
     * @param diceRoll The random dice roll.
     * @return The BoardCell to move to.
     */
    public BoardCell selectTarget(int diceRoll) {
        Board board = Board.getInstance(); // obtain the board instance
        Random random = new Random(); 
        board.calcTargets(board.getCell(getRow(), getColumn()), diceRoll); // calculate targets 
        Set<BoardCell> targets = board.getTargets(); // all targets
        ArrayList<BoardCell> smartTargets = new ArrayList<>(); // list of rooms we have yet to see, need to set to ArrayList for retrieval purposes
        ArrayList<Card> seenTemp = new ArrayList<Card>(getSeenCards()); // convert seen cards to an ArrayList to avoid hash code comparison
        // iterate through list of targets 
        for (BoardCell currentCell : targets) {
            if (currentCell.isRoomCenter() && !seenTemp.contains(new Card(board.getRoom(currentCell).getName(), CardType.ROOM))) { // if it's a room and we have not seen this room card yet
                smartTargets.add(currentCell); // we now consider this a priority target because we have yet to travel there
            }
        }
        if (!smartTargets.isEmpty()) { // if targets exist in rooms we have yet to visit
            diceRoll = random.nextInt(smartTargets.size()); // access random index of smartTargets 
            return smartTargets.get(diceRoll); 
        } else { // if no new rooms to travel to 
            diceRoll = random.nextInt(targets.size());
            ArrayList<BoardCell> accessibleTargets = new ArrayList<>(targets);
            return accessibleTargets.get(diceRoll); // go to any random target
        }
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
