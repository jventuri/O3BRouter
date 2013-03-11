package vnd.virtualarmor.hybridrouter.services.netdevice;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.juniper.jmp.cmp.serviceApiCommon.InternalApiContext;
import net.juniper.jmp.security.JSServiceClient;
import net.juniper.jmp.webSvc.security.AuthorizationContext;

import org.apache.log4j.Logger;

import vnd.virtualarmor.hybridrouter.Constants;
import vnd.virtualarmor.hybridrouter.VendorConstants;
import vnd.virtualarmor.hybridrouter.model.OidValueResponse;
import vnd.virtualarmor.hybridrouter.model.PollingTimerInfo;
import vnd.virtualarmor.hybridrouter.utility.PropertyManager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

/**
 * Bean responsible for polling SNMP device for OID values.
 * 
 */
@Stateless(name = "SnmpPollingBean")
@Local(SnmpPollingBeanLocal.class)
@Remote(SnmpPollingBeanRemote.class)
public class SnmpPollingBean implements TimedObject, SnmpPollingBeanLocal,
		SnmpPollingBeanRemote
{
	Logger logger = Logger.getLogger(SnmpPollingBean.class);

	@Resource
	TimerService timerService;

	public void init()
	{
		boolean timerExists = false;
		if (timerService.getTimers() != null)
		{
			Collection<Timer> timerCollection = timerService.getTimers();

			// ensure only one SnmpPollingTimer is running
			for (Timer timer : timerCollection)
			{
				if (timer.getInfo() instanceof PollingTimerInfo)
				{
					PollingTimerInfo info = (PollingTimerInfo) timer.getInfo();
					if (info.getName().equals("SnmpPollingTimer"))
					{
						timerExists = true;
						break;
					}
				}
			}
		}

		// create the timer's application info
		if (!timerExists)
		{
			PollingTimerInfo pti = new PollingTimerInfo();
			pti.setFailedCount(0);
			pti.setName("SnmpPollingTimer");

			// create the timer, starting 30 seconds from now and running every
			// 5
			// seconds
			timerService.createTimer(30000, 5000, pti);
			logger.debug("Successfully created SnmpPollingTimer timer!");
		}
	}

	@Override
	public void ejbTimeout(Timer timer)
	{
		try
		{
			if (timer.getInfo() instanceof PollingTimerInfo)
			{
				PollingTimerInfo info = (PollingTimerInfo) timer.getInfo();
				int failedCount = info.getFailedCount();

				String oidXml = getOidValue();

				OidValueResponse oidResponse = createResponseObject(oidXml);

				// pass or fail biz rules?
				if (applyBusinessRules(oidResponse.getValue()))
				{
					// pass, reset failed counter
					failedCount = 0;
				} else
				{
					// failed, increment counter
					failedCount++;

					// if exceeds tolerance then update configuration
					if (failedCount >= 5)
					{
						// read demo properties
						Properties props = PropertyManager
								.loadProperties(Constants.PROPERTY_FILE_NAME);
						String demoScriptId = props
								.getProperty(Constants.DEMO_SCRIPT_PROP);
						Long scriptId = Long.valueOf(demoScriptId);
						
						String demoPrimaryDeviceId = props
								.getProperty(Constants.DEMO_DEVICE_PRIMARY_PROP);
						Long primaryDeviceId = Long
								.valueOf(demoPrimaryDeviceId);
						
						String demoSecondaryDeviceId = props
								.getProperty(Constants.DEMO_DEVICE_SECONDARY_PROP);
						Long secondaryDeviceId = (demoSecondaryDeviceId != null && demoSecondaryDeviceId
								.length() > 0) ? Long
								.valueOf(demoSecondaryDeviceId) : 0;

						HashMap<String, String> params = new HashMap<String, String>();

						String key1 = props
								.getProperty(Constants.DEMO_SCRIPT_KEY1);
						String val1 = props
								.getProperty(Constants.DEMO_SCRIPT_VALUE1);
						if (key1 != null && key1.length() > 0 && val1 != null
								&& val1.length() > 0)
						{
							params.put(key1, val1);
						}

						String key2 = props
								.getProperty(Constants.DEMO_SCRIPT_KEY2);
						String val2 = props
								.getProperty(Constants.DEMO_SCRIPT_VALUE2);
						if (key2 != null && key2.length() > 0 && val2 != null
								&& val2.length() > 0)
						{
							params.put(key2, val2);
						}

						String key3 = props
								.getProperty(Constants.DEMO_SCRIPT_KEY3);
						String val3 = props
								.getProperty(Constants.DEMO_SCRIPT_VALUE3);
						if (key3 != null && key3.length() > 0 && val3 != null
								&& val3.length() > 0)
						{
							params.put(key3, val3);
						}

						// update the configuration
						// Create and initialize SNMP polling timer
						ManageNetConfLocal manageNetConf = ManageNetConfLocal.Naming
								.getInstance();

						manageNetConf.execScript(createApiContext(),
								primaryDeviceId, secondaryDeviceId, scriptId, params);

						// reset the counter
						failedCount = 0;
					}
				}

				info.setFailedCount(failedCount);
			}
		} catch (Exception e)
		{
			logger.error("Error in ejbTimout: " + e.getMessage());
		}
	}

	private InternalApiContext createApiContext()
	{
		InternalApiContext iac = new InternalApiContext();

		// credentials for HTTP Request
		AuthorizationContext authCtxt = new AuthorizationContext();

		// set username to REST user
		authCtxt.setUsername(Constants.USER_REST);

		if (iac.getBaseUrl() == null)
		{
			iac.setAuthDetailsAndBaseUrl(authCtxt, Constants.BASE_URL);
		} else
		{
			iac.setAuthDetailsAndBaseUrl(authCtxt, iac.getBaseUrl());
		}

		return iac;
	}

	private boolean applyBusinessRules(String oidStr)
	{
		boolean isRulePassed = false;

		Integer oidVal = new Integer(oidStr);

		// our simple business rule says latency < 70 is a failed condition
		if (oidVal < 70)
		{
			isRulePassed = false;
		}
		// business rule has passed test, so reset the counter
		else
		{
			isRulePassed = true;
		}

		return isRulePassed;
	}

	private String getOidValue()
	{
		InternalApiContext apic = createApiContext();

		// Create an instance of JSServiceClient using ApiContext
		JSServiceClient client = new JSServiceClient(apic);
		Client jerseyClient = client.jerseyHttpClient();

		// Test data
		String deviceAddress = "128.254.128.1";
		int port = 161;
		String community = "va-internal";
		String oid = "oid=.1.3.6.1.4.1.2636.3.3.1.1.8.510";

		String host = apic.getBaseUrl();
		String request_URL = host + VendorConstants.APP_URL_PREFIX
				+ "/hybridrouter" + "/oid-values/" + deviceAddress + "/" + port
				+ "/" + community + "?" + oid;

		ClientResponse clientResponse = jerseyClient
				.resource(request_URL)
				.accept(VendorConstants.APP_DATATYPE_PREFIX
						+ ".hybridrouter.oid-value+xml;version=1")
				.get(ClientResponse.class);

		String oidValue = "";
		int status = clientResponse.getStatus();
		if (status != Response.Status.OK.getStatusCode()
				&& status != Response.Status.NO_CONTENT.getStatusCode())
		{
			logger.error("Error calling SnmpDeviceRest, status is: " + status);
		} else
		{
			oidValue = clientResponse.getEntity(String.class);
		}

		return oidValue;
	}

	// Creates an object from the oid-values xml response
	private OidValueResponse createResponseObject(String xml)
	{
		OidValueResponse oidResponse = new OidValueResponse();

		try
		{
			final JAXBContext context = JAXBContext
					.newInstance(OidValueResponse.class);

			final StringWriter stringWriter = new StringWriter();
			stringWriter.append(xml);

			final Unmarshaller unmarshaller = context.createUnmarshaller();

			// Unmarshal the XML in the stringWriter back into an object
			oidResponse = (OidValueResponse) unmarshaller
					.unmarshal(new StringReader(stringWriter.toString()));
		} catch (JAXBException e)
		{
			logger.error("Exception converting XML " + xml
					+ " to a Response object.  Exception is: " + e.getMessage());
		}

		return oidResponse;
	}

}
