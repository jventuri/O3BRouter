package vnd.virtualarmor.hybridrouter.services.netdevice;

import javax.ejb.Remote;
import net.juniper.jmp.ApiContext;

@Remote
public interface ManageNetConfRemote
{

	public void execScript(ApiContext ctx, Long deviceId, Long scriptId);
}
