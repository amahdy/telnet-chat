package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.data.Room;
import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.tools.MessageFormatter;

/**
  * Plugin to list available chat rooms.
 * 
 * Arguments (minimum=0)
 * 
 * @author amahdy.net
 */
public class PluginRooms implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {
        if(hdlr.getRooms().isEmpty()) {
            hdlr.respond("No active rooms yet.");
        }else {
            hdlr.respond("Active rooms are:");

            synchronized (hdlr.getRooms().values()) {
                for(Room room: hdlr.getRooms().values()) {
                    String response = "* " + room.getRoomName()
                            + " (" + room.getUsers().size() + ")";
                    if(hdlr.getUser().getRoom()!=null
                            && hdlr.getUser().getRoom().equals(room)) {
                        response += " (** you are here)";
                    }
                    hdlr.respond(response);
                }
            }
            hdlr.respond("End of list.");
        }
    }
    
    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/rooms") 
                + MessageFormatter.formatAboutDesc("This command lists the available chat rooms, along with the number of active users in each room. It gives you an indicator on which room you are currently in, if you were in one.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "No helpful information will be displayed if there are no rooms created on the server."
                });
    }
}
