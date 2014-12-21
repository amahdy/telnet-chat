package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to /say a song.
 * 
 * Arguments (minimum=1)
 * [REQUIRED] Text to be sang.
 * 
 * @author amahdy.net
 */
public class PluginSing implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        if(args.length<2) {
            hdlr.respondErr("Too few arguments for the command '"
                    + args[0] + "'.");
            hdlr.respondErr("Command should be: " + args[0] + " text_to_sing");
            return;
        }

        if(hdlr.getUser().getRoom()==null) {
            hdlr.respondErr("You didn't join a chat room yet to sing in it.");
            return;
        }

        hdlr.broadcastAllOn("~FC"
                + hdlr.getUser().getUserName() 
                + "~RS sings o/~ " 
                + MessageFormatter.reconstructArgs(args, 1)
                + " o/~");
    }
    
    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/sing text-to-sing")
                + MessageFormatter.formatAboutDesc("This command displays an emotion that contains the song. The first argument for this command is the song-text, and it is required.")
                + MessageFormatter.formatAboutNotes(null);
    }
}
