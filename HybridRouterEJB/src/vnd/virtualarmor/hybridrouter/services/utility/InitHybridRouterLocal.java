package vnd.virtualarmor.hybridrouter.services.utility;

import javax.ejb.Stateless;

import net.juniper.jmp.ApiContext;
import net.juniper.jmp.cmp.serviceApiCommon.InternalApiContext;
import net.juniper.jmp.cmp.system.AppEnabledCallback;
import net.juniper.jmp.cmp.system.AppInitBasicContext;
import net.juniper.jmp.cmp.system.JxServiceLocator;
import net.juniper.jmp.cmp.system.appinit.AppInitContext;
import net.juniper.jmp.security.JSServiceClient;
import net.juniper.jmp.webSvc.security.AuthorizationContext;

import org.apache.log4j.Logger;

import vnd.virtualarmor.hybridrouter.Constants;
import vnd.virtualarmor.hybridrouter.services.netdevice.SnmpPollingBeanLocal;
import vnd.virtualarmor.hybridrouter.services.netdevice.SnmpPollingBeanRemote;
import vnd.virtualarmor.hybridrouter.utility.ClientUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * Initialization tasks for Hybrid Router
 * 
 */
@Stateless(name = "HybridRouter.AppEnabledCallbackEJB")
public class InitHybridRouterLocal implements AppEnabledCallback
{
	Logger logger = Logger.getLogger(InitHybridRouterLocal.class);

	/**
	 * Override execute() method of AppEnabledCallback. Execute method will run,
	 * every time the App is deployed, or jboss is restarted.
	 */
	@Override
	public void execute(AppInitBasicContext ctx) throws Exception
	{

		// only run this code once, independent of how many cluster nodes there
		// are
		if (ctx.isMasterNode())
		{
			// Get the context state
			String attr = null;
			try
			{
				AppInitContext ctx2 = (AppInitContext) ctx;
				attr = (String) ctx2
						.getAttribute(AppInitContext.APPSTARTSTATE_ATTR);
			} catch (Exception ex)
			{
				logger.error("Failed to get context state: " + ex.getMessage());
				return;
			}

			// Run initialization on fresh install of the application
			if (AppInitContext.APPSTARTSTATE_FRESHINSTALL.equals(attr))
			{
				InternalApiContext apic = new InternalApiContext();
				Client client = ClientUtils.setupHttpClient(apic);
				ClientResponse response = null;

				// See if the queue exists
				StringBuffer queueUri = new StringBuffer(apic.getBaseUrl())
						.append(Constants.HORNETQ_PATH).append("/")
						.append(Constants.QUEUE_NAME);
				response = client.resource(queueUri.toString()).head();
				if (response.getClientResponseStatus() != ClientResponse.Status.OK)
				{
					// Queue does not exist, lets create one
					String queueName = "<queue name=\"" + Constants.QUEUE_NAME
							+ "\"><durable>true</durable></queue>";

					logger.debug("apic.getBaseUrl()=" + apic.getBaseUrl()
							+ " and queueName= " + queueName);

					response = client
							.resource(
									apic.getBaseUrl() + Constants.HORNETQ_PATH)
							.type("application/hornetq.jms.queue+xml")
							.post(ClientResponse.class, queueName);

					if (response.getClientResponseStatus() != ClientResponse.Status.CREATED)
					{
						Status stat = response.getClientResponseStatus();
						int statusCode = stat.getStatusCode();
						String reason = stat.getReasonPhrase();

						String errorMsg = response.getEntity(String.class);
						logger.error("Failed to create '" + queueName
								+ ". Status code=" + statusCode
								+ ". reason is: " + reason + ". Error msg is: "
								+ errorMsg);
						return;
					} else
					{
						logger.info("Successfully created queue resource '"
								+ queueName);
					}
				}

				logger.debug("InitHybridRouterLocal b4 calling snmpPolling.init()");
				// Create and initialize SNMP polling timer
				SnmpPollingBeanRemote snmpPolling = JxServiceLocator
						.lookup("SnmpPollingBean");
				snmpPolling.init();
				logger.debug("InitHybridRouterLocal after calling snmpPolling.init()");
			}
		}

	}
}
