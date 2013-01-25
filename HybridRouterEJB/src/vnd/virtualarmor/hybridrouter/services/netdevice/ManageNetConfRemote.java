package vnd.virtualarmor.hybridrouter.services.netdevice;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ManageNetConfRemote {
	
	public void execScript(Long deviceId, Long scriptId);
}
