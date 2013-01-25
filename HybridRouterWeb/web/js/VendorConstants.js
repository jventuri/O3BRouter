/**
 * Description - This java script file contains the Constants.
 * This is read only file and user should not modify it.
 */
 
/**
* This is the vendor ID field used in REST code for generating media type string
* in Produces and Consumes, This field's value should be in sync with your Vendor ID.
* This constant should be used to get vendor id in case of SDK installation or SDK repair.
* To change its value SDK should be re-installed with new vendor id.
*/
	var VENDOR_ID = "virtualarmor";
	/**
	  * This is the URL prefix for custom application web-service.
	 **/
	var API_URL_PREFIX = "/api";
	/**
	  * This is the media type prefix for custom application web-service.
	 **/
	var APPLICATION_VND = "application/vnd";
	/**
	  * This is the URL prefix for Junos Space web-services.
	 **/
	var SPACE_URL_PREFIX= "/api/space";
	/**
	  * This is the media type prefix for Junos Space web-services.
	 **/
	var SPACE_DATATYPE_PREFIX= "application/vnd.net.juniper.space";
	/**
	 * This is the custom path prefix for application.
	 **/
	var APP_PREFIX = "hybrid-router";
	/**
	 * This is the Context Path for web application.
	 * This constant can be used to access the web project's resources. 
	 * For example:
	 * window.open("_blank", WEB_CONTEXT_PATH + "/js/MyPopup.html")
	 *
	 **/
	var WEB_CONTEXT_PATH = "/ui/" + VENDOR_ID + "/" + APP_PREFIX;
     /**
	  *  This is the URL prefix for application custom web services.
	  *  This constant can be used to construct the api URL while making a REST call
	  *  from the JS code
	  *  For example
	  * 	var proxy = new Jx.JxHttpProxyRest({
	  *			url: APP_URL_PREFIX+'/<Service Path of the REST Resource>/<Method Path of the REST API>',
	  *			...
	  **/
	var APP_URL_PREFIX = API_URL_PREFIX + "/" + VENDOR_ID + "/" + APP_PREFIX;
	/**
	  * This is the media type prefix for Junos Space web-services.
	  * This constant contains media type prefix for custom application web-service, vendor ID and the custom path prefix for application.
	  * This is used to construct Content-Type and Accept Header of REST API.
	  * 
	  * For Example - 
	  * create: {headers: { 
	  *		'Content-Type': APP_DATATYPE_PREFIX + '.<service-path>.<dto-name>+<json/xml>;version=1;charset=UTF-8',
	  *		'Accept': APP_DATATYPE_PREFIX + '.<service-path>.<dto-name>+<json/xml>;version=1'
	  *	}}
	 **/
	var APP_DATATYPE_PREFIX = APPLICATION_VND + "." + VENDOR_ID + "." + APP_PREFIX;