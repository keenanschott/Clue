package experiment;
import java.util.*;

public class TestBoardCell {
        private int row;
        private int col;
        private Set<TestBoardCell> adjacencyList;
        private boolean isRoom;
        private boolean occupied;
        
        public TestBoardCell(int testRow, int testCol) {
                row = testRow;
                col = testCol;
                adjacencyList = new HashSet<TestBoardCell>();
                isRoom = false;
        }

        public void addAdjacency(TestBoardCell cell) {
                adjacencyList.add(cell);
        }

        public Set<TestBoardCell> getAdjList() {
                return adjacencyList;
        }

        public boolean isRoom() {
                return isRoom;
        }

        public void setRoom(boolean roomStatus) {
                isRoom = roomStatus;
        }

        public void setOccupied(boolean occupation) {
                occupied = occupation;
        }

        public boolean getOccupied() {
                return occupied;
        }
}