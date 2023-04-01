package clueGame;

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
    
}
