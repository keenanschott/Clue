package clueGame;

/**
 * HumanPlayer
 * Extension of player class with room for later human player functionality implementation. 
 * DATE: 03/31/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
public class HumanPlayer extends Player {

    /**
     * HumanPlayer(String inName, String inColor, int inRow, int inColumn)
     * At the moment, inheriting constructor behavior from parent class Player
     * @param inName
     * @param inColor
     * @param inRow
     * @param inColumn
     */
    public HumanPlayer(String inName, String inColor, int inRow, int inColumn) {
        super(inName, inColor, inRow, inColumn);
    }
    
}
