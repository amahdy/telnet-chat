package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.data.Room;
import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.handler.PluginServer;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to join a chat room.
 * 
 * Arguments (minimum=1)
 * [REQUIRED] Room name
 * 
 * @author amahdy.net
 */
public class PluginJoin implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        if(args.length<2) {
            hdlr.respondErr("Too few arguments for the command '"
                    + args[0] + "'.");
            hdlr.respondErr("Command should be: " + args[0] + " room_name");
            return;
        }

        if(!hdlr.getRooms().containsKey(args[1])) {
            hdlr.respondErr("Can't find a room with name '" + args[1] + "'.");
        }else {
            // Leave previous room
            if(hdlr.getUser().getRoom()!=null) {
                if(hdlr.getUser().getRoom().getRoomName().equals(args[1])) {
                    hdlr.respondErr("You are already in room '"
                            + args[1] + "'.");
                    return;
                }
                PluginServer.execute("/leave", hdlr);
            }

            Room room = hdlr.getRooms().get(args[1]);
            room.getUsers().add(hdlr.getUser());
            hdlr.getUser().setRoom(room);

            hdlr.respond("Entering room: " + args[1]);
            hdlr.broadcastOthersOn("* new user joined chat: "
                    + hdlr.getUser().getUserName());

            PluginServer.execute("/who", hdlr);
        }
    }   

    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/join room-name")
                + MessageFormatter.formatAboutDesc("This command lets you join "
                + "a chat room. The first argument for this command "
                + "is room-name, and it is required.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "The specified room-name must be an existing chat room.",
                    "You can not re-join a room that you are already part of.",
                    "If you are already in a chat room, attempting to join "
                        + "a new chat room removes you from the old one.",
                    "Once you enter a chat room, everyone in the room "
                        + "will be notified.",
                    "Using any of the communication commands (such as '/say') "
                        + "will be visible by all the room members."
                });
    }
}
