package com.softserveinc.edu.webserver;

import com.softserveinc.edu.webserver.Communicator.SocketProcessor;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue implements Runnable {

	LinkedBlockingQueue<SocketProcessor> queue;

	public TaskQueue() {
		/**
		 * taskQueue
		 */
		queue = new LinkedBlockingQueue<>();
	}

	@Override
	public void run() {
		while (true) {
			for (SocketProcessor socket : queue) {
				if (socket.isRequestTextLoader) {
					socket.isRequestTextLoader = false;
					// TODO
					Handler handler = new FileHandler(new ErrorHandler());
					// @SuppressWarnings("unused")
					RequestBuilder rb = new RequestBuilder(socket.requestText);
					Request request = rb.getRequest();
					Response response = handler.handle(request);
					socket.sendResponse(response);
					queue.remove(socket);
					socket.close();
					// socket.sendTestResponse(socket.requestText);
					/*
					 * try { socket.sendResponse( "<html><body><h1>Hello
					 * World!!!</h1></body></html>");
					 * socket.bufferWritter.flush(); System.out.print("Response
					 * sent!"); } catch (IOException e) { // TODO Auto-generated
					 * catch block e.printStackTrace(); }
					 */
				}
			}
		}
	}
}
