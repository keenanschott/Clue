package clueGame;

public class Solution {
    private Card room;
    private Card person;
    private Card weapon;

    public Solution() {
        super();
    }

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
