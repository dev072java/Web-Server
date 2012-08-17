package com.softserveinc.edu.webserver;

public class Request {

	String requestMethod;
	String protocolVersion;
	String requetsText;
	String path;

 
	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

}
