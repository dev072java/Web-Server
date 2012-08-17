package com.softserveinc.edu.webserver;

import java.util.List;

public interface Configurator {

	/**
	 * 
	 * @param path
	 *            Path to file which you want to load
	 */
	void load(String path);

	/**
	 * Write parameters to file
	 */
	void save();

	/**
	 * 
	 * @param paramName
	 *            Name of parameter which you want to get
	 * @return Value of parameter. If parameter doesn't exist return null
	 */
	String getParameter(String paramName);

	/**
	 * 
	 * @param name
	 *            Name of parameter which you want to change
	 * @param value
	 *            Value that you want to set
	 */
	void setParameter(String name, String value);

	/**
	 * 
	 * @return Current port
	 */
	int getPort();

	/**
	 * 
	 * @return webroot path
	 */
	String getRoot();

	/**
	 * 
	 * @return available mimetypes
	 */
	List<String> getMimeTypes();

	/**
	 * 
	 * @return
	 */
	int getMaxConnections();

	/**
	 * 
	 * @param port
	 */
	void setPort(int port);

	/**
	 * 
	 * @param rootPath
	 */
	void setRoot(String rootPath);

	/**
	 * 
	 * @param mimetypes
	 *            !!! NOT IMPLEMENTED YET !!!
	 */
	void setMimeTypes(List<String> mimetypes);

	/**
	 * 
	 * @param maxConnections
	 */
	void setMaxConnections(int maxConnections);
}
