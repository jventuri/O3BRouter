package vnd.virtualarmor.hybridrouter.services.netdevice;

import javax.ejb.Local;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


@Local
public interface SnmpPollingBeanLocal
{
	
	public void init();
	
	public class Naming
	{
		static Logger logger = Logger.getLogger(Naming.class);

		public static SnmpPollingBeanLocal getInstance()
		{
			try
			{
				logger.debug("Looking up SnmpPollingBeanLocal");
				return (SnmpPollingBeanLocal) new InitialContext()
						.lookup(getJndiName());
			} catch (NamingException ne)
			{
				logger.error("Error looking up SnmpPollingBeanLocal: " + ne.getMessage());
				ne.printStackTrace();
				return null;
			}
		}

		private static String getJndiName()
		{
			return "HybridRouter/SnmpPollingBean/local";
		}
	}
}
