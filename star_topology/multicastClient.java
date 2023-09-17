import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

// Run this line on terminal:java multicastClient.java 224.0.1.0   

class multicastClient {
    public static void main(String[] args) {
        try {
            // get an ip address from a group of ip addresses
            String grp = args[0];
            InetAddress group = InetAddress.getByName(grp);
            int port = 5000;
            MulticastSocket socket = new MulticastSocket(port);
            // client joins the multicast group
            socket.joinGroup(group);
            // variable to hold received data
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while(true){
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
