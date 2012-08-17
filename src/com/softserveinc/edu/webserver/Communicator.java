package com.softserveinc.edu.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract public class Communicator {
	
	Configurator configurator = null;
	ServerSocket serverSoket;
	Thread serverThread;
	TaskQueue taskQueue;
	Thread queueThread;

	public class SocketProcessor implements Runnable {

		boolean isRequestTextLoader = false; // true if all text of request is
												// loader to server
		Socket socket; // socket - socket connection object
		InputStream inputStream;
		OutputStream outputStream;
		String requestText = "";
		String responseText = "";

		/**
		 * 
		 * @param socketParam
		 *            - client socket
		 * @throws IOException
		 */
		SocketProcessor(Socket socketParam) throws IOException {
			socket = socketParam;
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		}

		/**
		 * listening to the new client's requests
		 */
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
						System.out.print(line + "\n");
					}
				} catch (IOException e) {
					close();
				}
			}
		}

		/**
		 * 
		 * @param responseText
		 *            - response text
		 */
		public synchronized void sendTestResponse(String responseText) {
			/*
			 * String response = "HTTP/1.1 200 OK\r\n" + "Server:
			 * YarServer/2012-07-08\r\n" + "Content-Type: text/html\r\n" +
			 * "Content-Length: " + responseText.length() + "\r\n" +
			 * "Connection: close\r\n\r\n"; String resault = response +
			 * responseText; try { outputStream.write(resault.getBytes());
			 * outputStream.flush(); } catch (IOException e) { close(); }
			 */
			try {
				// responseText = "Hello\n\r";
				outputStream.write(responseText.getBytes());
				outputStream.flush();
				this.close();
			} catch (IOException e) {
				close();
			}
		}

		/**
		 * 
		 * @param response
		 *            - object with response info
		 */
		public synchronized void sendResponse(Response response) {
			try {
				outputStream.write(response.getBytes());
				outputStream.flush();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				Logger.getLogger(LoadBallancerCommunicator.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		/**
		 * 
		 * @param text
		 *            - request text
		 */
		public void setRequestText(String text) {
			requestText = text;
		}

		/**
		 * close current client connection
		 */
		public synchronized void close() {
			if (!socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException ignored) {
				}
			}
		}
	}
}
