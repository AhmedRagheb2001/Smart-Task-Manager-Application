package Smart;
		
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server {
	
	public static void main (String [] args)
	{
		ServerSocket server = null;
		ExecutorService executor = Executors.newFixedThreadPool(10);
		int port = 8000;
		
		try
		{
			server =new ServerSocket (port);
			while(true)
			{
				Socket socket = server.accept();
				executor.submit(new ClientHandler(socket));
				
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if(server != null)
				{
					server.close();
				}
				if(executor != null)
				{
					executor.shutdown();
				}
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

}
