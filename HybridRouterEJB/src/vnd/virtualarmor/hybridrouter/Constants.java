package vnd.virtualarmor.hybridrouter;

/**
 * Contains application wide constants
 */
public class Constants {

	public static String BASE_URL = "http://127.0.0.1:8080";

	//Property keys
	public static String REST_USER_PROP = "hr.user.rest.name";
	public static String REST_PASSWORD_PROP = "hr.user.rest.password";
	public static String BASE_URL_PROP = "hr.application.baseurl";
	public static String DEMO_SCRIPT_PROP = "demo.script.id";
	public static String DEMO_DEVICE_PROP = "demo.device.id";
	
	//property file deployed in jboss/server/all/conf/props directory
	public static String PROPERTY_FILE_NAME = "props/hybridRouter.properties";		

	public static String QUEUE_NAME = "jms.queue.HybridRouterJobsQueue";
	public static String HORNETQ_PATH = "/api/hornet-q/queues";

	//Per JS docs, user $$$rest doesn't require any password, but only works when calling the rest service from
	//a native app running within the Space Fabric.
	public static String USER_REST = "$$$rest";

	//Test constants
//	public static Long DYNAMIC_OP_SLAX_ID = 3309568L;
//	public static Long TEST_DEVICE_ID =	3145730L;
}
