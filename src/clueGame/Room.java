package clueGame;

public class Room {
    private String name;
    private BoardCell centerCell;
    private BoardCell labelCell;

    public Room(String inputName, BoardCell center, BoardCell label) {
        name = inputName;
        centerCell = center;
        labelCell = label;
    } 

    public String getName() {
        return name;
    }
    
    public BoardCell getLabelCell() {
        return labelCell;
    }

    public BoardCell getCenterCell() {
        return centerCell;
    }
}
