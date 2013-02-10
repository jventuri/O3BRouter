package vnd.virtualarmor.hybridrouter.services.netdevice;

import java.io.StringReader;
import java.io.StringWriter;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.juniper.jmp.ApiContext;
import net.juniper.jmp.cmp.serviceApiCommon.InternalApiContext;
import net.juniper.jmp.security.JSServiceClient;
import net.juniper.jmp.websvc.helper.JSAuditlogHelper;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import vnd.virtualarmor.hybridrouter.Constants;
import vnd.virtualarmor.hybridrouter.VendorConstants;
import vnd.virtualarmor.hybridrouter.model.Device;
import vnd.virtualarmor.hybridrouter.model.ExecScripts;
import vnd.virtualarmor.hybridrouter.model.Script;
import vnd.virtualarmor.hybridrouter.model.ScriptMgmt;
import vnd.virtualarmor.hybridrouter.model.ScriptParam;
import vnd.virtualarmor.hybridrouter.model.ScriptParams;
import vnd.virtualarmor.hybridrouter.model.TaskResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

/**
 * Session Bean implementation class ManageNetConfBean
 */

@Stateless(name = "ManageNetConfBean")
@Local(ManageNetConfLocal.class)
@Remote(ManageNetConfRemote.class)
public class ManageNetConfBean implements ManageNetConfRemote, ManageNetConfLocal {

	/**
	 * Instance Logger to log messages
	 **/
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ManageNetConfBean.class);

	/**
	 * Default constructor.
	 */
	public ManageNetConfBean() {
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
		Logger.getLogger("com.sun.jersey").setLevel(Level.DEBUG);

		InternalApiContext iac = (InternalApiContext) apic;

		// Create an instance of JSServiceClient using ApiContext
		JSServiceClient client = new JSServiceClient(apic);
		Client jerseyClient = client.jerseyHttpClient();

		// build the URL
		String execScriptUrl = iac.getBaseUrl()
				+ VendorConstants.SPACE_URL_PREFIX
				+ "/script-management/scripts/exec-scripts?queue="
				+ iac.getBaseUrl() + "/api/hornet-q/queues/"
				+ Constants.QUEUE_NAME;

		ExecScripts execScripts = createRequestDTO(deviceId, scriptId);
		
		// call exec-scripts REST API
		ClientResponse response = jerseyClient
				.resource(execScriptUrl)
				.accept(VendorConstants.SPACE_DATATYPE_PREFIX
						+ ".script-management.exec-scripts+xml;version=2")
				.type(VendorConstants.SPACE_DATATYPE_PREFIX
						+ ".script-management.exec-scripts+xml;version=2;charset=UTF-8")
				.post(ClientResponse.class, execScripts);

		String entity = response.getEntity(String.class);

		if (response.getClientResponseStatus() != ClientResponse.Status.ACCEPTED) {
			logger.error("execScript REST failed with status: "
					+ response.getClientResponseStatus() + " and reason: "
					+ response.getClientResponseStatus().getReasonPhrase()
					+ " and error message: " + entity);
		} else {
			TaskResponse tr = createResponseObject(entity);
			JSAuditlogHelper.addDescriptionToAuditLog("HybridRouter - Submitted configuration update for device '" + deviceId + "'. Job ID is" + tr.getId());
		}
		return;

	}

	// Builds a request body for exec-scripts
	private String getRequestBody(Long deviceId, Long scriptId) {

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

	// Creates the Request DTO
	private ExecScripts createRequestDTO(Long deviceId, Long scriptId) {
		ScriptMgmt sm = new ScriptMgmt();

		// Device
		Device device = new Device();
		device.setHref("/api/space/device-management/devices/"
				+ deviceId.toString());
		sm.setDevice(device);

		// Script
		Script script = new Script();
		script.setHref("/api/space/script-management/scripts/"
				+ scriptId.toString());
		sm.setScript(script);

		ScriptParams scriptParams = new ScriptParams();

		ScriptParam sp1 = new ScriptParam();
		sp1.setParamName("policy-name");
		sp1.setParamValue("testMarksScript");
		scriptParams.getScriptParam().add(sp1);

		ScriptParam sp2 = new ScriptParam();
		sp2.setParamName("silent");
		sp2.setParamValue("0");
		scriptParams.getScriptParam().add(sp2);

		sm.setScriptParams(scriptParams);

		ExecScripts es = new ExecScripts();
		es.getScriptMgmt().add(sm);

		return es;
	}

	// Creates an object from the exec-scripts xml response
	private TaskResponse createResponseObject(String xml) {
		TaskResponse taskResponse = new TaskResponse();

		try {
			final JAXBContext context = JAXBContext
					.newInstance(TaskResponse.class);

			final StringWriter stringWriter = new StringWriter();
			stringWriter.append(xml);

			final Unmarshaller unmarshaller = context.createUnmarshaller();

			// Unmarshal the XML in the stringWriter back into an object
			taskResponse = (TaskResponse) unmarshaller
					.unmarshal(new StringReader(stringWriter.toString()));
		} catch (JAXBException e) {
			logger.error("Exception converting XML " + xml
					+ " to a Response object.  Exception is: " + e.getMessage());
		}

		return taskResponse;
	}

}
