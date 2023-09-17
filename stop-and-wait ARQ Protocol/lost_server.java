import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

class Server {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private Timer timer;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                byte[] fileContent = "Contrary to popular belief, Lorem Ipsum is not simply random text.".getBytes();
                int windowSize = 4;
                int base = 0;
                int nextSeqNum = 0;

                while (base < fileContent.length) {
                    for (int i = base; i < Math.min(base + windowSize, fileContent.length); i++) {
                        Frame frame = new Frame(nextSeqNum, fileContent[i]);
                        out.writeObject(frame);
                        out.flush();
                        System.out.println("Frame: " + nextSeqNum);
                        nextSeqNum++;
                    }

                    timer = new Timer();

                    for (int i = base; i < Math.min(base + windowSize, fileContent.length); i++) {
                        final int seqNum = i;
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                System.out.println("Timeout for frame: " + seqNum);
                                try {
                                    System.out.println("Resending...");
                                    Frame frame = new Frame(seqNum, fileContent[seqNum]);
                                    out.writeObject(frame);
                                    out.flush();
                                    System.out.println("Frame resent: " + seqNum);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 1000); // 1 second timeout
                    }

                    try {
                        // Wait for acknowledgments and update base
                        for (int i = base; i < Math.min(base + windowSize, fileContent.length); i++) {
                            Ack ack = (Ack) in.readObject();
                            if (ack.getAckNumber() >= base) {
                                base = ack.getAckNumber() + 1;
                                timer.cancel();
                                break;
                            }
                        }
                    } catch (EOFException e) {
                        // Client closed the connection
                       // clientSocket.close();
                        break;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
