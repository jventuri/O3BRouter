package vnd.virtualarmor.hybridrouter.services.netdevice;

import javax.ejb.Remote;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


@Remote
public interface SnmpPollingBeanRemote
{
	
	public void init();
	
	public class Naming
	{
		static Logger logger = Logger.getLogger(Naming.class);

		public static SnmpPollingBeanRemote getInstance()
		{
			try
			{
				logger.debug("Looking up SnmpPollingBeanRemote");
				return (SnmpPollingBeanRemote) new InitialContext()
						.lookup(getJndiName());
			} catch (NamingException ne)
			{
				logger.error("Error looking up SnmpPollingBeanRemote: " + ne.getMessage());
				ne.printStackTrace();
				return null;
			}
		}

		private static String getJndiName()
		{
			String name = "HybridRouter/SnmpPollingBean/remote";
		//			+ SnmpPollingBean.class.getAnnotation(Stateless.class)
		//					.mappedName();
			
		//	String name = "HybridRouter/"
		//			+ SnmpPollingBean.class.getAnnotation(Stateless.class)
		//					.mappedName();
			return name;
		}
	}
}
