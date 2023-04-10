package clueGame;

import java.util.*;
import java.awt.*;

/**
 * BoardCell
 * This class constitutes a single cell in the game board. The grid in Board is
 * filled with these cells, and each cell has an adjacency list of adjacent
 * cells. Each cell has identifiers correlated to row, column, room initial,
 * secret passage, door direction, etc.
 * DATE: 3/26/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class BoardCell {
    private int row, col; // row and column identifiers for each cell
    private char initial, secretPassage; // chars
    private DoorDirection doorDirection = DoorDirection.NONE; // door direction for a given cell; default is NONE
    private boolean roomLabel = false, roomCenter = false, isRoom, isOccupied; // boolean statuses for a given cell;
                                                                               // defaults set for select booleans
    private Set<BoardCell> adjList; // adjacency list for a given cell

    /**
     * BoardCell()
     * Construct a cell with default values.
     * 
     * @param inputRow     The row value.
     * @param inputCol     The column value.
     * @param inputInitial The initial of the room.
     */
    public BoardCell(int inputRow, int inputCol, char inputInitial) {
        adjList = new HashSet<BoardCell>(); // allocate space for the adj. list
        row = inputRow; // given row and column
        col = inputCol;
        initial = inputInitial; // given room initial
    }

    /**
     * addAdjacency()
     * Add a cell to the adjacency list.
     * 
     * @param cell The BoardCell to add to the adjacency list.
     */
    public void addAdjacency(BoardCell cell) {
        adjList.add(cell); // add cell to the adjacency list
    }

    /**
     * isDoorway()
     * Whether or not a cell is a doorway.
     * 
     * @return Return the doorway status boolean; derived from the DoorDirection
     *         enum.
     */
    public boolean isDoorway() {
        if (doorDirection != DoorDirection.NONE) {
            return true;
        }
        return false; // if NONE, the cell is not a doorway
    }

    public void draw(Graphics g, int x, int y, int width, int height, BoardCell currentCell) {
        if (!roomLabel && !isOccupied) {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, width, height);
        }
        else if (roomLabel == true){
            Room currentRoom = Board.getInstance().getRoom(currentCell);
            String roomTitle = currentRoom.getName();
            g.drawString(roomTitle, x + 5, y + 5);
        }       
    }

    /**
     * setDoorDirection()
     * Set the direction of the doorway for a given cell.
     * 
     * @param direction The direction of the doorway.
     */
    public void setDoorDirection(char direction) {
        if (direction == '<') {
            this.doorDirection = DoorDirection.LEFT; // set left
        } else if (direction == '>') {
            this.doorDirection = DoorDirection.RIGHT; // set right
        } else if (direction == '^') {
            this.doorDirection = DoorDirection.UP; // set up
        } else if (direction == 'v') {
            this.doorDirection = DoorDirection.DOWN; // set down
        } else {
            this.doorDirection = DoorDirection.NONE; // any other character is NONE
        }
    }

    // all getters and setters
    public Set<BoardCell> getAdjList() {
        return adjList;
    }

    public DoorDirection getDoorDirection() {
        return doorDirection;
    }

    public char getInitial() {
        return initial;
    }

    public void setSecretPassage(char sp) {
        secretPassage = sp;
    }

    public char getSecretPassage() {
        return secretPassage;
    }

    public boolean isRoomCenter() {
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

}