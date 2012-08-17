/*
 * Class ErrorHandler
 * 
 * Version 1.0.0
 * 
 * Date 04.08.2012
 * 
 * Copyright Ivan Kos (c) 2012
 */
package com.softserveinc.edu.webserver;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides error handling for bad requests. When some error occurs - this class
 * creates and returns the correct response that contains the information about
 * the error.
 * 
 * @author Kos Ivan
 * 
 */
public class ErrorHandler extends Handler {

	/**
	 * Create response when some error occurs.
	 * 
	 * @param request
	 *            The request we want to handle.
	 * @return The response with the information about the error.
	 */
	
	@Override
	public Response handle(Request request) {
		System.out.println("ErrorHandler handle is invoked !!!");
		ResponseBuilder responseBuilder = new ResponseBuilder();
		Response response = new Response();
		try {
			System.out.println("ErrorHandler-handle()-addHeader-tryblock");
			responseBuilder.addHeader("1.1", 404, null);
		} catch (IOException ex) {
			System.out.println("ErrorHandler-handle()-addHeader-catchblock");
			Logger.getLogger(ErrorHandler.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		System.out.println("ErrorHandler-handle()-response=blablabla");
		response = responseBuilder.getResponse();
		System.out.println("ErrorHandler-handle()-preReturn");
//	    Response response = new Response();
//	    String t = "<html><body>404</body></html>";
//	    String s = "HTTP/1.1 200 OK\r\n" +
//	    "Date:Sun,02 Jul 2012 22:28:58 GMT\r\n" 
//	    + "Server: 072Server\r\n"
//	    + "Accept-Ranges: bytes\r\n"
//	    + "Content-Length:" + t.getBytes().length + "\r\n"
//	    + "Connection: close\r\n"
//	    + "Connection-Type:text/html\r\n\r\n"
//	    + t + "\r\n";
//	    	
//	    response.setBytes(s.getBytes());
		
		return response;
	}
}
