package experiment;
import java.util.*;

public class TestBoardCell {
        private int row, col; // row and column identifiers for each cell
        private Set<TestBoardCell> adjList; // adjacency list for a given cell
        private boolean isRoom, isOccupied; // boolean statuses for a given cell
        
        public TestBoardCell(int testRow, int testCol) {
                row = testRow; // test row and column
                col = testCol;
                adjList = new HashSet<TestBoardCell>(); // allocate space
                isRoom = false; // set booleans to false for now
                isOccupied = false;
        }

        public void addAdjacency(TestBoardCell cell) {
                adjList.add(cell); // add cell to adjacency list
        }

        public Set<TestBoardCell> getAdjList() {
                return adjList; // get adjacency list
        }

        public boolean isRoom() {
                return isRoom; // get isRoom
        }

        public void setRoom(boolean roomStatus) {
                isRoom = roomStatus; // set isRoom
        }

        public void setOccupied(boolean occupation) {
                isOccupied = occupation; // set isOccupied
        }

        public boolean getOccupied() {
                return isOccupied; // get isOccupied
        }
}