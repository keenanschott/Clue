package experiment;
import java.util.*;

public class TestBoardCell {
        int row;
        int col;
        Set<TestBoardCell> adjacencyList;
        boolean isRoom;
        boolean occupied;
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