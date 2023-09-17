
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

class multicastServer {
    public static void main(String[] args) {
        try {
            // addresses for multicasting 224.0.0.0 to 239.255.255.255
            // address 255.255.255.255 for local network is used to broadcast messages..IPv4
            // protocol
            InetAddress group = InetAddress.getByName("224.0.1.0");
            MulticastSocket socket = new MulticastSocket();
            // DatagramSocket socket = new DatagramSocket();
            socket.joinGroup(group);
            String message = "";
            do {
                System.out.println("Waiting to receive a packet");
                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);
                System.out.println("Packet received...");
                message = new String(receivePacket.getData(), 0, receivePacket.getLength()); // put last digit as a
                                                                                             // number to identity
                                                                                             // client
                byte[] data = message.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, group, 5000);
                System.out.println("Sending packet...");
                socket.send(packet);
                System.out.println("Packet sent...");
            } while (!message.equalsIgnoreCase("exit"));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
