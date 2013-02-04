package vnd.virtualarmor.hybridrouter.services.netdevice.rest.v1.test;

import org.junit.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.*;

import org.junit.AfterClass;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import net.juniper.jmp.webSvc.security.AuthorizationContext;
import net.juniper.jmp.security.JSServiceClient;
import com.sun.jersey.api.client.Client;
import net.juniper.jmp.parsers.common.UriContext;
import com.sun.jersey.api.client.ClientResponse;

import vnd.virtualarmor.hybridrouter.VendorConstants;
import vnd.virtualarmor.hybridrouter.rest.v1.test.SnmpRestTest;
import vnd.virtualarmor.hybridrouter.services.netdevice.rest.v1.*;

/*******************************************************************************
 * FILE NAME: ManageNetConfRemoteRestTest.java PURPOSE: Created by Junos Space
 * SDK EJB-Rest Wizard
 * 
 * 
 * Revision History: DATE: AUTHOR: CHANGE: Thu Jan 31 22:19:39 MST 2013 Auto
 * generated Initial Version
 * 
 * 
 ******************************************************************************/
public final class ManageNetConfRemoteRestTest {

	private static final String APPLICATION_CUSTOM_TYPE = "APPLICATION_CUSTOM_TYPE";
	/**
	 * This information is stored in junit.properties file and user can change
	 * from junit.properties file
	 */
	private static final String USER = getProperty("REST_USER_NAME");
	private static final String PASSWORD = getProperty("REST_PASSWORD");
	private static JSServiceClient jsClient = null;
	private static ClientResponse clientResponse = null;
	private static Client jerseyClient = null;
	private static final String HOST = "http://" + getProperty("HOST_NAME")
			+ ":" + getProperty("PORT_REST");
	private static final String contextPath = getProperty("CONTEXT_PATH");

	/**
	 * <pre>
	 * <b>Method:</b>tearDown
	 * 
	 * <b>Description:</b> This is an  auto generated method with stub
	 */
	@AfterClass
	public static void tearDown() throws Exception {
	}

	/**
	 * <pre>
	 * <b>Method:</b>setUp
	 * 
	 * <b>Description:</b> This is an  auto generated method with stub
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		AuthorizationContext authorizationContext = new AuthorizationContext();
		authorizationContext.setUsername(USER);
		authorizationContext.setPassword(PASSWORD);
		UriContext uriContext = new UriContext();
		uriContext.setBaseUrl(HOST);
		uriContext.setAuthorizationCtxt(authorizationContext);
		jsClient = new JSServiceClient(uriContext);
		jerseyClient = jsClient.jerseyHttpClient();
	}

	/**
	 * This method is used to read Information from junit.properties file
	 * 
	 * @param key
	 * @return String
	 */
	public static String getProperty(String key) {
		Properties prop = new Properties();
		try {
			InputStream is = ManageNetConfRemoteRestTest.class
					.getResourceAsStream("/junit.properties");
			if (is == null) {
				return null;
			}
			prop.load(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return prop.getProperty(key);
	}

	/**
	 * <pre>
	 * <b>Method:</b>execScriptXMLTest
	 * 
	 * <b>Description:</b> This is an  auto generated method with stub
	 */
	@Test
	public final void execScriptXMLTest() {
		execScript(MediaType.APPLICATION_XML);
	}

	/**
	 * <pre>
	 * <b>Method:</b>execScriptJSONTest
	 * 
	 * <b>Description:</b> This is an  auto generated method with stub
	 */
	@Test
	public final void execScriptJSONTest() {
		execScript(MediaType.APPLICATION_JSON);
	}

	/**
	 * <pre>
	 * <b>Method:</b>execScript
	 * 
	 * <b>Description:</b> This is an  auto generated method with stub
	 */
	public final void execScript(String dataType) {
		try {

			// Test data
			Long DYNAMIC_OP_SLAX_ID = 3309568L;
			Long TEST_DEVICE_ID = 3145730L;

			String request_URL = HOST + ManageNetConfRemoteRestTest.contextPath
					+ "/hybridrouter" + "/exec-script?deviceId="
					+ TEST_DEVICE_ID + "&scriptId=" + DYNAMIC_OP_SLAX_ID;
			
			if (MediaType.APPLICATION_XML.equals(dataType)) {
				clientResponse = jerseyClient
						.resource(request_URL)
						.accept(VendorConstants.APP_DATATYPE_PREFIX
								+ ".hybridrouter.oid-value+xml;version=1")
						.get(ClientResponse.class);
			} else if (MediaType.APPLICATION_JSON.equals(dataType)) {
				clientResponse = jerseyClient
						.resource(request_URL)
						.accept(VendorConstants.APP_DATATYPE_PREFIX
								+ ".hybridrouter.oid-value+json;version=1")
						.get(ClientResponse.class);
			} else if (APPLICATION_CUSTOM_TYPE.equals(dataType)) {
				clientResponse = jerseyClient
						.resource(request_URL)
						.accept(VendorConstants.APP_DATATYPE_PREFIX
								+ ".hybridrouter.oid-value;version=1")
						.get(ClientResponse.class);
			}

			int status = clientResponse.getStatus();
		//	Response.Status.NO_CONTENT
			if (status != Response.Status.OK.getStatusCode()) {
				fail();
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
