package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.handler.PluginServer;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to logout the current user.
 * 
 * Arguments (minimum=0)
 * 
 * @author amahdy.net
 */
public class PluginLogout implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        if(hdlr.getUser()!=null) {
            // Leave group
            if(hdlr.getUser().getRoom()!=null) {
                //TODO: fix if '/leave' plugin is not there?
                PluginServer.execute("/leave", hdlr);
            }

            hdlr.getUsers().remove(hdlr.getUser().getUserName());
            hdlr.setUser(null);
        }
    }

    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/logout")
                + MessageFormatter.formatAboutDesc("This command ends the usage of the currently used username, but not quit the server.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "You are not allowed to use this command as it leaves you in undefined state. It is designed for server usage only.",
                    "This command is used internally when you attempt to re-login or quit the server."
                });
    }
}
