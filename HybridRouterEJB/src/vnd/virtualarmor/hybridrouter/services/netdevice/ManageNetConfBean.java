package vnd.virtualarmor.hybridrouter.services.netdevice;

import java.util.ArrayList;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import net.juniper.jmp.ApiContext;
import net.juniper.jmp.cems.deviceScriptManager.service.dto.v2.ScriptMgmts.Device;
import net.juniper.jmp.cems.deviceScriptManager.service.dto.v2.ScriptMgmts.Script;
import net.juniper.jmp.cems.deviceScriptManager.service.dto.v2.ScriptMgmts.ScriptMgmt;
import net.juniper.jmp.cems.deviceScriptManager.service.dto.v2.ScriptMgmts.ScriptParams;
import net.juniper.jmp.cmp.serviceApiCommon.InternalApiContext;
import net.juniper.jmp.security.JSServiceClient;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import vnd.virtualarmor.hybridrouter.Constants;
import vnd.virtualarmor.hybridrouter.VendorConstants;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.LoggingFilter;

/**
 * Session Bean implementation class ManageNetConfBean
 */
@Stateless(name = "ManageNetConfRemote")
@Remote({ ManageNetConfRemote.class })
public class ManageNetConfBean implements ManageNetConfRemote {

	/**
	 * Instance Logger to log messages
	 **/
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ManageNetConfBean.class);

	/**
	 * Default constructor.
	 */
	public ManageNetConfBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Execute a script on a device.
	 * 
	 * @param deviceId
	 *            the ID of the device the script will be executed on.
	 * @param scriptId
	 *            the ID of the script that will be executed.
	 * 
	 * @return response string
	 */
	public void execScript(ApiContext apic, Long deviceId, Long scriptId) {

		// logging jersey
		Logger.getLogger("com.sun.jersey").setLevel(Level.ALL);

		InternalApiContext iac = (InternalApiContext) apic;

		// Create an instance of JSServiceClient using ApiContext
		JSServiceClient client = new JSServiceClient(apic);
		Client jerseyClient = client.jerseyHttpClient();

		// run exec-scripts REST API
		String execScriptUrl = iac.getBaseUrl()
				+ VendorConstants.SPACE_URL_PREFIX
				+ "/script-management/scripts/exec-scripts?queue="
				+ iac.getBaseUrl() + "/api/hornet-q/queues/"
				+ Constants.QUEUE_NAME;

		jerseyClient.addFilter(new LoggingFilter(System.out));

		// ScriptMgmt scriptMgmt = genScriptMgmtDTO(deviceId, scriptId);

		String reqBody = getRequestBody(deviceId, scriptId);

		ClientResponse response = jerseyClient
				.resource(execScriptUrl)
				.accept(VendorConstants.SPACE_DATATYPE_PREFIX
						+ ".script-management.exec-scripts+xml;version=2")
				.type(VendorConstants.SPACE_DATATYPE_PREFIX
						+ ".script-management.exec-scripts+xml;version=2;charset=UTF-8")
				.post(ClientResponse.class, reqBody);

		if (response.getClientResponseStatus() != ClientResponse.Status.ACCEPTED) {
			String errorMsg = response.getEntity(String.class);
			logger.error("execScript REST failed with status: "
					+ response.getClientResponseStatus() + " and reason: "
					+ response.getClientResponseStatus().getReasonPhrase()
					+ " and error message: " + errorMsg);
			return;
		}

	}

	private String getRequestBody(Long deviceId, Long scriptId) {
		// look at Jersey client side API (JAX-RS MessageBodyWriter classes) and
		// alsoo support for JAXB-based generation of XML from domain objects.

		StringBuffer scriptHref = new StringBuffer(
				"<script href=\"/api/space/script-management/scripts/").append(
				scriptId).append("\"/>");
		StringBuffer deviceHref = new StringBuffer(
				"<device href=\"/api/space/device-management/devices/").append(
				deviceId).append("\"/>");

		String scriptParams = "<scriptParams><scriptParam><paramName>policy-name</paramName><paramValue>testREST1</paramValue></scriptParam><scriptParam><paramName>silent</paramName><paramValue>0</paramValue></scriptParam></scriptParams>";

		StringBuffer requestBody = new StringBuffer(
				"<exec-scripts><scriptMgmt>").append(scriptHref)
				.append(deviceHref).append(scriptParams)
				.append("</scriptMgmt></exec-scripts>");

		return requestBody.toString();
	}

	private ScriptMgmt genScriptMgmtDTO(Long deviceId, Long scriptId) {
		ScriptMgmt sm = new ScriptMgmt();

		Device device = new Device();
		device.setUri("/api/space/device-management/devices/"
				+ deviceId.toString());
		sm.setDevice(device);

		Script script = new Script();
		script.setUri("/api/space/script-management/scripts/"
				+ scriptId.toString());
		sm.setScript(script);

		ArrayList<ScriptParams> scriptParams = new ArrayList<ScriptParams>();
		ScriptParams sp1 = new ScriptParams();
		sp1.setParamName("policy-name");
		sp1.setParamValue("testMarksScript");
		scriptParams.add(sp1);

		ScriptParams sp2 = new ScriptParams();
		sp2.setParamName("silent");
		sp2.setParamValue("0");
		scriptParams.add(sp2);

		sm.setScriptParams(scriptParams);

		return sm;
	}

}
