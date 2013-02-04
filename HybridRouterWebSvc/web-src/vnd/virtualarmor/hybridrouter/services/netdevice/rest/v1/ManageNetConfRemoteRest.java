package vnd.virtualarmor.hybridrouter.services.netdevice.rest.v1;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import net.juniper.jmp.annotation.rbac.CRUDEnum;
import net.juniper.jmp.annotation.rbac.RBAC;
import javax.ws.rs.GET;
import vnd.virtualarmor.hybridrouter.VendorConstants;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.HEAD;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import net.juniper.jmp.parsers.common.UriContext;
import net.juniper.jmp.parsers.annotations.ParseContext;
import javax.ws.rs.QueryParam;
import net.juniper.jmp.cmp.info.Required;
import net.juniper.jmp.cmp.async.Task;
import net.juniper.jmp.interceptors.hateoas.HATEOAS;

/*******************************************************************************
 * FILE NAME: ManageNetConfRemoteRest.java PURPOSE: Created by Junos Space SDK
 * EJB-Rest Wizard EJB NAME:
 * HybridRouterEJB:vnd.virtualarmor.hybridrouter.services
 * .netdevice.ManageNetConfRemote.java
 * 
 * 
 * Revision History: AUTHOR: CHANGE: Auto generated Initial Version
 * 
 * 
 ******************************************************************************/

@Path("/hybridrouter")
public interface ManageNetConfRemoteRest {

	/**
	 * default method
	 */
	@Path("/")
	@GET
	@Produces({
			VendorConstants.APP_DATATYPE_PREFIX + ".hybridrouter+xml;version=1",
			VendorConstants.APP_DATATYPE_PREFIX
					+ ".hybridrouter+json;version=1" })
	@RBAC(type = { CRUDEnum.READ }, capability = { "HybridRouterCap" })
	public ManageNetConfRemoteRest getRoot();

	@Path("exec-script")
	@POST
	@RBAC(type = { CRUDEnum.UPDATE }, capability = { "HybridRouterCap" })
	public void execScript(@Required @QueryParam("deviceId") Long deviceId,
			@Required @QueryParam("scriptId") Long scriptId,
			@Context UriContext uriContext);
}