package clueGame;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String inName, String inColor, int inRow, int inColumn) {
        super(inName, inColor, inRow, inColumn);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ComputerPlayer)) {
            return false;
        }
        Player c = (Player) o;
        if (this.getName() == c.getName() && this.getRow() == c.getRow() && this.getColumn() == c.getColumn()) {
            return true;
        }
        return false;
    }
    
}
