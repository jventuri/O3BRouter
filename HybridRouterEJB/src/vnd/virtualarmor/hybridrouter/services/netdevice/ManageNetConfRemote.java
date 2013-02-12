package vnd.virtualarmor.hybridrouter.services.netdevice;

import javax.ejb.Remote;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import net.juniper.jmp.ApiContext;

@Remote
public interface ManageNetConfRemote
{

	public void execScript(ApiContext ctx, Long deviceId, Long scriptId);
	
	public class Naming
	{
		static Logger logger = Logger.getLogger(Naming.class);

		public static ManageNetConfRemote getInstance()
		{
			try
			{
				logger.debug("Looking up ManageNetConfRemote");
				return (ManageNetConfRemote) new InitialContext()
						.lookup(getJndiName());
			} catch (NamingException ne)
			{
				logger.error("Error looking up ManageNetConfRemote: "
						+ ne.getMessage());
				ne.printStackTrace();
				return null;
			}
		}

		private static String getJndiName()
		{
			return "HybridRouter/ManageNetConfBean/remote";
		}
	}
}
