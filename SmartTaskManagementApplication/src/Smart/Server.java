package Smart;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Server {
    private static Set<String> usernames = new CopyOnWriteArraySet<>();  // Thread-safe Set for usernames
    private static Set<Task> tasks = new CopyOnWriteArraySet<>(); // Thread-safe Set for tasks
    private static final int PORT = 8003;

    public static void main(String[] args) {
        ServerSocket server = null;
        ExecutorService executor = Executors.newFixedThreadPool(10); // Thread pool for handling client requests

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                // Accept client connections
                Socket socket = server.accept();
                // Submit a new ClientHandler to handle the client's request
                executor.submit(new ClientHandler(socket, usernames, tasks));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
                if (executor != null) {
                    executor.shutdown();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
