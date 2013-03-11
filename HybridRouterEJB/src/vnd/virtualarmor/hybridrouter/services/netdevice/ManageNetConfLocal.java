package vnd.virtualarmor.hybridrouter.services.netdevice;

import net.juniper.jmp.ApiContext;
import javax.ejb.Local;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import vnd.virtualarmor.hybridrouter.model.ExecScripts;
import vnd.virtualarmor.hybridrouter.model.TaskResponse;

@Local
public interface ManageNetConfLocal extends ManageNetConf
{

	public class Naming
	{
		static Logger logger = Logger.getLogger(Naming.class);

		public static ManageNetConfLocal getInstance()
		{
			try
			{
				logger.debug("Looking up ManageNetConfLocal");
				return (ManageNetConfLocal) new InitialContext()
						.lookup(getJndiName());
			} catch (NamingException ne)
			{
				logger.error("Error looking up ManageNetConfLocal: "
						+ ne.getMessage());
				ne.printStackTrace();
				return null;
			}
		}

		private static String getJndiName()
		{
			return "HybridRouter/ManageNetConfBean/local";
		}
	}
}
