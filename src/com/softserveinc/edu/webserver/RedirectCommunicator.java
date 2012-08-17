package com.softserveinc.edu.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RedirectCommunicator extends Communicator implements Runnable {
	
	Configurator configurator = null;
	ServerSocket serverSocket;
	
	public RedirectCommunicator() throws IOException {
		
		configurator = XMLConfigurator.getInstance();
		serverSocket = new ServerSocket(configurator.getPort());
		
	}
	
	public void run()
	{
		while (true)
		{
			
			try {
				Socket socket;
				socket = serverSocket.accept();
				socket.getOutputStream().write(ResponseBuilder.redirectResponce("http://localhost:8085"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
