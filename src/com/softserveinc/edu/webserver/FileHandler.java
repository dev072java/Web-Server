/*
 * Class FileHandler
 * 
 * Version 1.0.0.0
 * 
 * Date 04.08.2012 2:25
 * 
 * Ivan Kos (c) 2012
 */
package com.softserveinc.edu.webserver;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Web Server use class FileHandler to create a correct response for a request
 * it get from the client. This class contains all required methods for that
 * 
 * @author Kos Ivan
 */
public class FileHandler extends Handler {

	/**
	 * The Constructor with one parameter. Initialize base-class field Handler
	 * next.
	 * 
	 * @param handler
	 */
	public FileHandler(Handler handler) {
		super(handler);
		System.out.println("FileHandler object is created!");
	}

	/**
	 * The handle() method handles the HTTP request and if the request is
	 * correct then creates the proper response.
	 * 
	 * @param request
	 *            The request to handle.
	 * @return The class Response object.
	 */

	@Override
	public Response handle(Request request) {
		System.out.println("FileHandler handle() method is invoked !!!");
		// the response object to return
		Response response = null;

		// split the first request line to get method,
		// path and HTTP protocol version.
		String requestMethod = request.getRequestMethod();
		String path = request.getPath();
		String protocolVersion = request.getProtocolVersion();

		// at this pont we have the physical path to the file
		// so we can try to create a FileInputStream or
		// if the file does not exist throw an error
		configurator = XMLConfigurator.getInstance();
		File file = new File(getPhysicalPath(path, configurator.getRoot()));
		
		System.out.println(getContent(path));
		
		

		if ( /* !getContent(path).equalsIgnoreCase("ERROR_FORMAT") && */file
				.exists() && requestMethod.equalsIgnoreCase("GET")) {
			System.out
					.println("FileHandler is trying to handle the request !!!");
			try {
				ResponseBuilder responseBuilder = new ResponseBuilder();
				responseBuilder.addHeader(protocolVersion, 200, file);
				System.out.println("FileHanlder handle if addFile");
				responseBuilder.addFile(file);

				response = responseBuilder.getResponse();
			} catch (IOException ex) {
				System.out.println("FileHanlder handle if catchBlock");
				Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		} else {
			System.out
					.println("FileHandler can't handle the request! super.handle() is invoked !");
			response = super.handle(request);
		}
		return response;
	}

	/**
	 * Add a slash to the end of the path, if there is not a slash there
	 * already. This method adds the correct type of slash, depending on the
	 * operating system.
	 * 
	 * @param path
	 *            The path to add a slash to.
	 * @return The path with a slash added.
	 */
	public String addSlash(String path) {
		path = path.trim();
		if (path.endsWith("" + File.separatorChar)) {
			return path;
		} else {
			return path + File.separatorChar;
		}
	}

	/**
	 * Determine the correct "content type" based on the file extension.
	 * 
	 * @param path
	 *            The file being transfered.
	 * @return The correct content type for this file.
	 */
	private String getContent(String path) {
		path = path.toLowerCase();
		if (path.endsWith(".html") || path.endsWith(".htm")
				|| path.endsWith("/")) {
			return "text/html";
		} else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (path.endsWith(".png")) {
			return "image/png";
		} else if (path.endsWith(".gif")) {
			return "image/gif";
		} else if (path.endsWith(".css")) {
			return "text/css";
		} else if (path.endsWith(".js")) {
			return "text/javascript";
		} else {
			return "ERROR_FORMAT";
		}
	}

	/**
	 * Translated the URL into a local disk file's path and return it.
	 * 
	 * @param path
	 *            The path (URL) we want to translate.
	 * @param httproot
	 *            The root folder.
	 * @return The physical path to the file on the disk.
	 */
	public String getPhysicalPath(String path, String httproot) {

		String physicalPath = addSlash(httproot);

		path = path.replace('/', File.separatorChar);
		path = path.substring(1); // to avoid double slash
		physicalPath += path;

		// if there is no file specified , default
		// to index.html
		if (physicalPath.endsWith(File.separator)) {
			physicalPath += "index.html";
		}
		return physicalPath;
	}
}
