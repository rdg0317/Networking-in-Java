import java.io.*;
import java.net.*;

class Client{

    public static void main(String[] args) {
        try {
            int serverPort = 12345;
            Socket clientSocket = new Socket("127.0.0.1", serverPort);
            try {
                System.out.println("Connecting to server...");
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Connected!");

            File file = new File("alice29.txt");
            if (!file.exists()) {
                System.out.println("File not found");
                clientSocket.close();
                return;
            }

            // System.out.println("File located");
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
            OutputStream outputStream = clientSocket.getOutputStream();
            // System.out.println("Print data");
            byte[] buffer = new byte[5000];
            int bytesRead;
            int flag = 0;
            // System.out.println("Print data");
            while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
                outputStream.flush();

                InputStream inputStream = clientSocket.getInputStream();

                byte[] ackBuffer = new byte[3];

                int ackBytesRead = inputStream.read(ackBuffer);
                if (ackBytesRead != 3 || !new String(ackBuffer, "UTF-8").equals("ACK")) {
                    System.out.println("ACK not received. Exiting.");
                    flag++;
                    break;
                }
                else
                System.out.println("ACK");
            }
            if (flag == 0) {
                System.out.println("TCP Connection established");
            }
            fileInputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
