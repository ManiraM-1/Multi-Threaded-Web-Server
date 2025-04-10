package MultiThreadedServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try{
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address,port);
                    try {
                        PrintWriter toSocket = new PrintWriter(socket.getOutputStream());
                        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        toSocket.println("Hello from the Client");
                        String response = fromSocket.readLine();
                        System.out.println("Response from the Socket is: "+response);
                    }
                    //toSocket.close();
                    //fromSocket.close();
                    //socket.close();
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }
    public static void main(String[] args) {
        Client client = new Client();
        try{
            for(int index = 0; index < 100; index++){
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            //return;
        }
    }
}
