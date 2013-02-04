package vnd.virtualarmor.hybridrouter.services.netdevice.rest.v1;

import org.apache.log4j.Logger;
import javax.ws.rs.WebApplicationException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import net.juniper.jmp.cmp.system.JxServiceLocator;
import net.juniper.jmp.parsers.common.UriContext;
import net.juniper.jmp.websvc.helper.JSAuditlogHelper;
import net.juniper.jmp.websvc.common.WebSvcAbstract;
import net.juniper.jmp.interceptors.hateoas.HATEOASMethodObject;
import javax.xml.bind.annotation.XmlElement;
import net.juniper.jmp.interceptors.hateoas.HATEOASMethod;

/*******************************************************************************
 * FILE NAME: ManageNetConfRemoteRestImpl.java
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
public class ManageNetConfRemoteRestImpl extends WebSvcAbstract implements
		ManageNetConfRemoteRest {

	/**
	 * logger instance to log messages
	 **/
	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(ManageNetConfRemoteRestImpl.class);
	vnd.virtualarmor.hybridrouter.services.netdevice.ManageNetConfRemote managenetconfremote = null;
	@SuppressWarnings("unused")
	@HATEOASMethod(href = "/hybridrouter/exec-script", description = "/hybridrouter/exec-script")
	@XmlElement(name = "method")
	private HATEOASMethodObject postexecScripthybridrouterexecscript;

	/**
	 * default constructor
	 */
	public ManageNetConfRemoteRestImpl() {
		managenetconfremote = getBean();
	}

	/**
	 * <pre>
	 * <b>Method:</b>execScript
	 *
	 * <b>Description:</b> This is an  auto generated method with stub
	 * implementation which uses the <code>vnd.virtualarmor.hybridrouter.services.netdevice.ManageNetConfRemote</code> EJB bean and exposes
	 * it's method with Rest web services interface.
	 * @param deviceId Long representing the device GUID
	 * @param scriptId Long representing the script GUID
	 * @param uriContext UriContext
	 * @return void
	 */
	public void execScript(Long deviceId, Long scriptId, UriContext uriContext) {

		try {
			getBean().execScript(uriContext.getApicontext(), deviceId, scriptId);
		} catch (Exception e) {
			JSAuditlogHelper.addDescriptionToAuditLog("Exception  occurred "
					+ e.getMessage());
			throw new WebApplicationException(e);
		} finally {
			//TODO: Auto generated code
		}

		return;
	}

	/**
	 * Return the new instance
	 * @return ManageNetConfRemoteRest
	 */
	public ManageNetConfRemoteRest getRoot() {
		return new ManageNetConfRemoteRestImpl();
	}

	/**
	 * Create the bean instance
	 * @return vnd.virtualarmor.hybridrouter.services.netdevice.ManageNetConfRemote
	 */
	private vnd.virtualarmor.hybridrouter.services.netdevice.ManageNetConfRemote getBean() {
		if (managenetconfremote == null) {
			managenetconfremote = JxServiceLocator
					.lookup("ManageNetConfRemote");
		}
		return managenetconfremote;
	}
}
