package com.softserveinc.edu.webserver;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ResponseBuilder {

	ByteArrayOutputStream raw = new ByteArrayOutputStream();
	Writer writer = new OutputStreamWriter(raw);
	Response response = new Response();
	// default HTML tags
	static final String HTML_START = "<html><title>Java072 Web Server</title><body>\r\n";
	static final String HTML_END = "</body></html>\r\n";
	// email for troubleshooting
	private static final String mailto = "java072@softserve.com";

	/**
	 * Send HTML to user if maximum connection count is reached
	 * 
	 * @param writer
	 *            - output stream from socket
	 * @param code
	 *            - error code
	 * @throws IOException
	 */
	public static void sendHTMLToUser(OutputStream out) throws IOException {
		PrintStream ps = new PrintStream(out);
		ps.print(HTML_START);
		ps.print("<body bgcolor='#008080'>\r\n");
		ps.print("<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>\r\n");
		ps.print("<div align='center'><center></p>\r\n");
		ps.print("<table border='1' width='700' bgcolor='#004080'><tr>\r\n");
		ps.print("<td align='center'><p align='center'><font color='#FFFFFF' size='6'><strong>404 File Not Found</strong></font></p>"
				+ "<p align='left'><font color='#FFFFFF'><strong>The Web Server cannot find the requested file or script."
				+ "check for your URL ,to be sure that the path is correct. Contact the Web Server administrator "
				+ " if the problem persist <a href='mailto:>"
				+ mailto
				+ "'>"
				+ mailto
				+ "</a></strong></font></p><p>&nbsp;</p></td></tr></table>\r\n");
		ps.print("</center></div>\r\n");
		ps.print(HTML_END);
		ps.flush();
	}

	public void addHeader(String version, int code, File file)
			throws IOException {

		// String extension = getFileExtension(file.getName().toLowerCase());
		// String mimeType = getMimes(extension);
		// Sending a Http Response of type
		// HTTP/1.x 200 OK
		// Date : xx/xx/xxxx
		// Server : serverName
		// Content-length : X bytes
		// Content-type : mime type
		writer.write("HTTP/" + version + " " + code + getCodeMessage(code)
				+ "\r\n");
		DateFormat dateFormat = DateFormat.getTimeInstance();
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		writer.write("Date: " + dateFormat.format(new Date()) + "\r\n");
		writer.write("Server: Java072WebServer\r\n");
		writer.write("Accept-Ranges: bytes\r\n");

		if (null != file) {
			writer.write("Content-Length: " + String.valueOf(file.length())
					+ "\r\n");
			String mimeType = getMimes(getFileExtension(file.getName()));
			writer.write("Content-Type: " + mimeType + "\r\n");
		}
		if (code > 400) {
            String message = "<html><body><h1>404 Page Not Found</h1></body></html>";
            writer.write("Content-Length: " + String.valueOf(message.getBytes().length) + "\r\n");
            writer.write("Content-Type: text/html\r\n\r\n");
            writer.write(message);
        }

		writer.write("\r\n");
		writer.flush();
	}

	public void addFile(File file) throws FileNotFoundException, IOException {
		byte[] theData;
		try (DataInputStream fis = new DataInputStream(new BufferedInputStream(
				new FileInputStream(file)))) {
			theData = new byte[(int) file.length()];
			fis.readFully(theData);
		}
		raw.write(theData);
		raw.flush();
	}

	private String getFileExtension(String filename) {

		int pos; // file extension position in the file name
		String ext; // file extension
		// seraching for the position of the last dot(point)
		pos = filename.lastIndexOf(".");
		// extracting the extension file form the extension position+1 to the
		// file length
		ext = (pos > 0) ? filename.substring(pos) : "";
		return ext;
	}

	public Response getResponse() {
		response.setBytes(raw.toByteArray());
		return response;
	}

	public static String getCodeMessage(int code) {
		switch (code) {
		case 200:
			return " OK ";
		case 400:
			return " 400 Bad Request ";
		case 401:
			return " 401 Unauthorized ";
		case 402:
			return " Payment Required ";
		case 403:
			return " Forbidden ";
		case 404:
			return " Not Found ";
		case 405:
			return " Method Not Allowed ";
		case 406:
			return " Not Acceptable ";
		case 500:
			return " Internal Server Error ";
		case 501:
			return " Not Implemented ";
		case 502:
			return " Service Unavailable ";
		case 505:
			return " HTTP Version not supported ";
		default:
			return "";
		}
	}

	public static String getMimes(String fileExtension) {
		switch (fileExtension) {
		case ".htm":
		case ".html":
			return "text/html";
		case ".py":
			return "text/html";
		case ".xsl":
			return "text/xsl";
		case ".gif":
			return "image/gif";
		case ".jpeg":
		case ".jpg":
		case ".jpe":
			return "image/jpeg";
		case ".png":
			return "image/png";
		case ".class":
		case ".bin":
		case ".dms":
		case ".lha":
		case ".lzh":
			return "application/octet-stream";
		case ".jnlp":
			return "application/x-java-jnlp-file";
		case ".doc":
			return "application/msword";
		case ".oda":
			return "application/oda";
		case ".pdf":
			return "application/pdf";
		case ".ai":
		case ".eps":
		case ".ps":
			return "application/postscript";
		case ".smi":
		case ".smil":
			return "application/smil";
		case ".xls":
			return "application/vnd.ms-excel";
		case ".ppt":
			return "application/vnd.ms-powerpoint";
		case ".wbxml":
			return "application/vnd.wap.wbxml";
		case ".wmlc":
			return "application/vnd.wap.wmlc";
		case ".wmlsc":
			return "application/vnd.wap.wmlscriptc";
		case ".bcpio":
			return "application/x-bcpio";
		case ".vcd":
			return "application/x-cdlink";
		case ".pgn":
			return "application/x-chess-pgn";
		case ".cpio":
			return "application/x-cpio";
		case ".csh":
			return "application/x-csh";
		case ".dcr":
		case ".dir":
		case ".dxr":
			return "application/x-director";
		case ".dvi":
			return "application/x-dvi";
		case ".spl":
			return "application/x-futuresplash";
		case ".gtar":
			return "application/x-gtar";
		case ".hdf":
			return "application/x-hdf";
		case ".js":
			return "application/x-javascript";
		case ".skp":
		case ".skd":
		case ".skt":
		case ".skm":
			return "application/x-koan";
		case ".latex":
			return "application/x-latex";
		case ".nc":
		case ".cdf":
			return "application/x-netcdf";
		case ".sh":
			return "application/x-sh";
		case ".shar":
			return "application/x-shar";
		case ".swf":
			return "application/x-shockwave-flash";
		case ".sit":
			return "application/x-stuffit";
		case ".sv4cpio":
			return "application/x-sv4cpio";
		case ".sv4crc":
			return "application/x-sv4crc";
		case ".tar":
			return "application/x-tar";
		case ".tcl":
			return "application/x-tcl";
		case ".tex":
			return "application/x-tex";
		case ".texinfo":
		case ".texi":
			return "application/x-texinfo";
		case ".t":
		case ".tr":
		case ".roff":
			return "application/x-troff";
		case ".man":
			return "application/x-troff-man";
		case ".me":
			return "application/x-troff-me";
		case ".ms":
			return "application/x-troff-ms";
		case ".ustar":
			return "application/x-ustar";
		case ".src":
			return "application/x-wais-source";
		case ".zip":
			return "application/zip";
		case ".au":
		case ".snd":
			return "audio/basic";
		case ".mid":
		case ".midi":
		case ".kar":
			return "audio/midi";
		case ".mpga":
		case ".mp2":
		case ".mp3":
			return "audio/mpeg";
		case ".aif":
		case ".aiff":
		case ".aifc":
			return "audio/x-aiff";
		case ".ram":
		case ".rm":
			return "audio/x-pn-realaudio";
		case ".rpm":
			return "audio/x-pn-realaudio-plugin";
		case ".ra":
			return "audio/x-realaudio";
		case ".wav":
			return "audio/x-wav";
		case ".pdb":
		case ".xyz":
			return "chemical/x-pdb";
		case ".bmp":
			return "image/bmp";
		case ".ief":
			return "image/ief";
		case ".tiff":
		case ".tif":
			return "image/tiff";
		case ".wbmp":
			return "image/vnd.wap.wbmp";
		case ".ras":
			return "image/x-cmu-raster";
		case ".pnm":
			return "image/x-portable-anymap";
		case ".pbm":
			return "image/x-portable-bitmap";
		case ".pgm":
			return "image/x-portable-graymap";
		case ".ppm":
			return "image/x-portable-pixmap";
		case ".rgb":
			return "image/x-rgb";
		case ".xbm":
			return "image/x-xbitmap";
		case ".xpm":
			return "image/x-xpixmap";
		case ".xwd":
			return "image/x-xwindowdump";
		case ".igs":
		case ".iges":
			return "model/iges";
		case ".msh":
		case ".mesh":
		case ".silo":
			return "model/mesh";
		case ".wrl":
		case ".vrml":
			return "model/vrml";
		case ".css":
			return "text/css";
		case ".asc":
		case ".txt":
			return "text/plain";
		case ".rtx":
			return "text/richtext";
		case ".rtf":
			return "text/rtf";
		case ".sgm":
		case ".sgml":
			return "text/sgml";
		case ".tsv":
			return "text/tab-separated-values";
		case ".wml":
			return "text/vnd.wap.wml";
		case ".wmls":
			return "text/vnd.wap.wmlscript";
		case ".etx":
			return "text/x-setext";
		case ".xml":
			return "text/xml";
		case ".mpeg":
		case ".mpg":
		case ".mpe":
			return "video/mpeg";
		case ".qt":
		case ".mov":
			return "video/quicktime";
		case ".avi":
			return "video/x-msvideo";
		case ".movie":
			return "video/x-sgi-movie";
		case ".ice":
			return "x-conference/x-cooltalk";
		default:
			return "content/unknown";
		}
	}

	public static byte[] redirectResponce(String string) {
		
		String responseText = "<HTML><HEAD><META HTTP-EQUIV=\"REFRESH\" CONTENT=\"1; URL=" + string + "\"></HEAD><BODY></BODY></HTML>";
		String response = "HTTP/1.1 200 OK\r\n" 
		+ "Server: YarServer/2012-07-08\r\n"
		+ "Content-Type: text/html\r\n" + "Content-Length: "
		+ responseText.length() + "\r\n"
		+ "Connection: close\r\n\r\n";
		String resault = response + responseText;
				return resault.getBytes();
	}

}
