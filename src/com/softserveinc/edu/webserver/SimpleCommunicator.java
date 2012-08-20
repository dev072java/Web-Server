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

	
}
