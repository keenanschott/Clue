package clueGame;

/**
 * Solution
 * The solution to the game; has one weapon card, one room card, and one person card. The human player attempts to deduce this solution to win the game.
 * DATE: 3/31/2023
 * @author Keenan Schott
 * @author Finn Burns
 */
public class Solution {
    private Card room;
    private Card person;
    private Card weapon;

    /**
	 * Solution()
     * Constructor.
     */
    public Solution() {
        super();
    }

    public Solution(Card inRoom, Card inPerson, Card inWeapon) {
        super();
        room = inRoom;
        person = inPerson;
        weapon = inWeapon;
    }

    /**
	 * equals()
     * Compare two Solution instances.
	 * 
	 * @param o The comparison object.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Solution)) {
            return false; // if not a Solution instance; false
        }
        Solution c = (Solution) o; // cast
        return (getRoom().equals(c.getRoom()) && getPerson().equals(c.getPerson()) && getWeapon().equals(c.getWeapon())); // compare instance variabless
    }

    // all getters and setters
    public void setRoom(Card inRoom) {
        room = inRoom;
    }

    public void setPerson(Card inPerson) {
        person = inPerson;
    }

    public void setWeapon(Card inWeapon) {
        weapon = inWeapon;
    }

    public Card getRoom() {
        return room;
    }

    public Card getPerson() {
        return person;
    }

    public Card getWeapon() {
        return weapon;
    }
}
