import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(3000);
        System.out.println("Waiting for client to connect");

        Socket connection = serverSocket.accept();
        System.out.println("Just connected ");

        DataInputStream inputStream = new DataInputStream(connection.getInputStream());// recieve
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());// send Info

        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = "", str2 = "";
        System.out.println("Start communicating with Client. Write stop to finish ");
        while (!str.equals("stop")) {

            str = inputStream.readUTF(); // recieve
           // C:\java\2101163\single server_client\abc.txt
            File file = new File("C:/java/2101163/single server_client/" + str);
            if (file.exists()) {

                FileReader fileReader1 = new FileReader(file.getAbsolutePath());
                BufferedReader br1 = new BufferedReader(fileReader1);
                int c = 0;
                int character = 0;
                while ((c = br1.read()) != -1) // Read char by Char
                {
                    character++;
                }
                outputStream
                        .writeUTF("Absolute Path" + file.getAbsolutePath() + "\n Number of Character" + character + "");
                outputStream.flush();

            } else {
                outputStream.writeUTF("Does not Exists");
                outputStream.flush();
            }

        }

        inputStream.close();
        connection.close();
        serverSocket.close();

    }
}
