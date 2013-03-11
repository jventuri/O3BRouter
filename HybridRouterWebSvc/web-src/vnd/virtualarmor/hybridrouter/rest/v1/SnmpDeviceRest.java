package vnd.virtualarmor.hybridrouter.rest.v1;

import javax.ws.rs.Path;
import javax.ws.rs.GET;

import vnd.virtualarmor.hybridrouter.VendorConstants;

import javax.ws.rs.Produces;
import net.juniper.jmp.annotation.rbac.CRUDEnum;
import net.juniper.jmp.annotation.rbac.RBAC;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import net.juniper.jmp.cmp.info.Required;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.HEAD;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import net.juniper.jmp.parsers.common.UriContext;
import net.juniper.jmp.parsers.annotations.ParseContext;
import net.juniper.jmp.cmp.async.Task;
import net.juniper.jmp.interceptors.hateoas.HATEOAS;

/**
 * Manages retrieval of OID values from an SNMP device.
 * 
 * @author JayVenturini
 *
 */
@Path("/hybridrouter")
public interface SnmpDeviceRest {

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
	public SnmpDeviceRest getRoot();

	@Path("oid-values/{deviceAddress}/{port}/{community}")
	@GET
	@Produces({
			VendorConstants.APP_DATATYPE_PREFIX
					+ ".hybridrouter.oid-value+xml;version=1;q=0.01",
			VendorConstants.APP_DATATYPE_PREFIX
					+ ".hybridrouter.oid-value+json;version=1;q=0.01" })
	@RBAC(type = { CRUDEnum.READ }, capability = { "HybridRouterCap" })
	public OidValue getOidValue(
			@PathParam("deviceAddress") String deviceAddress,
			@PathParam("port") int port,
			@PathParam("community") String community,
			@Required @QueryParam("oid") String oid);
}
