package preprocessing;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import errorHandling.ErrorHandler;

/**
 * 
 * Parser that parses XML file
 * 
 * @author Stephen
 *
 */
public class XMLParser {

	private final String ERROR_MESSAGE_PARSING = "XML File Could Not Be Parsed";
	
	/**
	 * Parses XML file and returns Document
	 * @param file: XML file
	 * @return parsed XML file as Document
	 */
    public Document parseXMLFile(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
	        Document document = builder.parse(file);
	        document.getDocumentElement().normalize();
	        return document;
		} catch (Exception e) {
			ErrorHandler.handleError(ERROR_MESSAGE_PARSING);
		}
		return null;
    }

}
