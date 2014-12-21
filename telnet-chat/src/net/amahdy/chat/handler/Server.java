package net.amahdy.chat.handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main class for the chat server. Run this class to load the server
 * and start listening at port '9399'.
 * Optionally, you can give an alternative port as a command line argument.
 * 
 * @author amahdy.net
 */
public class Server {

    // Default connection port
    private static final int DEFAULT_PORT = 9399;

    /**
     * Entry point of the application. Accepts one argument to be considered
     * as the connection port, alternative to the default port '9399'.
     * 
     * @param args 
     */
    public static void main(String[] args) {

        int port = DEFAULT_PORT;
        ServerSocket serverSocket = null;
        Socket socket = null;

        try { // Check if port was passed as a command line argument
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Usage: java AMahdyChat [port]");
            System.err.println("Where options include:");
            System.err.println("\tport: The port on which to listen.");
            System.exit(0);
        }

        try { // Start listenting
            serverSocket = new ServerSocket(port);
            while (true) {
                socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        } catch (IOException ioe) {
            System.err.println("I/O Error...");
            System.err.println(ioe.getMessage());
        } finally {
            try {
                if(socket!=null) {
                    socket.close();
                }
                if(serverSocket!=null) {
                    serverSocket.close();
                }
            } catch (IOException ioe) {
                System.err.println("I/O Error...");
                System.err.println(ioe.getMessage());
            }
        }
    }
}
