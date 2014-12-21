package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.handler.Handler;

/**
 * Interface holding basic implementations of a plugin.
 * 
 * Adding a plugin is as simple as adding its command to the 'cPlugins' list
 * in net.amahdy.chat.handler.PluginServer (TODO: to a text file)
 * and then create a class under net.amahdy.chat.handler.plugins
 * with class name Plugin{CommandName}, where CommandName is a lowercase
 * representation of the command except first letter to be uppercase.
 * That class should implements 'Plugin' and its procedures
 * should go under 'run' method.
 * 
 * @see net.amahdy.chat.handler.plugins.PluginTemplate for a template.
 * 
 * @author amahdy.net
 */
public interface Plugin {

    /**
     * Main runnable method. Will be executed once a plugin is called.
     * 
     * @param hdlr the handler (client) calling this plugin.
     * @param args arguments to be passed to the plugin.
     */
    public void run(Handler hdlr, String... args);

    /**
     * Method for displaying help/ about the plugin.
     * 
     * @return String representing the description of the plugin.
     */
    public String about();
}
