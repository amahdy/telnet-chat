package net.amahdy.chat.handler.plugins;

import java.io.IOException;
import net.amahdy.chat.data.User;
import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.handler.PluginServer;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Plugin to login to the server using a username.
 * 
 * Arguments (minimum=0)
 * [OPTIONAL] User name
 * [OPTIONAL] Room description
 * 
 * @author amahdy.net
 */
public class PluginLogin implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        String line = null;

        try {
            if(args.length>1) {
                line = args[1];
            }else {
                init(hdlr);
                line = hdlr.getIn().readLine();
            }

            // Make sure name is not taken
            while(hdlr.getUsers().containsKey(line)
                    && !line.equalsIgnoreCase(PluginServer.COMMAND_QUIT)) {

                // Cancel login if input is same as current username
                if(hdlr.getUser()!=null
                        && line.equals(hdlr.getUser().getUserName())) {
                    hdlr.respond("Canceling re-login.");
                    return;
                }
                hdlr.respondErr("Sorry, name taken.");
                init(hdlr);
                line = hdlr.getIn().readLine();
            }
        } catch (IOException ex) {
            System.err.println("I/O Error...");
            System.err.println(ex.getMessage());
        }

        // Don't proceed if '/quit' signal received
        if(line!=null && !line.equalsIgnoreCase(PluginServer.COMMAND_QUIT)) {
            // Log out previous user
            PluginServer.execute("/logout", hdlr);

            // Initialize user
            User user = new User();
            user.setUserName(line);
            if(args.length>2) {
                // Potential space between text,
                // should be all considered as description
                String text = MessageFormatter.reconstructArgs(args, 2);
                user.setDescription(text);
            }
            user.setHdlr(hdlr);
            hdlr.setUser(user);
            hdlr.getUsers().put(user.getUserName(), user);

            // Greeting connected user
            hdlr.respond("Welcome " + user.getUserName() + "!");
        }else {
            PluginServer.execute(PluginServer.COMMAND_QUIT, hdlr, args);
        }
    }

    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/login [user-name] [user-description]")
                + MessageFormatter.formatAboutDesc("This command lets you login to the server using a picked username. If you are already logged in, a prompt asks you to re-login using a different username.")
                + MessageFormatter.formatAboutNotes(new String[] {
                    "Attempt to re-login using the same old username cancels the new re-login.",
                    "You can type the command all alone, or with optional tow arguments: user-name and user-description.",
                    "Typing '/quit' instead of a username terminates the connection.",
                    "You can not login with an already taken username.",
                    "This command is being called automatically once a new connection to the server has been created."
                });
    }

    private void init(Handler hdlr) {
        hdlr.respond("Login Name?");
    }
}
