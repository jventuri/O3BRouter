/*******************************************************************************  
 * FILE NAME: ManageSnmpRemote.java  
 * PURPOSE:   This interface exposes ManageSnmpRemote application's services  
 *   
 * Revision History:  
 * DATE:                             AUTHOR:             CHANGE:    
 * Sat Aug 11 14:35:36 MDT 2012   Auto Generated      Initial creation  
 *******************************************************************************/ 
package vnd.virtualarmor.hybridrouter.services.netdevice; 
 
import javax.ejb.Remote;

/**
 * Created by Junos Space SDK.
 * User: ${USERNAME}
 * Date: Sat Aug 11 14:35:36 MDT 2012
 */

@Remote
public interface ManageSnmpRemote { 
 	
	public String getOidValue(String deviceAddress, int port, String community, String oid);

}  