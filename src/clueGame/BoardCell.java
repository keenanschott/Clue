package clueGame;

import java.util.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
public class BoardCell extends JPanel {
    private int row, col; // row and column identifiers for each cell
    private char initial, secretPassage; // chars
    private DoorDirection doorDirection = DoorDirection.NONE; // door direction for a given cell; default is NONE
    private boolean roomLabel = false, roomCenter = false, isRoom, isOccupied; // boolean statuses for a given cell;
                                                                               // defaults set for select booleans
    private Set<BoardCell> adjList; // adjacency list for a given cell
    private boolean target = false; // flag for target drawing
    private static int x; // static dimensions during drawing
    private static int y;
    private static int width;
    private static int height;

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
        addMouseListener(new CellListener());
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
     * paintComponent()
     * Draw in the rooms, walkways, and bad spaces/closets independently.
     * 
     * @param g The Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        if ((target && roomCenter) || (isRoom && Board.getInstance().getRoom(initial).getCenterCell().target)) {
            g.setColor(Color.CYAN); // room related target
            g.fillRect(x, y, width, height);
        } else if (target) {
            g.setColor(Color.CYAN); // standard target
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK); // border
            g.drawRect(x, y, width, height);
        } else if (isRoom && secretPassage == '\0') {
            g.setColor(Color.GRAY); // room
            g.fillRect(x, y, width, height);
        } else if (secretPassage != '\0') {
            g.setColor(Color.lightGray); // secret passage
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLUE);
            g.setFont(new Font("Tahoma", Font.PLAIN, Board.getInstance().getWidth() / 80));
            g.drawString(String.valueOf(secretPassage), x + (width / 2) - 4, y + (height / 2) + 6); // necessary offset
        } else if (getInitial() == 'W') { // cell is walkway
            g.setColor(Color.YELLOW); // filler
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK); // border
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
        g.setFont(new Font("Tahoma", Font.PLAIN, Board.getInstance().getWidth() / 80)); // stylistic font
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

    /**
     * setDimensions()
     * Set the dimensions from Board for the drawing.
     * 
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @param width  The cell width.
     * @param height The cell height.
     */
    public static void setDimensions(int x, int y, int width, int height) {
        BoardCell.x = x;
        BoardCell.y = y;
        BoardCell.width = width;
        BoardCell.height = height;
    }

    // all getters and setters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

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

    public void setTarget(boolean inTarget) {
        target = inTarget;
    }

    /**
     * Cell Listener
     * This class implements the listener for the mouse when a BoardCell is pressed.
     * DATE: 4/18/2023
     * 
     * @author Keenan Schott
     * @author Finn Burns
     */
    private class CellListener implements MouseListener {
        /**
         * mouseClicked()
         * When the mouse is clicked, a BoardCell should behave according to this logic.
         * 
         * @param e The mouse event.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            Board instance = Board.getInstance();
            if (target && instance.getCurrentPlayer() instanceof HumanPlayer) { // if a target cell
                instance.moveHuman(instance.getCell(row, col)); // move the human player
                repaint();
            } else if (isRoom && instance.getRoom(initial).getCenterCell().target
                    && instance.getCurrentPlayer() instanceof HumanPlayer) {
                // if a room cell with a center cell as a target
                instance.moveHuman(instance.getRoom(initial).getCenterCell()); // move the human player
                repaint();
            } else {
                JLabel label = new JLabel("<html><center>Invalid tile!"); // option pane to warn the player
                label.setHorizontalAlignment(SwingConstants.CENTER);
                JOptionPane.showMessageDialog(instance, label, "Warning!", JOptionPane.WARNING_MESSAGE);
            }
        }

        // following methods not needed but have to be implemented
        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}