package net.amahdy.chat.data;

import net.amahdy.chat.handler.Handler;

/**
 * This class holds data for a user.
 * 
 * @author amahdy.net
 */
public class User {

    // (UNIQUE) Name of the user
    private String userName;
    // (OPTIONAL) Descritpion of the user
    private String description = "No Description.";
    // Chat room in which the user participates or null if none.
    private Room room = null;
    // User handler (client)
    private Handler hdlr;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Handler getHdlr() {
        return hdlr;
    }

    public void setHdlr(Handler hdlr) {
        this.hdlr = hdlr;
    }
}
