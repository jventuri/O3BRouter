package vnd.virtualarmor.hybridrouter.rest.v1.test;

import static org.junit.Assert.fail;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.juniper.jmp.parsers.common.UriContext;
import net.juniper.jmp.security.JSServiceClient;
import net.juniper.jmp.webSvc.security.AuthorizationContext;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import vnd.virtualarmor.hybridrouter.VendorConstants;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

/*******************************************************************************
 * FILE NAME: SnmpRestTest.java PURPOSE: Created by Junos Space SDK EJB-Rest
 * Wizard
 * 
 * 
 * Revision History: DATE: AUTHOR: CHANGE: Sat Oct 27 22:54:08 MDT 2012 Auto
 * generated Initial Version
 * 
 * 
 ******************************************************************************/
public final class SnmpRestTest {

	private static final String APPLICATION_CUSTOM_TYPE = "APPLICATION_CUSTOM_TYPE";
	/**
	 * This information is stored in junit.properties file and user can change
	 * from junit.properties file
	 */
	private static final String USER = TestUtils.getProperty(
			SnmpRestTest.class, "REST_USER_NAME");
	private static final String PASSWORD = TestUtils.getProperty(
			SnmpRestTest.class, "REST_PASSWORD");
	private static JSServiceClient jsClient = null;
	private static ClientResponse clientResponse = null;
	private static Client jerseyClient = null;
	private static final String HOST = "http://"
			+ TestUtils.getProperty(SnmpRestTest.class, "HOST_NAME") + ":"
			+ TestUtils.getProperty(SnmpRestTest.class, "PORT_REST");
	private static final String contextPath = TestUtils.getProperty(
			SnmpRestTest.class, "CONTEXT_PATH");

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
	 * <pre>
	 * <b>Method:</b>getOidValueXMLTest
	 * 
	 * <b>Description:</b> This is an  auto generated method with stub
	 */
	@Test
	public final void getOidValueXMLTest() {
		getOidValue(MediaType.APPLICATION_XML);
	}

	/**
	 * <pre>
	 * <b>Method:</b>getOidValueJSONTest
	 * 
	 * <b>Description:</b> This is an  auto generated method with stub
	 */
	@Test
	public final void getOidValueJSONTest() {
		getOidValue(MediaType.APPLICATION_JSON);
	}

	/**
	 * <pre>
	 * <b>Method:</b>getOidValue
	 * 
	 * <b>Description:</b> This is an  auto generated method with stub
	 */
	public final void getOidValue(String dataType) {
		try {

			// value = hybridRouter.getOidValue("udp:128.254.128.1", 161,
			// "va-internal", ".1.3.6.1.4.1.2636.3.3.1.1.8.510");

			// Test data
			String deviceAddress = "128.254.128.1";
			int port = 161;
			String community = "va-internal";
			String oid = "oid=.1.3.6.1.4.1.2636.3.3.1.1.8.510";

			String request_URL = HOST + SnmpRestTest.contextPath
					+ "/hybridrouter" + "/oid-values/" + deviceAddress + "/"
					+ port + "/" + community + "?" + oid;
			System.out.println("\nrequest URL: " + request_URL);
			
			System.out.println("XML accept: " + VendorConstants.APP_DATATYPE_PREFIX
			+ ".hybridrouter.oid-value+xml;version=1");
			
			System.out.println("JSON accept: " + VendorConstants.APP_DATATYPE_PREFIX
					+ ".hybridrouter.oid-value+json;version=1");
					
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
			if (status != Response.Status.OK.getStatusCode()
					&& status != Response.Status.NO_CONTENT.getStatusCode()) {
				fail();
			}

			String output = clientResponse.getEntity(String.class);

			System.out.println("For OID key: " + oid + " the value is: "
					+ output);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * <pre>
	 * <b>Method:</b>tearDown
	 * 
	 * <b>Description:</b> This is an  auto generated method with stub
	 */
	@AfterClass
	public static void tearDown() throws Exception {
	}

}
