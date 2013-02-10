package vnd.virtualarmor.hybridrouter.rest.v1;

import java.util.Random;

import org.apache.log4j.Logger;

import vnd.virtualarmor.hybridrouter.Constants;
import vnd.virtualarmor.hybridrouter.services.netdevice.ManageNetConfLocal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import net.juniper.jmp.cmp.system.JxServiceLocator;
import net.juniper.jmp.websvc.common.WebSvcAbstract;
import javax.ws.rs.WebApplicationException;
import net.juniper.jmp.websvc.helper.JSAuditlogHelper;

/*******************************************************************************
 * FILE NAME: SnmpDeviceRestImpl.java
 * PURPOSE:   Created by Junos Space SDK EJB-Rest Wizard
 *
 *
 * Revision History: 
 * AUTHOR:              CHANGE:  
 * Auto generated       Initial Version  
 * 
 * 
 ******************************************************************************/
@XmlRootElement(name = "hybrid-router")
@XmlAccessorType(XmlAccessType.NONE)
public class SnmpDeviceRestImpl extends WebSvcAbstract implements
		SnmpDeviceRest {

	/**
	 * logger instance to log messages
	 **/
	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(SnmpDeviceRestImpl.class);
	vnd.virtualarmor.hybridrouter.services.netdevice.ManageSnmpRemote hybridrouter = null;

	/**
	 * default constructor
	 */
	public SnmpDeviceRestImpl() {
		hybridrouter = getBean();
	}

	/**
	 * <pre>
	 * <b>Method:</b>getOidValue
	 *
	 * <b>Description:</b> This is an  auto generated method with stub
	 * implementation which uses the <code>vnd.virtualarmor.hybridrouter.services.netdevice.ManageSnmpRemote</code> EJB bean and exposes
	 * it's method with Rest web services interface.
	 * @param deviceAddress String
	 * @param port int
	 * @param community String
	 * @param oid String
	 * @return vnd.virtualarmor.hybridrouter.services.netdevice.rest.v1.HybridRouter_getOidValue
	 */
	public vnd.virtualarmor.hybridrouter.rest.v1.SnmpDevice_getOidValue getOidValue(
			String deviceAddress, int port, String community, String oid) {

		vnd.virtualarmor.hybridrouter.rest.v1.SnmpDevice_getOidValue wrapperDTO = null;
		try {
			wrapperDTO = new vnd.virtualarmor.hybridrouter.rest.v1.SnmpDevice_getOidValue();
			
			/* TODO comment out for demo, as we just return the fabricated value from getOidValue()
			wrapperDTO.setField(getBean().getOidValue(deviceAddress, port,
					community, oid));
					*/
			
		} catch (WebApplicationException we) {
			logger.error("WebApplicationException getting oid value: " + we.getMessage());
			JSAuditlogHelper.addDescriptionToAuditLog("Exception getting oid value: " + we.getMessage());
			throw we;
		} catch (Exception e) {
			logger.error("Exception getting oid value: " + e.getMessage());
			JSAuditlogHelper.addDescriptionToAuditLog("Exception getting oid value: " + e.getMessage());
			throw new WebApplicationException(e);
		} finally {
			
		}

	//TODO - this is the actual return value, uncomment this after the demo
	//	String auditDesc = "SNMP device '" + deviceAddress + "' returned OID value of '" + wrapperDTO.getField() + "'";
	//	JSAuditlogHelper.addDescriptionToAuditLog(auditDesc);
		
		//TODO - Temp code, remove this after demo!
		wrapperDTO.setField(String.valueOf(getOidValue()));
	
		return wrapperDTO;
	}

	/**
	 * Return the new instance
	 * @return SnmpDeviceRest
	 */
	public SnmpDeviceRest getRoot() {
		return new SnmpDeviceRestImpl();
	}

	/**
	 * Create the bean instance
	 * @return vnd.virtualarmor.hybridrouter.services.netdevice.ManageSnmpRemote
	 */
	private vnd.virtualarmor.hybridrouter.services.netdevice.ManageSnmpRemote getBean() {
		if (hybridrouter == null) {
			hybridrouter = JxServiceLocator.lookup("ManageSnmpRemote");
		}
		return hybridrouter;
	}
	
	// TODO - Temporary code to generate an oid value, remove after demo
	private int getOidValue()
	{
		int min = 1;
		int max = 100;

		Random random = new Random();

		// Returns a pseudorandom, uniformly distributed int value between 0
		// (inclusive) and the specified value (exclusive), hence adding 1
		int oidValue = random.nextInt(max - min + 1) + min;
		

		// this code is here so we can write the oidvalue to the audit log (can only do this from a REST service)
		if (oidValue < 70)
		{
			JSAuditlogHelper.addDescriptionToAuditLog("HybridRouter - SNMP (TEST) data rate is '" + oidValue + "', BELOW tolerance (70).");
		}
		// business rule has passed test, so reset the counter
		else
		{
			JSAuditlogHelper.addDescriptionToAuditLog("HybridRouter - SNMP (TEST) data rate is '" + oidValue + "', ABOVE tolerance (70).");
		}

		return oidValue;
	}
}
