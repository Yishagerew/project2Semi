/**
 * @author Yishagerew L.
 * @DateModified Nov 14, 20146:18:20 PM
 */

package eHealth.rest.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class XMLFormatter {
	
	/**
	 * The following method sets formatting parameters not the real parsing
	 * @param xmlDoc
	 *        Unformatted xml document given as a string
	 * @return
	 *        Returns a formatted xml file
	 * @throws IOException
	 */
public static String getFormattedXML(String xmlDoc) throws IOException
{
	final Document document = parseXmlFile(xmlDoc);

    OutputFormat format = new OutputFormat(document);
    format.setLineWidth(65);
    format.setIndenting(true);
    format.setIndent(2);
    Writer out = new StringWriter();
    XMLSerializer serializer = new XMLSerializer(out, format);
    serializer.serialize(document);
    return out.toString();
}
/**
 * 
 * @param in
 *        A string input for formatting
 * @return
 *       Returns a formatted xml
 */
private static Document parseXmlFile(String in) {
    try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(in));
        return db.parse(is);
    } catch (ParserConfigurationException e) {
        throw new RuntimeException(e);
    } catch (SAXException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

}
