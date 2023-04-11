package clueGame;

import java.util.*;
import java.awt.*;

/**
 * BoardCell
 * This class constitutes a single cell in the game board. The grid in Board is
 * filled with these cells, and each cell has an adjacency list of adjacent
 * cells. Each cell has identifiers correlated to row, column, room initial,
 * secret passage, door direction, etc.
 * DATE: 4/10/2023
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

    /**
     * draw()
     * Draw in the rooms, walkways, and bad spaces/closets independently.
     * 
     * @param g      The Graphics object.
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @param width  The cell width.
     * @param height The cell height.
     */
    public void draw(Graphics g, int x, int y, int width, int height) {
        if (isRoom) { // cell is room
            g.setColor(Color.GRAY); // filler
            g.fillRect(x, y, width, height);
        } else if (getInitial() == 'W') { // cell is walkway
            g.setColor(Color.YELLOW); // border
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK); // filler
            g.drawRect(x, y, width, height);
        } else { // cell is bad space/closet
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.fillRect(x, y, width, height);
        }
    }

    /**
     * drawLabel()
     * Display the label as a String for each room.
     * 
     * @param g The Graphics object
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void drawLabel(Graphics g, int x, int y) {
        g.setColor(Color.BLUE); // all labels are blue
        g.setFont(new Font("Tahoma", Font.PLAIN, 16)); // stylistic font
        Room currentRoom = Board.getInstance().getRoom(this); // get room so we can access room name
        String roomTitle = currentRoom.getName();
        g.drawString(roomTitle, x, y); // draw label using roomTitle
    }

    /**
     * drawDoor()
     * Draws the doorway symbols in blue.
     * 
     * @param g      The Graphics object.
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @param width  The cell width.
     * @param height The cell height.
     */
    public void drawDoor(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLUE); // all doorways are blue
        if (this.getDoorDirection() == DoorDirection.DOWN) {
            g.fillRect(x, y + height, width, height / 5); // draw in cell next cell up, then scale down height so that
                                                          // doorway appears on bottom edge of cell
        } else if (this.getDoorDirection() == DoorDirection.UP) {
            g.fillRect(x, y - (height / 5), width, height / 5); // draw in next cell up, then scale down height so that
                                                                // doorway appears on top edge of cell
        } else if (this.getDoorDirection() == DoorDirection.RIGHT) {
            g.fillRect(x + width, y, width / 5, height); // draw in next cell over, then scale down width so that
                                                         // doorway appears on right edge of cell
        } else {
            g.fillRect(x - (width / 5), y, width / 5, height); // draw in next cell over, then scale down width so that
                                                               // doorway appears on left edge of cell
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