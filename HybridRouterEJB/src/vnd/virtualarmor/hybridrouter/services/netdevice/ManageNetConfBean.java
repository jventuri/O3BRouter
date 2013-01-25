package vnd.virtualarmor.hybridrouter.services.netdevice;

import java.util.ArrayList;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;

import com.sun.jersey.api.client.ClientResponse;

import vnd.virtualarmor.hybridrouter.Constants;
import vnd.virtualarmor.hybridrouter.VendorConstants;
import vnd.virtualarmor.hybridrouter.utility.ClientUtils;

import net.juniper.jmp.cmp.serviceApiCommon.InternalApiContext;

import net.juniper.jmp.cems.deviceScriptManager.service.dto.v2.ScriptMgmts.Device;
import net.juniper.jmp.cems.deviceScriptManager.service.dto.v2.ScriptMgmts.Script;
import net.juniper.jmp.cems.deviceScriptManager.service.dto.v2.ScriptMgmts.ScriptParams;
import net.juniper.jmp.cems.deviceScriptManager.service.dto.v2.ScriptMgmts.ScriptMgmt;

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
	 * @param deviceId the ID of the device the script will be executed on.
	 * @param scriptId the ID of the script that will be executed.
	 */
	public void execScript(Long deviceId, Long scriptId) {
		InternalApiContext apic = new InternalApiContext();
		Client client = ClientUtils.setupHttpClient(apic);

		// http://127.0.0.1:8080/api/space/script-management/scripts/exec-scripts?queue=http://127.0.0.1:8080/api/hornet-q/queues/jms.queue.test

		// run exec-scripts REST API
		String execScriptUrl = apic.getBaseUrl()
				+ VendorConstants.SPACE_URL_PREFIX
				+ "/script-management/scripts/exec-scripts?queue="
				+ apic.getBaseUrl() + Constants.QUEUE_PATH;

		// accept=application/vnd.net.juniper.space.script-management.exec-scripts+xml;version=2
		// content-type=application/vnd.net.juniper.space.script-management.exec-scripts+xml;version=2;charset=UTF-8

		ScriptMgmt scriptMgmt = genScriptMgmtDTO(deviceId, scriptId);
		
		ClientResponse response = client
				.resource(execScriptUrl)
				.accept(VendorConstants.SPACE_DATATYPE_PREFIX
						+ ".script-management.exec-scripts+xml;version=2")
				.type(VendorConstants.SPACE_DATATYPE_PREFIX
						+ ".script-management.exec-scripts+xml;version=2;charset=UTF-8")
				.post(ClientResponse.class, scriptMgmt);

		if (response.getClientResponseStatus() != ClientResponse.Status.ACCEPTED) {
			logger.error(response.getClientResponseStatus().getReasonPhrase());
			return;
		}
	}
	
	private ScriptMgmt genScriptMgmtDTO(Long deviceId, Long scriptId)
	{
		ScriptMgmt sm = new ScriptMgmt();
		
		Device device = new Device();
		device.setUri("/api/space/device-management/devices/" + deviceId.toString());
		sm.setDevice(device);
		
		Script script = new Script();
		script.setUri("/api/space/script-management/scripts/" + scriptId.toString());
		sm.setScript(script);
		
		//TODO - we need to get the parameters for the script, but from where?  Ask Mark...
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
