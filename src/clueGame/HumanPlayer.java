package clueGame;

public class HumanPlayer extends Player {

    public HumanPlayer(String inName, String inColor, int inRow, int inColumn) {
        super(inName, inColor, inRow, inColumn);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HumanPlayer)) {
            return false;
        }
        HumanPlayer c = (HumanPlayer) o;
        if (this.getName() == c.getName() && this.getRow() == c.getRow() && this.getColumn() == c.getColumn()) {
            return true;
        }
        return false; 
    }
    
}
