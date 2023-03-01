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
}
