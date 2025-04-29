package Smart;

import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {
	
	private Task task;
	private Socket socket= null;
	
	public ClientHandler(Socket clientSocket)
	{
		this.socket=clientSocket;
	}
	
	public void run()
	{
		
		
	}
	
	 

}
