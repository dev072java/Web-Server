/*
 * Class Handler
 * 
 * Version 1.0.0
 * 
 * Date 04.08.2012 1:26
 * 
 * Copyright Ivan Kos (c) 2012
 */
package com.softserveinc.edu.webserver;

/**
 * Provides the required functionality for handling clients requests.
 * 
 * @author Kos Ivan
 * 
 */
abstract public class Handler {

	// this field is required to get different types
	// of responses. It means that response can send
	// a file or an error and we need to know how
	// to handle it properly.
	private Handler next;
	
	// subclasses uses this reference !!!
	protected Configurator configurator;

	// the simplest constructor (do nothing :))
	public Handler() {
	}

	/**
	 *  Constructor with one parameter.
	 * 
	 * @param next 
	 */
	protected Handler(Handler next) {
		this.next = next;
	}

	/**
	 * Method handle() processes requests, and return the correct response.
	 * 
	 * @param request
	 *            The request we want to handle.
	 * @return The correct response.
	 */
	public Response handle(Request request) {
    System.out.println("Handler handler() is invoked !!!");
		Response response = null;
		if (next != null) {
			response = next.handle(request);
		}
		return response;
	}
}
