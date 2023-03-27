package clueGame;

/**
 * Room
 * This class constitutes a single room. It has a center cell and a label cell along with a name. In Board, all rooms are put into a Map to be drawn from later.
 * DATE: 3/26/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
public class Room {
    private String name; // name of the room
    private BoardCell centerCell; // the center cell of the room
    private BoardCell labelCell; // the label cell of the room

    /**
     * Set up a room with the given information.
     * 
     * @param inputName Inputted name for the room.
     * @param center Given center cell for the room. 
     * @param label Given label cell for the room. 
     */
    public Room(String inputName, BoardCell center, BoardCell label) {
        name = inputName;
        centerCell = center;
        labelCell = label;
    } 

    // all getters and setters below
    public String getName() {
        return name;
    }
    
    public BoardCell getLabelCell() {
        return labelCell;
    }

    public BoardCell getCenterCell() {
        return centerCell;
    }

    public void setLabelCell(BoardCell label) {
        labelCell = label;
    }

    public void setCenterCell(BoardCell center) {
        centerCell = center;
    }
}
