package vnd.virtualarmor.hybridrouter.utility;

import java.util.Properties;

import org.apache.log4j.Logger;

import vnd.virtualarmor.hybridrouter.Constants;
import net.juniper.jmp.ApiContext;
import net.juniper.jmp.ApiContextInterface;
import net.juniper.jmp.webSvc.security.AuthorizationContext;
import net.juniper.jmp.cmp.serviceApiCommon.InternalApiContext;
import net.juniper.jmp.security.JSServiceClient;

import com.sun.jersey.api.client.Client;

public class ClientUtils {

	/**
	 * Create a Jersey HTTP client.
	 * 
	 * @param apic
	 *            - ApiContext which provides base URL and credentials
	 * @return
	 */
	public static Client setupHttpClient(ApiContext apic) {

		InternalApiContext iac = (InternalApiContext) apic;

		// credentials for HTTP Request
		AuthorizationContext authCtxt = new AuthorizationContext();

		// set username to REST user
		authCtxt.setUsername(Constants.USER_REST);

		if (iac.getBaseUrl() == null) {
			iac.setAuthDetailsAndBaseUrl(authCtxt, Constants.BASE_URL);
		} else {
			iac.setAuthDetailsAndBaseUrl(authCtxt, iac.getBaseUrl());
		}

		// Create a client for communication with HTTP-based REST Web services
		JSServiceClient client = new JSServiceClient(apic);

		return client.jerseyHttpClient();
	}
}
