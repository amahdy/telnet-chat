package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.handler.PluginServer;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to quit the server.
 * 
 * Arguments (minimum=0)
 *
 * @author amahdy.net
 */
public class PluginQuit implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        String username = hdlr.getUser().getUserName();
        
        // Log out
        PluginServer.execute("/logout", hdlr);

        // Tell everyone you are leaving
        Handler.broadcastAll(username + " is disconnected.");

        hdlr.respond("BYE");
    }
    
    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/quit")
                + MessageFormatter.formatAboutDesc("Used for terminating the connection with the server. It attempts to disconnect you from the chat room that you are in, if you were in one, and then terminates the connection.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "This is the only command available to halt the connection and exits."
                });
    }
}
