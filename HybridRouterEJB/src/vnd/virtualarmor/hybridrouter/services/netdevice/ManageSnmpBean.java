/*******************************************************************************  
 * FILE NAME: ManageSnmpImpl.java  
 * PURPOSE:   This class implements  ManageSnmpRemote interface   
 *   
 * Revision History:  
 * DATE:                             AUTHOR:             CHANGE:    
 * Sat Aug 11 14:35:36 MDT 2012   Auto Generated      Initial creation  
 *******************************************************************************/  
package vnd.virtualarmor.hybridrouter.services.netdevice; 
import javax.ejb.Stateless;
import javax.ejb.Remote; 
import org.apache.log4j.Logger;

import com.va.snmpManager.SnmpManager;

/**
 * Created by Junos Space SDK
 * User: ${USERNAME}
 * Date: Sat Aug 11 14:35:36 MDT 2012
 */ 
@Stateless(name = "ManageSnmpRemote")
@Remote({ ManageSnmpRemote.class })
public class ManageSnmpBean implements ManageSnmpRemote { 
	/**
	 * Instance Logger to log messages
	 **/
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ManageSnmpBean.class);

	/**
	 * Gets the value associated with a given OID.
	 * @param deviceAddress - address of the SNMP device
	 * @param port - port the SNMP device is listening on.
	 * @param community - community access
	 * @param oid - OID we are interested in getting the value of.
	 */
	public String getOidValue(String deviceAddress, int port, String community, String oid)
	{
		String value;

		// address of the form "udp:128.254.100.2/161"
		StringBuffer addressBuffer = new StringBuffer("udp:")
				.append(deviceAddress).append("/").append(port);


		try
		{
			SnmpManager manager = new SnmpManager(addressBuffer.toString());

			// Synchronous GET
			value = manager.getAsString(oid, community);

			manager.stop();
		} catch (Exception e)
		{
			value = "Error retrieving OID value for SNMP Device: '"
					+ addressBuffer.toString() + "' and OID: '" + oid + "'.  Exception is: " + e.getMessage();
			
			logger.error(value, e);
		}

		return value;
	}
	
	
} 