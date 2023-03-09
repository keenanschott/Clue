package clueGame;
import java.util.*;

/**
 * TestBoardCell
 * A part of Clue Init that constitutes a single cell in the game board.
 * @author Keenan Schott
 * @author Finn Burns
 */
public class BoardCell {
    private int row, col; // row and column identifiers for each cell
    private char initial, secretPassage; // chars
    private DoorDirection doorDirection = DoorDirection.NONE; // door direction for a given cell; default is NONE
    private boolean roomLabel, roomCenter, isRoom, isOccupied; // boolean statuses for a given cell
    private Set<BoardCell> adjList; // adjacency list for a given cell
    
    /**
     * Construct a cell with default values.
     * 
     * @param inputRow The row value.
     * @param inputCol The column value.
     * @param inputInitial The initial of the room.
     */
    public BoardCell(int inputRow, int inputCol, char inputInitial) {
        adjList = new HashSet<BoardCell>(); // allocate space
        row = inputRow; // given row and column
        col = inputCol;
        initial = inputInitial; // given room
        roomLabel = false; // set booleans to false as a default unless changed later
        roomCenter = false;
    }

    /**
     * Add a cell to the adjacency list.
     * 
     * @param cell The BoardCell to add to the adjacency list.
     */
    public void addAdjacency(BoardCell cell) {
        adjList.add(cell); // add cell to the adjacency list
    }

    /**
     * Get the adjacency list.
     * 
     * @return Return the adjacency list.
     */
    public Set<BoardCell> getAdjList() {
        return adjList;
    }

    /**
     * Whether or not a cell is a doorway.
     * 
     * @return Return the doorway status boolean.
     */
    public boolean isDoorway() {
        if (doorDirection != DoorDirection.NONE) {
            return true;
        }
        return false; // if NONE, the cell is not a doorway
    }

    /**
     * Get the actual door direction.
     * 
     * @return Return the doorway direction.
     */
    public DoorDirection getDoorDirection() {
        return doorDirection;
    }

    /**
     * Set the direction of the doorway for a given cell.
     * 
     * @param direction The direction of the doorway.
     */
    public void setDoorDirection(char direction) {
        if (direction == '<') {
            this.doorDirection = DoorDirection.LEFT;
        } else if (direction == '>') {
            this.doorDirection = DoorDirection.RIGHT;
        } else if (direction == '^') {
            this.doorDirection = DoorDirection.UP;
        } else if (direction == 'v') {
            this.doorDirection = DoorDirection.DOWN;
        } else {
            this.doorDirection = DoorDirection.NONE; // any other character is NONE
        }
    }

    // all getters and setters
    public char getInitial() {
        return initial;
    }

    public void setSecretPassage(char sp) {
        secretPassage = sp;
    }

    public char getSecretPassage() {
        return secretPassage;
    }

    public boolean isRoomCenter(){
		return roomCenter;
	}

    public boolean isLabel() {
        return roomLabel;
    }

    public void setLabel(boolean label) {
        roomLabel = label;
    }

    public void setCenter(boolean center) {
        roomCenter = center;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied; // set isOccupied
    }

    public boolean getIsOccupied() {
        return isOccupied; // get isOccupied
    }

    public void setIsRoom(boolean roomStatus) {
        isRoom = roomStatus; // set isRoom
    }

    public boolean getIsRoom() {
        return isRoom; // get isRoom
    }

    // could use better naming
    public boolean getCenter() {
        return roomCenter;
    }

    public boolean getLabel() {
        return roomLabel;
    }
}
