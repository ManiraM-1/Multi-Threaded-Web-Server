package MultiThreadedServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Modified_Server {

    // This function returns a Consumer that handles a connected client socket
    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (
                    Socket socket = clientSocket;
                    PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true)
            ) {
                // Respond to the client
                toClient.println("Hello from the Server " + socket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        Modified_Server server = new Modified_Server();
        int port = 8010;

        // Step 1: Create a thread pool (tune the size based on your system)
        int threadPoolSize = 100;  // You can experiment with this
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is listening on port: " + port);

            // Step 2: Continuously accept client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Step 3: Submit the task to the thread pool
                executor.submit(() -> server.getConsumer().accept(clientSocket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Optional: clean shutdown if you want to stop the server
            executor.shutdown();
        }
    }
}

