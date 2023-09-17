import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class LostAck {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String serverAddress = "127.0.0.1";
        int port = 12345;
        Socket socket = new Socket(serverAddress, port);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        // Simulated file content
        byte[] fileContent = "Contrary to popular belief, Lorem Ipsum is not simply random text.".getBytes();
        System.out.println("Total number of frames: " + fileContent.length);
        int expectedSeqNum = 0;
        int lost[] = { 2, 5, -1 };
        int l = 0;
        for (int i = 0; i < fileContent.length; i++) {
            Frame frame = (Frame) in.readObject();
            System.out.println("Frame: " + frame.getSequenceNumber());
            if (frame.getSequenceNumber() == expectedSeqNum) {
                // Skip sending ACK for a specific frame (simulate lost ACK)
                if (frame.getSequenceNumber() != lost[l]) {
                    Ack ack = new Ack(expectedSeqNum);
                    out.writeObject(ack);
                    out.flush();
                    System.out.println("Sent ACK for frame: " + expectedSeqNum);
                    expectedSeqNum++;
                } else {
                    l++;
                }
            }
        }
    }
}