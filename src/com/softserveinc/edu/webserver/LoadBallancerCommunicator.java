package com.softserveinc.edu.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoadBallancerCommunicator extends Communicator implements Runnable {

	

	public LoadBallancerCommunicator() throws IOException {
		configurator = XMLConfigurator.getInstance();
		serverSoket = new ServerSocket(configurator.getPort());
		taskQueue = new TaskQueue();
		queueThread = new Thread(taskQueue);
		queueThread.setDaemon(true);
		queueThread.start();
	}
	
	public void run() {
		int i = 0;
		serverThread = Thread.currentThread();
		Socket socket;
		while (isRunning) {
			try {
				if (i == 5)
					i = 0;
				socket = serverSoket.accept();
				if (serverThread.isInterrupted()) {
					break;
				} else if (socket != null) {
					try {

						if (configurator.getMaxConnections() > taskQueue.queue
								.size()) {
							if (i == 3 || i == 4) {
								System.out.println("case" + i);
								final SocketProcessor processor = new SocketProcessor(
										socket);
								final Thread thread = new Thread(processor);
								thread.start();
								taskQueue.queue.add(processor);
							}
							if (i == 0 || i == 1 || i == 2) {
								System.out.println("case" + i);
								socket.getOutputStream()
										.write(ResponseBuilder
												.redirectResponce("http://localhost:8085"));
								socket.getOutputStream().flush();
								socket.close();
							}
							if (i == 5 || i == 6 || i == 7 || i == 8 || i == 9) {
								System.out.println("case" + i);
								socket.getOutputStream()
										.write(ResponseBuilder
												.redirectResponce("http://localhost:8086"));
								socket.getOutputStream().flush();
								socket.close();
							}
						} else {
							ResponseBuilder.sendHTMLToUser(socket
									.getOutputStream());
							socket.close();
						}
						i++;
					} catch (IOException ignored) {
					}
				}
			} catch (IOException e) {
				shutdownServer();
			}
		}

	}
}
