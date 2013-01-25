/*******************************************************************************  
 * FILE NAME: VendorConstants.java  
 * PURPOSE:   This is Auto Generated Java Class which contains Constants. 
 *   
 * Revision History:  
 * DATE:                             AUTHOR:             CHANGE:    
 * Fri Jan 25 06:52:33 MST 2013   Auto Generated     Initial creation  
 *******************************************************************************/ 
package vnd.virtualarmor.hybridrouter; 

/**
 * Created by Junos Space SDK.
 * This is read only file and user should not modify it.
 * User: ${USERNAME}
 * Date: Fri Jan 25 06:52:33 MST 2013
 */
public class VendorConstants { 
	/**
	 * This is the vendor ID field used in REST code for generating media type string
	 * in Produces and Consumes, This field's value should be in sync with your Vendor ID.
	 * This constant should be used to get vendor id in case of SDK installation or SDK repair.
	 * To change its value SDK should be re-installed with new vendor id.
	 */
	public static final String VENDOR_ID = "virtualarmor";
	/**
	 * This is the URL prefix for custom application web-service.
	 **/
	public static final String API_URL_PREFIX = "/api";
	/**
	 * This is the media type prefix for custom application web-service.
	 **/
	public static final String APPLICATION_VND = "application/vnd";
	/**
	 * This is the URL prefix for Junos Space web-services.
	 **/
	public static final String SPACE_URL_PREFIX= "/api/space";
	/**
	 * This is the media type prefix for Junos Space web-services.
	 **/
	public static final String SPACE_DATATYPE_PREFIX= "application/vnd.net.juniper.space";
	/**
	 * This is the custom path prefix for application.
	 **/
	public static final String APP_PREFIX = "hybrid-router";
	/**
	  *  This is the URL prefix for application custom web services.
	  *  This constant can be used to construct the api URL while making a REST call
	  *  from the java code
	  *	 For Example:
	  *	 	String request_URL = VendorConstants.APP_URL_PREFIX 
	  *	  			+ "/<Service Path of the REST Resource>/<Method Path of the REST API>";
	  *		ClientRequest request = new ClientRequest(request_URL,
	  *				clientExecutor);
	  **/
	public static final String APP_URL_PREFIX = API_URL_PREFIX + "/" + VENDOR_ID + "/" + APP_PREFIX;
	/**
	 * This is the media type prefix for Junos Space web-services.
	 * This constant contains media type prefix for custom application web-service, vendor ID and the custom path prefix for application
	 * This constant should be used to construct Produces and Consumes for REST API.
	 *
	 * For example: 
	 *   
	 *   
	 * <code>
	 *@Consumes({VendorConstants.APP_DATATYPE_PREFIX + ".<service-path>.<dto-name>+<json/xml>;version=1;charset=UTF-8",
	 *	VendorConstants.APP_DATATYPE_PREFIX + ".<service-path>.<dto-name>+<json/xml>;version=1;charset=UTF-8" })
	 *@Produces({VendorConstants.APP_DATATYPE_PREFIX + ".<service-path>.<dto-name>+<json/xml>;version=1;",
	 *VendorConstants.APP_DATATYPE_PREFIX + ".<service-path>.<dto-name>+<json/xml>;version=1;" })
	 * </code>
	 **/
	public static final String APP_DATATYPE_PREFIX = APPLICATION_VND + "." + VENDOR_ID + "." + APP_PREFIX; 
	
}  