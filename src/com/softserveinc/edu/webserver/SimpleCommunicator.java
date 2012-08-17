package com.softserveinc.edu.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleCommunicator extends Communicator implements Runnable {

	/**
	 * 
	 * @param port
	 *            - port of web server
	 * @throws IOException
	 */
	public SimpleCommunicator() throws IOException {
		configurator = XMLConfigurator.getInstance();
		serverSoket = new ServerSocket(configurator.getPort());
		taskQueue = new TaskQueue();
		queueThread = new Thread(taskQueue);
		queueThread.setDaemon(true);
		queueThread.start();
	}

	/**
	 * listen to new clients
	 */
	@Override
	public void run() {
		serverThread = Thread.currentThread();
		Socket socket;
		while (true) {
			try {
				socket = serverSoket.accept();
				if (serverThread.isInterrupted()) {
					break;
				} else if (socket != null) {
					try {
						final SocketProcessor processor = new SocketProcessor(
								socket);
						final Thread thread = new Thread(processor);
						thread.start();
						if (configurator.getMaxConnections() > taskQueue.queue
								.size()) {
							taskQueue.queue.add(processor);
						} else {
							ResponseBuilder.sendHTMLToUser(socket
									.getOutputStream());
							socket.close();
						}
					} catch (IOException ignored) {
					}
				}
			} catch (IOException e) {
				shutdownServer();
			}
		}
	}

	/**
	 * stop web server work
	 */
	public synchronized void shutdownServer() {
		for (SocketProcessor socketProcessor : taskQueue.queue) {
			socketProcessor.close();
		}
		if (!serverSoket.isClosed()) {
			try {
				serverSoket.close();
			} catch (IOException ignored) {
			}
		}
	}

	/**
	 * 
	 * @author Oleh Halushchak
	 * 
	 */
	/*public class SocketProcessor implements Runnable {

		boolean isRequestTextLoader = false; // true if all text of request is
												// loader to server
		Socket socket; // socket - socket connection object
		InputStream inputStream;
		OutputStream outputStream;
		String requestText = "";
		String responseText = "";

		*//**
		 * 
		 * @param socketParam
		 *            - client socket
		 * @throws IOException
		 *//*
		SocketProcessor(Socket socketParam) throws IOException {
			socket = socketParam;
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		}

		*//**
		 * listening to the new client's requests
		 *//*
		@Override
		public void run() {
			String temp;
			while (!socket.isClosed()) {
				String line = "";
				BufferedReader bufferReader = new BufferedReader(
						new InputStreamReader(inputStream));
				try {
					while (true) {
						temp = bufferReader.readLine();
						if (temp == null || temp.trim().length() == 0) {
							break;
						}
						line += temp + "\n";
					}
					isRequestTextLoader = true;
					if (!"".equals(line)) {
						setRequestText(line);
						System.out.print(line + "Request received!\n");
					}
				} catch (IOException e) {
					close();
				}
			}
		}

		*//**
		 * 
		 * @param responseText
		 *            - response text
		 *//*
		public synchronized void sendTestResponse(String responseText) {
			
			 * String response = "HTTP/1.1 200 OK\r\n" + "Server:
			 * YarServer/2012-07-08\r\n" + "Content-Type: text/html\r\n" +
			 * "Content-Length: " + responseText.length() + "\r\n" +
			 * "Connection: close\r\n\r\n"; String resault = response +
			 * responseText; try { outputStream.write(resault.getBytes());
			 * outputStream.flush(); } catch (IOException e) { close(); }
			 
			try {
				// responseText = "Hello\n\r";
				outputStream.write(responseText.getBytes());
				outputStream.flush();
				this.close();
			} catch (IOException e) {
				close();
			}
		}

		*//**
		 * 
		 * @param response
		 *            - object with response info
		 *//*
		public synchronized void sendResponse(Response response) {
			try {
				outputStream.write(response.getBytes());
				outputStream.flush();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				Logger.getLogger(SimpleCommunicator.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		*//**
		 * 
		 * @param text
		 *            - request text
		 *//*
		public void setRequestText(String text) {
			requestText = text;
		}

		*//**
		 * close current client connection
		 *//*
		public synchronized void close() {
			taskQueue.queue.remove(this);
			if (!socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException ignored) {
				}
			}
		}
	}*/
}
