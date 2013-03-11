package vnd.virtualarmor.hybridrouter.services.netdevice;

import java.util.Map;

import net.juniper.jmp.ApiContext;
import vnd.virtualarmor.hybridrouter.model.ExecScripts;
import vnd.virtualarmor.hybridrouter.model.TaskResponse;

/**
 * Common interface for the ManageNetConfBean
 * 
 * @author JayVenturini
 *
 */
public interface ManageNetConf
{
	public TaskResponse execScript(ApiContext apic, Long primaryDeviceId, Long secondaryDeviceId, Long scriptId, Map<String, String> scriptParameters);
	
	public TaskResponse execScript(ApiContext apic, ExecScripts execScripts);
}
