package net.amahdy.chat.handler.plugins;

import net.amahdy.chat.handler.Handler;
import net.amahdy.chat.handler.PluginServer;
import net.amahdy.chat.tools.MessageFormatter;

/**
 *
 * @author amahdy
 */
public class PluginHelp implements Plugin {

    @Override
    public void run(Handler hdlr, String... args) {

        StringBuilder help = new StringBuilder();
        for(String cPlugin: PluginServer.getCPlugins()) {

            String plugin = PluginServer.getPlugins().get(cPlugin);

            ClassLoader classLoader = Plugin.class.getClassLoader();

            try {
                Class aClass = classLoader.loadClass(plugin);
                Plugin p = (Plugin) aClass.newInstance();
                help.append("\n");
                help.append(p.about());
            } catch (ClassNotFoundException ex) {
                hdlr.respond("~LI~FRPlugin '" + plugin + "' load failure.~RS");
            } catch (InstantiationException ex) {
                hdlr.respond("~LI~FRPlugin '" + plugin + "' load failure.~RS");
            } catch (IllegalAccessException ex) {
                hdlr.respond("~LI~FRPlugin '" + plugin + "' load failure.~RS");
            }
        }
        hdlr.respond(help.toString());
    }

    @Override
    public String about() {
        return MessageFormatter.formatAboutSyntax("/help")
                + MessageFormatter.formatAboutDesc("Displays this help.")
                + MessageFormatter.formatAboutNotes(null);
    }
}
