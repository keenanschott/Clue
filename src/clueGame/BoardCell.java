package clueGame;
import java.util.*;

public class BoardCell {
    private int row;
    private int col;
    private char intitial;
    private DoorDirection doorDirection;
    private boolean roomLabel;
    private boolean roomCenter;
    private char secretPassage;
    private Set<BoardCell> adjList;
    
    public BoardCell(int inputRow, int inputCol) {
        row = inputRow;
        col = inputCol;
        adjList = new HashSet<BoardCell>();
        roomLabel = false;
        roomCenter = false;
    }

    public void addAdj(BoardCell adj) {
        adjList.add(adj);
    }

    // TODO: add necessary getters and setters
}
