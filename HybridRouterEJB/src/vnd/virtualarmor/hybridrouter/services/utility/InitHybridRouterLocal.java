package vnd.virtualarmor.hybridrouter.services.utility;

import javax.ejb.Stateless;

import net.juniper.jmp.cmp.serviceApiCommon.InternalApiContext;
import net.juniper.jmp.cmp.system.AppEnabledCallback;
import net.juniper.jmp.cmp.system.AppInitBasicContext;
import net.juniper.jmp.cmp.system.appinit.AppInitContext;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import vnd.virtualarmor.hybridrouter.Constants;
import vnd.virtualarmor.hybridrouter.utility.ClientUtils;

@Stateless(name = "HybridRouter.AppEnabledCallbackEJB")
public class InitHybridRouterLocal implements AppEnabledCallback {
	Logger logger = Logger.getLogger(InitHybridRouterLocal.class);

	/**
	 * Override execute() method of AppEnabledCallback. Execute method will run,
	 * every time the App is deployed, or jboss is restarted.
	 */
	@Override
	public void execute(AppInitBasicContext ctx) throws Exception {

		logger.warn("In InitHybridRouterLocal.execute before isMasterMode");
		// we only need to run this code once, independent of how many cluster
		// nodes there are
		if (ctx.isMasterNode()) {
			logger.warn("In InitHybridRouterLocal.execute after isMasterMode");
			// Get the context state
			String attr = null;
			try {
				AppInitContext ctx2 = (AppInitContext) ctx;
				attr = (String) ctx2
						.getAttribute(AppInitContext.APPSTARTSTATE_ATTR);
			} catch (Exception ex) {
				logger.error("Failed to get context state: " + ex.getMessage());
				return;
			}
			logger.warn("In InitHybridRouterLocal.execute after isMasterMode2");
			// Run initialization on fresh install of the application
			if (AppInitContext.APPSTARTSTATE_FRESHINSTALL.equals(attr)) {
				InternalApiContext apic = new InternalApiContext();
				Client client = ClientUtils.setupHttpClient(apic);
				ClientResponse response = null;
				logger.warn("In InitHybridRouterLocal.execute after isMasterMode3");
				// See if the queue exists
				response = client.resource(
						apic.getBaseUrl() + Constants.QUEUE_PATH).head();
				if (response.getClientResponseStatus() != ClientResponse.Status.OK) {
					// Queue does not exist, lets create one
					logger.warn("In InitHybridRouterLocal.execute after isMasterMode4");
					response = client
							.resource(
									apic.getBaseUrl() + "/api/hornet-q/queues")
							.type("application/hornetq.jms.queue+xml")
							.post(ClientResponse.class,
									"<queue name='"
											+ Constants.QUEUE_NAME
											+ "'><durable>true</durable></queue>");

					if (response.getClientResponseStatus() != ClientResponse.Status.CREATED) {
						logger.warn("In InitHybridRouterLocal.execute after isMasterMode5");
						logger.error("Failed to create '"
								+ Constants.QUEUE_NAME
								+ "'.  Reason is: "
								+ response.getClientResponseStatus()
										.getReasonPhrase());
					} else {
						logger.warn("In InitHybridRouterLocal.execute after isMasterMode6");
						logger.info("Successfully created resource '"
								+ Constants.QUEUE_NAME + "'");
					}
				}
			}
		}

	}
}
