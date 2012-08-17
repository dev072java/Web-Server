package com.softserveinc.edu.webserver;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLConfigurator implements Configurator {

    private File file;
    private Document doc;
    private Map<String, String> props;
    private static XMLConfigurator instance;
	private static final String FILE_PATH = "D:\\SoftServe IT Academy\\WebServer\\WebServer\\src\\com\\softserveinc\\edu\\webserver\\config.xml"; 

	private XMLConfigurator() {
		load(FILE_PATH);
	}
    public static XMLConfigurator getInstance() {
        if (null == instance) {
            return new XMLConfigurator();
        } else {
            return instance;
        }
    }

    @Override
    public void load(String fileName) {
        file = new File(fileName);
        props = new HashMap<>();

        if (file.exists()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(fileName);
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(XMLConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("file doesnt exist");
            System.exit(1);
        }
        this.stepThrough(doc.getDocumentElement());
    }

    @Override
    public void save() {
        writeToFile();
    }

    @Override
    public String getParameter(String paramName) {
        // TODO Auto-generated method stu
        String result = props.get(paramName);
        if (result != null) {
            return result;
        } else {
            return null;
        }
    }

    @Override
    public void setParameter(String name, String value) {
        // TODO Auto-generated method stub
        changeValue(doc, name, value);
        save();
        load(file.getPath());
    }

    private void stepThrough(Node start) {
        if (start.getNodeType() == Node.ELEMENT_NODE) {
            props.put(start.getNodeName(), start.getChildNodes().item(0).getNodeValue());
        }

        for (Node child = start.getFirstChild();
                child != null;
                child = child.getNextSibling()) {
            stepThrough(child);
        }

    }

    private void changeValue(Node start, String elemName, String elemValue) {
        if (start.getNodeName().equals(elemName)) {
            start.getFirstChild().setNodeValue(elemValue);
        }

        for (Node child = start.getFirstChild();
                child != null;
                child = child.getNextSibling()) {
            changeValue(child, elemName, elemValue);
        }

    }

    private void writeToFile() {
        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(file);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = factory.newTransformer();
            transformer.transform(domSource, fileResult);
        } catch (TransformerException ex) {
            // TODO Auto-generated catch block
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getPort() {
        return Integer.parseInt(props.get("port"));
    }

    @Override
    public String getRoot() {
        return props.get("web-root");
    }

    @Override
    public List<String> getMimeTypes() {
        List<String> mimeTypes = new LinkedList<>();
        String[] mimeTypesArray = props.get("mime-types").split(" ");
        mimeTypes.addAll(Arrays.asList(mimeTypesArray));
        return mimeTypes;
    }

    @Override
    public void setPort(int port) {
        setParameter("port", String.valueOf(port));
    }

    @Override
    public void setRoot(String rootPath) {
        setParameter("web-root", String.valueOf(rootPath));

    }

    @Override
    public void setMimeTypes(List<String> mimetypes) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getMaxConnections() {
        return Integer.parseInt(props.get("max-connections"));
    }

    @Override
    public void setMaxConnections(int maxConnections) {
        setParameter("max-connections", String.valueOf(maxConnections));
    }

    //public static void main(String[] args) {
       // Configurator configurator = XMLConfigurator.getInstance();
       // configurator.load("C:\\Users\\4epa\\Documents\\NetBeansProjects\\Web-Server\\src\\com\\softserveinc\\edu\\lms\\webserver\\config.xml");
       // System.out.println(configurator.getPort());
       // System.out.println(configurator.getRoot());
       // System.out.println(configurator.getMimeTypes());
       // System.out.println(configurator.getMaxConnections());
     // }
}
