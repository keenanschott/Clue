package clueGame;
import java.util.*;

public class Board {
    private BoardCell[][] grid;
    private int numRows;
    private int numColumns;
    private String layoutConfigFile;
    private String setupConfigFile;
    private Map<Character,Room> roomMap;
    private static Board theInstance = new Board();

    private Board() {
        super();
    }
    
    public static Board getInstance() {
        return theInstance;
    }
    
    public void initialize() {

    }

    public void loadSetupConfig() {

    }

    public void loadLayoutConfig() {

    }
}
