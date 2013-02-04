package vnd.virtualarmor.hybridrouter.rest.v1;

import org.apache.log4j.Logger;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import net.juniper.jmp.cmp.system.JxServiceLocator;
import net.juniper.jmp.websvc.common.WebSvcAbstract;
import javax.ws.rs.WebApplicationException;

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
			wrapperDTO.setField(getBean().getOidValue(deviceAddress, port,
					community, oid));
		} catch (WebApplicationException e) {
			logger.error("WebApplicationException getting oid value: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Exception getting oid value: " + e.getMessage());
			throw new WebApplicationException(e);
		} finally {
			//TODO: Auto generated code
		}

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
}
