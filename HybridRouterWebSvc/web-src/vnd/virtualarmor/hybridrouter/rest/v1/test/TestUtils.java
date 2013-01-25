package vnd.virtualarmor.hybridrouter.rest.v1.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Test utility methods
 * 
 * @author JayVenturini
 *
 */
public class TestUtils {

	
	/**
	 * This method is used to read Information from junit.properties file
	 * @param key
	 * @return String
	 */
	public static String getProperty(Class clazz, String key) {
		Properties prop = new Properties();
		try {
			InputStream is = clazz.getResourceAsStream("/junit.properties");
			if (is == null) {
				return null;
			}
			prop.load(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
}
