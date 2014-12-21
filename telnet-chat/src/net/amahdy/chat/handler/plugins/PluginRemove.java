package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.data.Room;
import net.amahdy.chat.data.User;
import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to remove a chat room.
 * 
 * Arguments (minimum=1)
 * [REQUIRED] Room name
 * 
 * @author amahdy.net
 */
public class PluginRemove implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        if(args.length<2) {
            hdlr.respondErr("Too few arguments for the command '"
                    + args[0] + "'.");
            hdlr.respondErr("Command should be: " + args[0] + " room_name");
            return;
        }

        if(!hdlr.getRooms().containsKey(args[1])) {
            hdlr.respondErr("Room '" + args[1] + "' does not exist.");
            return;
        }

        Room room = hdlr.getRooms().get(args[1]);
        synchronized (room.getUsers()) {
            for(User user: room.getUsers()) {
                user.getHdlr().respondErr(
                        "You have been kicked out of '"
                        + room.getRoomName()
                        + "' room, because the user '"
                        + hdlr.getUser().getUserName()
                        + "' deleted it.");
                user.setRoom(null);
            }
        }
        hdlr.getRooms().remove(room.getRoomName());
        hdlr.respond("Room '" + args[1] + "' removed successfully.");
    }
    
    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/remove room-name")
                + MessageFormatter.formatAboutDesc("This command lets you remove a chat room. The first argument for this command is room-name, and it is required.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "You can not remove a chat room that does not exist.",
                    "Once you remove a chat room, everyone in this room are forced to leave the room, and will be notified that you removed the chat room."
                });
    }
}
