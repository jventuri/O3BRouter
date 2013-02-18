package vnd.virtualarmor.hybridrouter.services.netdevice.rest.v1;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.juniper.jmp.ApiContext;
import net.juniper.jmp.interceptors.hateoas.HATEOASMethod;
import net.juniper.jmp.interceptors.hateoas.HATEOASMethodObject;
import net.juniper.jmp.parsers.common.UriContext;
import net.juniper.jmp.websvc.common.WebSvcAbstract;
import net.juniper.jmp.websvc.helper.JSAuditlogHelper;

import org.apache.log4j.Logger;

import vnd.virtualarmor.hybridrouter.Constants;
import vnd.virtualarmor.hybridrouter.model.ExecScripts;
import vnd.virtualarmor.hybridrouter.model.TaskResponse;
import vnd.virtualarmor.hybridrouter.services.netdevice.ManageNetConfRemote;

/**
 * REST service to manager NETCONF devices
 * 
 * @author JayVenturini
 * 
 */
@XmlRootElement(name = "hybrid-router")
@XmlAccessorType(XmlAccessType.NONE)
public class ManageNetConfRemoteRestImpl extends WebSvcAbstract implements
		ManageNetConfRemoteRest
{

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
	public ManageNetConfRemoteRestImpl()
	{
		managenetconfremote = getBean();
	}

	/**
	 * <b>Description:</b> This is a REST method for executing device scripts by
	 * delegating its implementation to the
	 * <code>vnd.virtualarmor.hybridrouter.services.netdevice.ManageNetConfRemote</code>
	 * EJB bean.
	 * 
	 * @param ExecScripts
	 *            POJO representing request parameters
	 * 
	 * @return TaskResponse response containing task metadata
	 */
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,
			MediaType.TEXT_PLAIN })
	public TaskResponse execScript(ExecScripts execScripts,
			UriContext uriContext)
	{
		TaskResponse tr = new TaskResponse();

		try
		{

			if (execScripts.getScriptMgmt().size() > 0)
			{
				// build device href element if only the device guid was passed
				// in
				String deviceHref = execScripts.getScriptMgmt().get(0)
						.getDevice().getHref();
				if (deviceHref.indexOf(Constants.DEVICE_HREF) == -1)
				{
					StringBuffer deviceBuf = new StringBuffer(
							Constants.DEVICE_HREF).append(deviceHref);
					execScripts.getScriptMgmt().get(0).getDevice()
							.setHref(deviceBuf.toString());
				}

				// build script href element if only the script guid was passed
				// in
				String scriptHref = execScripts.getScriptMgmt().get(0)
						.getScript().getHref();
				if (scriptHref.indexOf(Constants.SCRIPT_HREF) == -1)
				{
					StringBuffer scriptBuf = new StringBuffer(
							Constants.SCRIPT_HREF).append(scriptHref);
					execScripts.getScriptMgmt().get(0).getScript()
							.setHref(scriptBuf.toString());
				}

				tr = getBean().execScript(uriContext.getApicontext(),
						execScripts);
				JSAuditlogHelper
						.addDescriptionToAuditLog("Submitting configuration update for device '"
								+ deviceHref + "'");
			}
		} catch (Exception e)
		{
			JSAuditlogHelper.addDescriptionToAuditLog("Exception  occurred "
					+ e.getMessage());
			throw new WebApplicationException(e);
		} finally
		{
		}

		return tr;
	}

	/**
	 * Return the new instance
	 * 
	 * @return ManageNetConfRemoteRest
	 */
	public ManageNetConfRemoteRest getRoot()
	{
		return new ManageNetConfRemoteRestImpl();
	}

	/**
	 * Create the bean instance
	 * 
	 * @return 
	 *         vnd.virtualarmor.hybridrouter.services.netdevice.ManageNetConfRemote
	 */
	private vnd.virtualarmor.hybridrouter.services.netdevice.ManageNetConfRemote getBean()
	{
		if (managenetconfremote == null)
		{
			managenetconfremote = ManageNetConfRemote.Naming.getInstance();
		}
		return managenetconfremote;
	}
}
