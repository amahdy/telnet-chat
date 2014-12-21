package net.amahdy.chat.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class holds data for a chat room.
 * 
 * @author amahdy.net
 */
public class Room {

    // (UNIQUE) Name of the Room
    private String roomName;
    // (OPTIONAL) Description of the room
    private String roomDescription = "No Description.";
    // List of connected users in this room
    private Set<User> users = Collections.synchronizedSet(new HashSet());

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean equals(Room obj) {
        return obj.getRoomName().equals(roomName);
    }
}
