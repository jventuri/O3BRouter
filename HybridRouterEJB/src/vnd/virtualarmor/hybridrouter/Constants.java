package vnd.virtualarmor.hybridrouter;

/**
 * Contains application wide constants
 */
public class Constants {

	public static String PROPERTIES_FILE = "HybridRouter.properties";
//public static String REST_USER = "super";
//	public static String REST_PASSWORD="v1rtualarmor";
//	public static String BASE_URL = "http://127.0.0.1:8080";
	
	//Property keys
	public static String REST_USER_PROP = "hr.user.rest.name";
	public static String REST_PASSWORD_PROP = "hr.user.rest.password";
	public static String BASE_URL_PROP = "hr.application.baseurl";
	
	public static String QUEUE_NAME = "hybrid-router-queue";
	public static String QUEUE_PATH = "/api/hornet-q/queues/jms.queue." + QUEUE_NAME;

	//Per JS docs, user $$$rest doesn't require any password, but only works when calling the rest service from
	//a native app running within the Space Fabric.
	public static String USER_REST = "$$$rest";

	
	
}
