
package Smart;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Set;
import java.util.HashSet;

public class Server {
	private static Set<String> usernames = new HashSet<>();
	private static Set<Task> tasks = new HashSet<>();
	public static void main (String [] args)
	{
		int port = 8003;
		ServerSocket server = null;
		ExecutorService executor = Executors.newFixedThreadPool(10);
		System.out.println("Waits for a connection");
		
			try {
				server = new ServerSocket(port);
				
				while(true)
				{
					Socket socket = server.accept();
					
					executor.submit(new ClientHandler(socket,usernames,tasks));
					
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			finally {
				try
				{
					if(server != null)
					{
						server.close();
					}
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}



