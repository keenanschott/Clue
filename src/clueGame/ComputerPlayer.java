package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * ComputerPlayer
 * Extension of player class with room for later computer functionality implementation. 
 * DATE: 03/31/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
public class ComputerPlayer extends Player {

    /**
     * ComputerPlayer(String inName, String inColor, int inRow, int inColumn)
     * At the moment, inheriting constructor behavior from parent class Player
     * @param inName
     * @param inColor
     * @param inRow
     * @param inColumn
     */
    public ComputerPlayer(String inName, String inColor, int inRow, int inColumn) {
        super(inName, inColor, inRow, inColumn);
    }
    
    public Solution createSuggestion(Room currentRoom) {
        Solution newSuggestion = new Solution();
        Board board = Board.getInstance();
        newSuggestion.setRoom(new Card(board.getRoom(board.getCell(getRow(), getColumn())).getName(), CardType.ROOM));
        ArrayList<Card> deckCopy = board.getDeck();
        boolean weapon = false, person = false;
        Random random = new Random();
        int randomInt;
        Card randomCard;
        while (weapon == false && person == false) {
            randomInt = random.nextInt(deckCopy.size());
            randomCard = deckCopy.get(randomInt);
            if (weapon == false && randomCard.getType() == CardType.WEAPON && !getSeenCards().contains(randomCard) && !getHand().contains(randomCard)) {
                newSuggestion.setWeapon(randomCard);
                weapon = true;
            } else if (person == false && randomCard.getType() == CardType.PERSON && !getSeenCards().contains(randomCard) && !getHand().contains(randomCard)) {
                newSuggestion.setPerson(randomCard);
                person = true;
            }
        }
        return newSuggestion;
    }

    // TODO: figure out how to generate random roll
    public BoardCell selectTarget(int diceroll) {
        Board board = Board.getInstance();
        Random random = new Random(); 
        // int randomIdx = random.nextInt(6); // variable to store random index/path length
        board.calcTargets(board.getCell(getRow(), getColumn()), diceroll); //TODO change from 6 to something else
        Set<BoardCell> targets = board.getTargets();
        ArrayList<BoardCell> smartTargets = new ArrayList<>(); // list of targets in rooms we have yet to see, need to set to ArrayList for retrieval purposes
        
        // iterate through list of targets 
        for (BoardCell currentCell : targets) {
            if (currentCell.isRoomCenter() && !getSeenCards().contains(new Card(board.getRoom(currentCell).getName(), CardType.ROOM))) { // if it's a room and we have not seen this room card yet
                smartTargets.add(currentCell); // we now consider this a target because we have yet to travel there
            }
        }
        if (!smartTargets.isEmpty()) { // if targets exist in rooms we have yet to visit 
            diceroll = random.nextInt(smartTargets.size()); // access random index of smartTargets
            return smartTargets.get(diceroll);
        } else { // if no new rooms to travel to 
            diceroll = random.nextInt(targets.size());
            ArrayList<BoardCell> accessibleTargets = new ArrayList<>(targets);
            return accessibleTargets.get(diceroll);
        }
    }

    @Override
    public Card disproveSuggestion(Solution suggestion) {
        ArrayList<Card> matching = new ArrayList<Card>();
        for (Card currentCard : getHand()) {
            if (currentCard.getType() == CardType.PERSON && currentCard.equals(suggestion.getPerson())) {
                matching.add(currentCard);
            } else if (currentCard.getType() == CardType.ROOM && currentCard.equals(suggestion.getRoom())) {
                matching.add(currentCard);
            } else if (currentCard.getType() == CardType.WEAPON && currentCard.equals(suggestion.getWeapon())) {
                matching.add(currentCard);
            }
        }
        if (matching.size() == 1) {
            updateSeen(matching.get(0));
            return matching.get(0);
        } else if (matching.size() > 1) {
            Random random = new Random();
            int randomInt = random.nextInt(matching.size());
            updateSeen(matching.get(randomInt));
            return matching.get(randomInt);
        }
        return null;
    }
}
