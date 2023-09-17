
import java.io.*;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) throws IOException {
        System.out.println("Connecting to server... ");

        Socket socket = new Socket("localhost",3000);
        System.out.println("Connected to server... ");

        DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());

        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        String str="",str2="";
        System.out.println("Start comminicating with Server. Write stop to finish ");

        while(!str.equals("stop"))
        {
            System.out.println("Say: ");
            str=br.readLine();
            System.out.println("You: "+str);

            dataOutputStream.writeUTF(str);  //send to server
            dataOutputStream.flush();

            str2=dataInputStream.readUTF(); //receive
            System.out.println("Server says: "+str2);

        }
        System.out.println("Comes out of loop in client");

        dataInputStream.close();
        dataOutputStream.close();
        socket.close();
    }
}
