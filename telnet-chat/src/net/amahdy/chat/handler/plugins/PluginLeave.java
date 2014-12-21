package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to leave a chat room.
 * 
 * Arguments (minimum=0)
 * 
 * @author amahdy.net
 */
public class PluginLeave implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        if(hdlr.getUser().getRoom()==null) {
            hdlr.respondErr("You are not in a chat room.");
        }else {
            hdlr.broadcastOthersOn("* user has left chat: "
                    + hdlr.getUser().getUserName());

            hdlr.respond("* user has left chat: "
                    + hdlr.getUser().getUserName()
                    + " (** this is you)");

            hdlr.getUser().getRoom().getUsers().remove(hdlr.getUser());
            hdlr.getUser().setRoom(null);
        }
    }

    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/leave")
                + MessageFormatter.formatAboutDesc("This command allows you to leave a chat room.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "You can not leave if you are not in a chat room.",
                    "Once you leave a chat room, everyone in the room will be notified."
                });
    }
}
