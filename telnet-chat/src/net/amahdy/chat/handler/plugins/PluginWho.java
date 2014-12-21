package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.data.User;
import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to list users in a chat room.
 * 
 * Arguments (minimum=0)
 * 
 * @author amahdy.net
 */
public class PluginWho implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        if(hdlr.getUser().getRoom()==null) {
            hdlr.respondErr("You did not join any room.");
            return;
        }

        synchronized(hdlr.getUser().getRoom().getUsers()) {
            for(User user: hdlr.getUser().getRoom().getUsers()) {
                String response = "* " + user.getUserName();
                if(user.getUserName().equals(hdlr.getUser().getUserName())) {
                    response += " (** this is you)";
                }
                hdlr.respond(response);
            }
        }
        hdlr.respond("End of list."); 
    }

    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/who")
                + MessageFormatter.formatAboutDesc("This command lists the available users in the chat room.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "You must be inside a chat room to use this command.",
                    "This command is executed automatically once you join a chat room.",
                    "There will be an indicator next to your username."
                });
    }
}
