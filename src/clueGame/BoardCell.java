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
    private char initial, secretPassage; // chars we'll need later
    private DoorDirection doorDirection; // door direction for a given cell
    private boolean roomLabel, roomCenter; // booleans we'll need later
    private Set<BoardCell> adjList; // adjacency list for a given cell
    
    /**
     * Construct a cell with default values.
     * 
     * @param inputRow The row value.
     * @param inputCol The column value.
     */
    public BoardCell(int inputRow, int inputCol) {
        row = inputRow; // given row and column
        col = inputCol;
        adjList = new HashSet<BoardCell>(); // allocate space
        roomLabel = false; // set booleans to false as a default
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

    // TODO: revisit the below function stubs
    public boolean isDoorway() {
        return false;
    }

    public DoorDirection getDoorDirection() {
        return doorDirection;
    }

    public boolean isRoomCenter(){
		return roomCenter;
	}

    public boolean isLabel() {
        return roomLabel;
    }

    public char getSecretPassage() {
        return secretPassage;
    }
}
