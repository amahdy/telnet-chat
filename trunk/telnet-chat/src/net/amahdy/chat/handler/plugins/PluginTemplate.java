package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin TEMPLATE for future plugins.
 * 
 * Arguments (minimum=2)
 * [REQUIRED] First required argument
 * [REQUIRED] Second required argument
 * [OPTIONAL] First optional argument
 * [OPTIONAL] Second optional argument
 * 
 * <b>Steps to create a plugin</b>
 * 
 * 1. Make a copy of this class, rename it from 'PluginTemplate'
 * to 'Plugin{CommandName}',
 * where 'CommandName' is the command that will execute the plugin.
 * 
 * 2. Add your plugin logic in the 'run' method.
 * 
 * 3. Feel free to add as much helper private methods as you want.
 * 
 * 4. Make sure you make appropriate error checking.
 * 
 * 5. Go to net.amahdy.chat.handler.PluginServer
 * and add the new command to the 'cPlugins' list.
 * 
 * @author amahdy.net
 */
public class PluginTemplate implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        /**
         * Arguments are at least of length '1'.
         * The first element is the command itself,
         * so need to check that the length is at least minimum+1
         * 
         */
        if(args.length<3) {
            hdlr.respondErr("Too few arguments for the command '"
                    + args[0] + "'.");
            hdlr.respondErr("Command should be: " + args[0]
                    + " arg1 arg2 [arg3] [arg4]");
            return;
        }

        hdlr.respond("This is the template for creating a plugin!");

        hdlr.respondErr("This is how you can output an error to the user");

        hdlr.respond("Required parameters received are '" + args[1]
                + "' and '" + args[2] + "'.");

        if(args.length>3) { 
            hdlr.respond("Optional parameter received is '" + args[3] + "'.");
        

            if(args.length>4) { 
                hdlr.respond("Another optional parameter received is '"
                        + args[4] + "'.");
            }

            hdlr.respond("You can combine multiple arguments like '"
                    + MessageFormatter.reconstructArgs(args, 3) + "'.");
       }
    }
    
    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/template arg1 arg2 [arg3] [arg4]")
                + MessageFormatter.formatAboutDesc("The class 'net.amahdy.chat.handler.plugins.PluginTemplate' provides a ready to use template for developing new plugins for the chat server. Please follow the instructions in the java doc of the class to learn more on how to extend the chat server with more functionalities.")
                + MessageFormatter.formatAboutNotes(null);
    }
}
