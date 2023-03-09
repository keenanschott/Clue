package experiment;
import java.util.*;

/**
 * TestBoardCell
 * A part of Clue Paths that constitutes a single cell in the game board.
 * DATE: 3/8/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
public class TestBoardCell {
        private int row, col; // row and column identifiers for each cell
        private Set<TestBoardCell> adjList; // adjacency list for a given cell
        private boolean isRoom = false, isOccupied = false; // boolean statuses for a given cell; false as default
        
        /**
         * Construct a cell with default values.
         * 
         * @param testRow The row value.
         * @param testCol The column value.
         */
        public TestBoardCell(int testRow, int testCol) {
                row = testRow; // test row and column
                col = testCol;
                adjList = new HashSet<TestBoardCell>(); // allocate space
        }

        /**
         * Add a cell to the adjacency list.
         * 
         * @param cell The TestBoardCell to add to the adjacency list.
         */
        public void addAdjacency(TestBoardCell cell) {
                adjList.add(cell); // add cell to the adjacency list
        }

        /**
         * Get the adjacency list.
         * 
         * @return Return the adjacency list.
         */
        public Set<TestBoardCell> getAdjList() {
                return adjList;
        }

        // all getters and setters
        public void setIsRoom(boolean roomStatus) {
                isRoom = roomStatus; // set isRoom
        }

        public boolean getIsRoom() {
                return isRoom; // get isRoom
        }

        public void setIsOccupied(boolean occupation) {
                isOccupied = occupation; // set isOccupied
        }

        public boolean getIsOccupied() {
                return isOccupied; // get isOccupied
        }
}