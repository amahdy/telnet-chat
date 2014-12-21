package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to /say a thought.
 * 
 * Arguments (minimum=1)
 * [REQUIRED] Text to be though.
 * 
 * @author amahdy
 */
public class PluginThink implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {
        
        if(args.length<2) {
            hdlr.respondErr("Too few arguments for the command '"
                    + args[0] + "'.");
            hdlr.respondErr("Command should be: " + args[0]
                    + " text_of_thought");
            return;
        }

        if(hdlr.getUser().getRoom()==null) {
            hdlr.respondErr("You didn't join a chat room yet to think in it.");
            return;
        }

        hdlr.broadcastAllOn("~FC" 
                + hdlr.getUser().getUserName() 
                + "~RS thinks . o O ( " 
                + MessageFormatter.reconstructArgs(args, 1)
                + " )");
    }
    
    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/think text-to-think")
                + MessageFormatter.formatAboutDesc("This command displays an emotion that contains the thought. The first argument for this command is the thought-text, and it is required.")
                + MessageFormatter.formatAboutNotes(null);
    }
}
