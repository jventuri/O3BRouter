package vnd.virtualarmor.hybridrouter.services.netdevice;

import java.util.List;

import javax.ejb.Remote;

import net.juniper.jmp.ApiContext;
import net.juniper.jmp.ApiContextInterface;

@Remote
public interface ManageNetConfRemote {
	
	public void execScript(ApiContext ctx, Long deviceId, Long scriptId);
}
