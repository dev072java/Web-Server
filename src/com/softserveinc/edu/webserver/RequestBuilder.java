package com.softserveinc.edu.webserver;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestBuilder {

	Request request = new Request();
	String requestText;

	public RequestBuilder(String requestText) {
		this.requestText = requestText;
		try {
			parseRequest();
		} catch (IOException ex) {
			Logger.getLogger(Request.class.getName()).log(Level.SEVERE,
					"Error in Request constructor", ex);
		}
	}

	private void parseRequest() throws IOException {
		StringTokenizer st = new StringTokenizer(requestText, "\r\n");
		String firstLine = st.nextToken();
		st = new StringTokenizer(firstLine);
		try {
			request.setRequestMethod(st.nextToken());
			request.setPath(st.nextToken());
			String versionHTTP = st.nextToken();
			int dotPos = versionHTTP.indexOf("1.");
			request.setProtocolVersion(versionHTTP.substring(dotPos + 2)
					.equals("0") ? "1.0" : "1.1");
		} catch (NoSuchElementException ex) {
			throw new IOException("Could not parse first line");
		}
	}

	public Request getRequest() {
		return request;
	}

	public String getRequestText() {
		return requestText;
	}

	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}

	// public static void main(String[] args) {
	// String requestText = "POST /1/7/post.php HTTP/1.1\n"
	// +
	// "Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, ap-plication/x-shockwave-flash, */*"
	// + "Referer: http://www.httprecipes.com/1/7/post.php"
	// + "Accept-Language: en-us";
	// RequestBuilder rb = new RequestBuilder(requestText);
	// Request request = rb.getRequest();
	// System.out.println(request.getProtocolVersion());
	// System.out.println(request.getRequestMethod());
	// System.out.println(request.getPath());
	// }
}
