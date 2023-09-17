import java.io.*;
import java.net.*;

// Client class
class MyClient {
    
    public static void main(String[] args)
    {
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket("localhost", 1234)) {
            
            // reading from server
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // object of scanner class
           // Scanner sc = new Scanner(System.in);
          //  String line = null;

            while (true) {
                // displaying server reply
                String serverReply = in.readLine();
                if (serverReply == null) {
                    System.out.println("Server has closed the connection.");
                    break;
                }
                System.out.println("Server replied: " + serverReply);
            }
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
