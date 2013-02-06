package vnd.virtualarmor.hybridrouter.utility;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

/**
 * XML related utilities
 * 
 */
public class XmlUtils {

	private static Logger logger = Logger.getLogger(XmlUtils.class);

	/**
	 * Marshall an object to xml
	 * 
	 * @param obj
	 *            Object we want to marshall
	 * @param clazz
	 *            The Class of the object
	 * 
	 * @return String XML representation of the object
	 */
	public static String marshallToXml(Object obj, Class clazz) {
		StringWriter stringWriter = new StringWriter();

		try {
			// Create a JAXB context passing in the class of the object we want
			// to marshall/unmarshall
			JAXBContext context = JAXBContext.newInstance(clazz);

			Marshaller marshaller = context.createMarshaller();

			// Marshal the javaObject and write the XML to the stringWriter
			marshaller.marshal(obj, stringWriter);
		} catch (JAXBException e) {
			logger.error("Exception converting Class " + clazz.getName()
					+ " to XML.  Exception is: " + e.getMessage());
		}

		return stringWriter.toString();
	}

}
