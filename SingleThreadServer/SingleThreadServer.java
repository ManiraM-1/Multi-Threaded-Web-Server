package SingleThreadServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadServer {
    public void run(){
        int port = 8010;
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
            socket.setSoTimeout(10000);
            while(true){
                System.out.println("The Server is Listening to port on: "+port);
                Socket acceptConnection = socket.accept();
                System.out.println("Connection is accepted from Client: "+acceptConnection.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(acceptConnection.getOutputStream());
                //PrintWriter Converts String(Data) to Bytes and sends it to client in form of Stream
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptConnection.getInputStream()));
                //BufferedReader takes the info from Client in form of bytes and collect them together
                //BufferReader takes only Reader(as parameter) and InputStreamReader (which implements Reader) not the Input Stream
                // The Input Stream Reader requires InputStream(as parameter).
                toClient.println("Hello from the Server");
                toClient.close();
                fromClient.close();
                acceptConnection.close();
            }
        } catch (IOException ex) {
            //throw new RuntimeException(e);
            ex.printStackTrace();
        }

    }
    public static void main(String[] args) {
        SingleThreadServer server = new SingleThreadServer();
        try{
            server.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
