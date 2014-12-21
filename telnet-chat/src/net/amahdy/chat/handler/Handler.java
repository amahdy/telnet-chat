package net.amahdy.chat.handler;

import java.io.*;
import java.net.Socket;
import java.util.*;
import net.amahdy.chat.data.Room;
import net.amahdy.chat.data.User;
import net.amahdy.chat.tools.MessageFormatter;

/**
 * Handler for the client connection. Could be also considered
 * as client manager. Manages the connections to the server,
 * holds information about connected users and rooms. And manages the I/O
 * between the client and server.
 * 
 * @author amahdy.net
 */
public class Handler extends Thread {

    // === STATIC CONTENT ======================================================
    // Available connections
    private static final Set<Handler> hdlrs
            = Collections.synchronizedSet(new HashSet());
    // Connected users
    private static final Map<String, User> users
            = Collections.synchronizedMap(new HashMap());
    // Available chat rooms
    private static final Map<String, Room> rooms
            = Collections.synchronizedMap(new HashMap());

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }
    // =========================================================================

    // === HANDLER DATA ========================================================
    // Connection socket
    private final Socket socket;
    // Stream input
    private final BufferedReader in;
    // Stream output
    private final PrintWriter out;
    // The connected user
    private User user;

    public BufferedReader getIn() {
        return in;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // =========================================================================

    /**
     * Handler Constructor
     * Takes a socket to create a new handler for a given new connection
     * to the server.
     * 
     * @param socket New connection socket
     * @throws IOException Input/Output failure
     */
    public Handler(Socket socket) throws IOException {

        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Main runnable part of the Handler.
     * 
     */
    @Override
    public void run() {

        hdlrs.add(this);

        // Load configured plugins
        PluginServer.init();

        try {
            // Greeting message
            respond("Welcome to the AMahdy.net chat server");

            // Force first time login
            PluginServer.execute(PluginServer.COMMAND_LOGIN, this);

            // Stream input line
            String line;
            // Accepts input till '/quit' signal is received
            while (!(line = in.readLine())
                    .equalsIgnoreCase(PluginServer.COMMAND_QUIT)) {

                if(!line.startsWith("/")) {
                    line = "/say " + line;
                }

                //Get commands and arguments
                String[] args = line.split(" ");

                //TODO: handle this somewhere else.
                if(args[0].toLowerCase().equals("/logout")) {
                    respondErr("Only server can logout the user.");
                    respondErr("To exit please use '/quit' instead");
                    continue;
                }

                PluginServer.execute(args[0], this, args);
            }

            // Quit because '/quit' signal received
            PluginServer.execute(PluginServer.COMMAND_QUIT, this);

        } catch (IOException ioe) {
            System.err.println("I/O Error...");
            System.err.println(ioe.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ioe) {
                System.err.println("I/O Error...");
                System.err.println(ioe.getMessage());
            } finally {
                if(user!=null) {
                    if(user.getRoom()!=null) {
                        user.getRoom().getUsers().remove(user);
                    }
                    users.remove(user.getUserName());
                    user = null;
                    hdlrs.remove(this);
                }
            }
        }
    }

    /**
     * Handler Response Manager
     * Formats a response and respond to the client.
     * 
     * @param text Message to respond with.
     */
    public void respond(String text) {

        this.out.println(MessageFormatter.parse("\r~OL~FG<=~RS " + text));
        this.out.print(MessageFormatter.parse("~OL~FB=>~RS "));
        this.out.flush();
    }

    /**
     * Handler Response Manager for Errors
     * Formats an error response and respond to the client.
     * 
     * @param text Error message to respond with.
     */
    public void respondErr(String text) {

        respond("~FR" + text + "~RS");
    }

    /**
     * Broadcasts a message to all available connections.
     * 
     * @param msg Message to be broadcasted
     */
    public static void broadcastAll(String msg) {

        synchronized (hdlrs) {
            for(Handler handler: hdlrs) {
                handler.respond(msg);
            }
        }
    }

    /**
     * Broadcasts a message to all other users in current room except myself.
     * 
     * @param msg Message to be broadcasted
     */
    public void broadcastOthersOn(String msg) {
        if(user.getRoom()!=null) {
            synchronized (user.getRoom().getUsers()) {
                for(User u: user.getRoom().getUsers()) {
                    if(user!=u) {
                        u.getHdlr().respond(msg);
                    }
                }
            }
        }
    }

    /**
     * Broadcasts a message to all users in current room.
     * 
     * @param msg Message to be broadcasted
     */
    public void broadcastAllOn(String msg) {
        if(user.getRoom()!=null) {
            broadcastOthersOn(msg);
            respond(msg);
        }
    }
}
