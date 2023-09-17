import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

class multicastServer {
    public static void main(String[] args) {
        try{
            //addresses for multicasting 224.0.0.0 to 239.255.255.255
            //address 255.255.255.255 for local network is used to broadcast messages..IPv4 protocol
            InetAddress group = InetAddress.getByName("224.0.1.0");
            MulticastSocket socket = new MulticastSocket();
            //DatagramSocket socket = new DatagramSocket();
            String message = "";
            do{
                System.out.println("Type a message to send");
            Scanner ob = new Scanner(System.in);
            message = ob.nextLine();
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data,data.length,group,5000);
            System.out.println("Sending packet...");
            socket.send(packet);
            System.out.println("Packet sent...");
            }while(!message.equalsIgnoreCase("exit"));
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
