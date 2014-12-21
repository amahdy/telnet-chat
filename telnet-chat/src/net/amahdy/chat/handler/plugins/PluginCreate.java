package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.data.Room;
import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to create a new chat room.
 * 
 * Arguments (minimum=1)
 * [REQUIRED] Room name
 * [OPTIONAL] Room description
 * 
 * @author amahdy.net
 */
public class PluginCreate implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        if(args.length<2) {
            hdlr.respondErr("Too few arguments for the command '"
                    + args[0] + "'.");
            hdlr.respondErr("Command should be: " + args[0]
                    + " room_name [room_description]");
            return;
        }

        if(hdlr.getRooms().containsKey(args[1])) {
            hdlr.respondErr("Room '" + args[1] + "' already exists.");
            return;
        }

        Room room = new Room();
        room.setRoomName(args[1]);
        if(args.length>2) {
            // Potential space between text,
            // should be all considered as description
            String text = MessageFormatter.reconstructArgs(args, 2);
            room.setRoomDescription(text);
        }
        hdlr.getRooms().put(args[1], room);

        hdlr.respond("Room '" + args[1] + "' created successfully.");
    }

    @Override
    public String about() {
        return MessageFormatter
                .formatAboutSyntax("/create room-name [room-description]")
                + MessageFormatter.formatAboutDesc("This command lets you " 
                + "create a new chat room. The first argument is required, " 
                + "in which you state a room-name, and the second argument "
                + "is optional for the room-description.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "You can not create a room with a name that"
                        + " is already taken."
                });
    }
}
