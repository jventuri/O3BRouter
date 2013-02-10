package vnd.virtualarmor.hybridrouter.mdb;

import java.util.Map;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import net.juniper.jmp.cmp.deviceChange.ApplicationDeviceStatus;
import net.juniper.jmp.cmp.deviceChange.DeviceChangeDiffResult;

// @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "interested_app_names LIKE '%HybridRouter%'")

@MessageDriven(name = "ConfigurationChangeMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/device-change"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "NonDurable")
		 })
public class ConfigurationChangeMDB extends
		net.juniper.jmp.cmp.deviceChange.DeviceChangeBaseMDB {

	public boolean handleDeviceChange(DeviceChangeDiffResult result) {
		
		Integer deviceId = result.getDeviceId();
		Map<String, String> diffResults = result.getDiffResults();
	
		//update audit log here!
		
		return false;
	}

	public void setDeviceStatus(Integer deviceId, ApplicationDeviceStatus status) {
		/*
		 * set the status of (application specific) device as 'out of sync' or
		 * 'in sync'
		 */
		int i=1;
	}

	public String getApplicationName() {
		/*
		 * Application name should match the name specified in app interest
		 * registration file under <app-name> node.
		 */
		return "HybridRouter";
	}
}
