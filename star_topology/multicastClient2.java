import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.*;
// Run this line on terminal:java multicastClient.java 224.0.1.0   

class multicastClient {
    public static void main(String[] args) {
        try {
            // get an ip address from a group of ip addresses
            String grp = args[0];
            int client = Integer.valueOf(args[1]);
            InetAddress group = InetAddress.getByName(grp);
            int port = 5000;

            MulticastSocket socket = new MulticastSocket(port);
            // client joins the multicast group
            socket.joinGroup(group);
            // variable to hold received data
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                System.out.println("Send (0) or Receive (1)?");
                Scanner ob = new Scanner(System.in);
                int ch = ob.nextInt();
                ob.nextLine();
                if (ch == 0) {
                    String message = ob.nextLine();
                    //message += client;
                    byte[] data = message.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(data, data.length, group, 5000);
                    System.out.println("Sending packet...");
                    socket.send(sendPacket);
                    //socket.receive(packet);
                    System.out.println("Packet sent...");
                    continue;// now this client will never receive a message
                }
                System.out.println("Receiving packet...");
                socket.receive(packet);
                System.out.println("Packet received...");
                String message = new String(packet.getData(), 0, packet.getLength());
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Termination signal received. Exiting...");
                    break;
                }
                System.out.println("Received: " + message);
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
