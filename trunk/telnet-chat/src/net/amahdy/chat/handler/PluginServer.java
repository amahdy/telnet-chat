package net.amahdy.chat.handler;

import java.util.HashMap;
import net.amahdy.chat.handler.plugins.Plugin;

/**
 * Loads and manages the available plugins in the server.
 * Some plugins are built-in: quit, login, logout, and say.
 * 
 * Example: to add 'foo' command: Add 'foo' to the 'cPlugins' list,
 * create PluginFoo class under net.amahdy.chat.handler.plugins,
 * and make PluginFoo implements net.amahdy.chat.handler.plugins.Plugin.
 * 
 * @see net.amahdy.chat.handler.plugins.PluginTemplate for a template.
 * 
 * @author amahdy.net
 */
public abstract class PluginServer {

    // Command used to halt connection
    public static final String COMMAND_QUIT = "/quit";
    // Command used to login
    public static final String COMMAND_LOGIN = "/login";
    // Command used to logout
    public static final String COMMAND_LOGOUT = "/logout";
    // Command used to speak (chat!)
    public static final String COMMAND_SAY = "/say";
    // Command used to display help about the available commands
    public static final String COMMAND_HELP = "/help";

    // Available commands (plugins)
    //TODO: read them from external file.
    private static String[] cPlugins = {
        COMMAND_QUIT, COMMAND_LOGIN, COMMAND_LOGOUT, COMMAND_SAY,
        "/create", "/join", "/help", "/leave", "/remove",
        "/rooms", "/sing", "/tell", "/template",
        "/think", "/who",
    };

    public static String[] getCPlugins() {
        return cPlugins;
    }

    private static HashMap<String, String> plugins = new HashMap();

    public static HashMap<String, String> getPlugins() {
        return plugins;
    }

    /**
     * Initialize the plugins.
     * 
     */
    public static void init() {

        for(int i=0; i<cPlugins.length; i++) {
            String command = cPlugins[i].toLowerCase();
            String cls = "net.amahdy.chat.handler.plugins.Plugin"
                    + command.substring(1, 2).toUpperCase()
                    + command.substring(2);
            plugins.put(command, cls);
        }
    }

    /**
     * Executes a given command by mapping it to the corresponding plugin.
     * 
     * @param command Command given
     * @param hdlr Handler requesting the command
     * @param args Arguments given with the command
     */
    public static void execute(String command, Handler hdlr, String... args) {

        // The command is preceeded with '/' and might be in differnet cAsE
        String plugin = plugins.get(command.toLowerCase());
        if(plugin==null) {
            hdlr.respondErr("Unrecognized command '" + command + "'.");
        }else {
            ClassLoader classLoader = Plugin.class.getClassLoader();

            try {
                Class aClass = classLoader.loadClass(plugin);
                Plugin p = (Plugin) aClass.newInstance();
                p.run(hdlr, args);
            } catch (ClassNotFoundException ex) {
                hdlr.respond("~LI~FRPlugin '" + plugin + "' load failure.~RS");
            } catch (InstantiationException ex) {
                hdlr.respond("~LI~FRPlugin '" + plugin + "' load failure.~RS");
            } catch (IllegalAccessException ex) {
                hdlr.respond("~LI~FRPlugin '" + plugin + "' load failure.~RS");
            }
        }
    }
}
