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
    private boolean roomLabel, roomCenter; // booleans
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

    @Override
    public String toString() {
        return row + ", " + col + ", " + initial + ", " + secretPassage + ", " + roomLabel + ", " + roomCenter + ", " + doorDirection;
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

    // TODO: revisit the below function stubs
    public boolean isDoorway() {
        if (doorDirection != DoorDirection.NONE) {
            return true;
        }
        return false;
    }

    public DoorDirection getDoorDirection() {
        return doorDirection;
    }

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
            this.doorDirection = DoorDirection.NONE;
        }
    }

    public void setInitial(char ini) {
        initial = ini;
    }

    public char getInitial() {
        return initial;
    }

    public void setSecretPassage(char sp) {
        secretPassage = sp;
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

    public char getSecretPassage() {
        return secretPassage;
    }
}
