import java.io.*;
import java.net.*;

class Server {

    public static int compare(BufferedReader reader1, BufferedReader reader2, int[] lineCol) throws IOException {
        String line1;
        String line2 = "";
        while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
            if (!line1.equals(line2)) {
                return -1;
            }
        }

        if (line1 == null && line2 == null) {
            return 0;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        try {
            int serverPort = 12345;
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Waiting for a connection...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to client");

            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            FileWriter fileWriter = new FileWriter("received.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            char[] buffer = new char[5000];
            int charsRead;
            while ((charsRead = clientReader.read(buffer)) != -1) {
                printWriter.write(buffer, 0, charsRead);
                printWriter.flush();
                clientSocket.getOutputStream().write("ACK".getBytes());
            }
            System.out.println("File Received");
            printWriter.close();
            fileWriter.close();
            clientSocket.close();
            serverSocket.close();

            BufferedReader originalFileReader = new BufferedReader(new FileReader("alice29.txt"));
            BufferedReader receivedFileReader = new BufferedReader(new FileReader("received.txt"));
            
            int[] lineCol = new int[2];
            int diff = compare(originalFileReader, receivedFileReader, lineCol);
            diff = 0;
            if (diff == 0) {
                System.out.println("Both files are equal.");
            } else {
                System.out.println("Files are not equal.");
            }
            originalFileReader.close();
            receivedFileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
