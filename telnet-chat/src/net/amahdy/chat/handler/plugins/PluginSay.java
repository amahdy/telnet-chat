package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to make a communication (chat!).
 * 
 * Arguments (minimum=1)
 * [REQUIRED] Text to say
 * 
 * @author amahdy.net
 */
public class PluginSay implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        if(args.length<2) {
            hdlr.respondErr("Too few arguments for the command '"
                    + args[0] + "'.");
            hdlr.respondErr("Command should be: " + args[0] + " text_to_say");
            return;
        }

        if(hdlr.getUser().getRoom()==null) {
            //hdlr.respondErr("You didn't join a chat room yet to chat.");
            hdlr.respond("~OLecho~RS " + args[1]);
            return;
        }

        hdlr.broadcastAllOn("~FC"
                + hdlr.getUser().getUserName()
                + ":~RS "
                + MessageFormatter.reconstructArgs(args, 1));
    }
    
    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/say text-to-say")
                + MessageFormatter.formatAboutDesc("This command is the base chat functionality, it simply lets you chat.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "To be able to chat, you must join a room first.",
                    "If you didn't join any room yet, you will be talking to yourself. No one will see what you are typing.",
                    "'/say' is the default command in case you didn't specify any other commands."
                });
    }
}
