package clueGame;

/**
 * Room
 * A part of Clue Init that constitutes a single room object.
 * @author Keenan Schott
 * @author Finn Burns
 */
public class Room {
    private String name; // name of the room
    private BoardCell centerCell; // the center cell of the room
    private BoardCell labelCell; // the label cell of the room

    /**
     * Set up a room with the given information.
     */
    public Room(String inputName, BoardCell center, BoardCell label) {
        name = inputName;
        centerCell = center;
        labelCell = label;
    } 

    // all getters below
    public String getName() {
        return name;
    }
    
    public BoardCell getLabelCell() {
        return labelCell;
    }

    public BoardCell getCenterCell() {
        return centerCell;
    }
}