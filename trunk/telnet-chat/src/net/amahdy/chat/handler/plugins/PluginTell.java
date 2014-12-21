package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to make a private chat.
 * 
 * Arguments (minimum=2)
 * [REQUIRED] Username of the person to chat with in private
 * [REQUIRED] Text of the private chat
 * 
 * @author amahdy.net
 */
public class PluginTell implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        if(args.length<3) {
            hdlr.respondErr("Too few arguments for the command '"
                    + args[0] + "'.");
            hdlr.respondErr("Command should be: " + args[0]
                    + " peer_user_name text_to_tell");
            return;
        }

        if (args[1].equals(hdlr.getUser().getUserName())) {
            hdlr.respondErr("Talking to yourself again?");
        } else if(hdlr.getUsers().get(args[1])==null) {
            hdlr.respondErr("Can't find user '" + args[1] + "'.");
        } else {
            String msg = MessageFormatter.reconstructArgs(args, 2);
            hdlr.getUsers().get(args[1]).getHdlr().respond(
                                "~FC"
                                + hdlr.getUser().getUserName()
                                + "~RS tells you "
                                + msg);
            hdlr.respond("You tell ~FC" + args[1] + "~RS " + msg);
        }
    }
    
    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/tell peer-user-name text-to-tell")
                + MessageFormatter.formatAboutDesc("This unique command lets you talk privately with a specific user. It is not required to be part of a chat room. It only needs the username of the peer that you want to talk to. This command requires two arguments, a valid username as first argument, and the message-text to be delivered as second argument.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "You cannot pass your own username as the first argument since you can not talk to yourself."
                });
    }
}
