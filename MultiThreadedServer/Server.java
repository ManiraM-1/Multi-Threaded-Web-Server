package MultiThreadedServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer(){
        /*return new Consumer<Socket>() {
            @Override
            public void accept(Socket socket) {
                try {
                    PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                    toClient.println("Hello from the Server");
                    toClient.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

        return (clientSocket) -> {
            try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
                toSocket.println("Hello from server " + clientSocket.getInetAddress());
                //toSocket.close();
                //clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }
    public static void main(String[] args) {
        Server server = new Server();
        int port = 8010;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("The Server is listening to port: "+port);
            while (true){
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
