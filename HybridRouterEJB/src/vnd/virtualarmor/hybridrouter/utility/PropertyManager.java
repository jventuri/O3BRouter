package vnd.virtualarmor.hybridrouter.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * Manages Properties
 * 
 */
public class PropertyManager {
	
	private static Logger logger = Logger.getLogger(PropertyManager.class);

	/**
	 * Ensure static access only.
	 */
	private PropertyManager() {
	}

	/**
	 * Load properties from the file system.
	 * 
	 * @param fileName - name of property file
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties loadProperties(String fileName) throws IOException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();

		InputStream in = classLoader.getResourceAsStream(fileName);
		if (in == null) {
			throw new FileNotFoundException(fileName);
		}

		Properties properties = new Properties();
		properties.load(in);
		in.close();

		return (properties);
	}

	public static InputStream getResourceAsStream(String name) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();

		return classLoader.getResourceAsStream(name);
	}

}
