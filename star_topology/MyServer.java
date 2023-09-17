import java.io.*;
import java.net.*;
import java.util.Scanner;

// Server class
class MyServer {
	public static void main(String[] args)
	{
		ServerSocket server = null;

		try {

			// server is listening on port 1234
			server = new ServerSocket(1234);
			server.setReuseAddress(true);

			// running infinite loop for getting
			// client request
			while (true) {

				// socket object to receive incoming client
				// requests
				Socket client = server.accept();

				// Displaying that new client is connected
				// to server
				System.out.println("New client connected"+ client.getInetAddress().getHostAddress());

				// create a new thread object
				ClientHandler clientSock
					= new ClientHandler(client);

				// This thread will handle the client
				// separately
				new Thread(clientSock).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (server != null) {
				try {
					server.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ClientHandler class
	private static class ClientHandler implements Runnable {
		private final Socket clientSocket;

		// Constructor
		public ClientHandler(Socket socket)
		{
			this.clientSocket = socket;
		}

		public void run()
		{
			PrintWriter out = null;
			Scanner ob = new Scanner(System.in);
			try {
				out = new PrintWriter(clientSocket.getOutputStream());// send Info
				System.out.println("Enter \"stop\" to exit.");
				String str="";
				while (!str.equalsIgnoreCase("stop")) {
					str = ob.nextLine();
					// writing the received message from
					// client
					out.println(str);
					out.flush();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			out.close();
		}
	}
}
