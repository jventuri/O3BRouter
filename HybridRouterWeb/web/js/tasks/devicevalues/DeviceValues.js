/**
 * Description - This file implements a task that creates the screen that demonstrates
 * the DeviceValues operations.
 * Class - DeviceValues
 */
Ext.ns("Js.devicevalues");


/**
 * Description - Constructor function for the DeviceValues namespace.
 * Constructor function
 */
Js.devicevalues.DeviceValues = function() {
};


/**
 * Description  -Alias for the DeviceValues namespace.
 */
var devicevalues = Js.devicevalues.DeviceValues.prototype;

var gConfig;


/**
 * Description - initialize is one of the life cycle function that is called by the
 * framework when a user clicks on the ribbon node corresponding to this task.
 * In this particular case the initialize function reads the parent container
 * reference and stores it locally.If running in debug mode it then loads
 * all the css and javascript files that it needs for create user task.
 * It uses the 'require' utility function for this purpose.The show function
 * is used as a callback function which gets called once all the javascript
 * files are loaded.
 * Note: When running in non-debug mode it is strongly recommended to not
 * load files individually but rather fo the following:
 * 1. Concatenate all the base file and dependent javascript files into one
 * single javascript file.
 * 2. Obfuscate and then compress the files using tools like YUI compressor.
 * 3. Provide the file name for this single javascript file in module.xml
 * which will automatically be loaded by the framework when user clicks
 * on ribbon node corresponding to this task.
 * Parameters - config
 * Public
 */
devicevalues.initialize = function(config) {    
    this.parentCt = config.parentCt; 
    gConfig = config;
    this.node = config.node; 
    this.taskParameters = config.taskParameters; 
    this.loadDependencies(config);
    
};

/**
 * Description - This is a function that construct Task Window.
 * Public
 */
devicevalues.construct= function() {
    var displayPanel = this.build(gConfig);
    this.devicevaluesTaskWindow = new Jx.JxTaskWindow({
                           title: 'SNMP Device Values',
                           maximized: true,
                           layout: 'fit',
                           items: [displayPanel]
                        });
    this.devicevaluesTaskWindow.render(this.parentCt.getEl());
    this.devicevaluesTaskWindow.show();
};
/**
 * Description - show is a life cycle function that is invoked by framework
 * immediately after initialize function completes executing.
 * In this particular case the show function performs the
 * task of constructing and then showing the task window.
 * Method - show
 * Public
 */
devicevalues.show = function() {
    var node = Jx.SelectionModel.getPreviousSelectedNode();
    if(node == this.node) {       
       // Here, users can call function that show the Window that contains their screen.
    	 this.construct();
    }
};

/**
 * Description - destroy is a life cycle method that is invoked by the framework
 * in two cases:
 * 1. When user explicitly navigates away from this task by clicking
 * on some other task or workspace node.
 * 2. This particular task implicitly makes a switch to another task.
 * Task application developer should use this function to perform
 * any resource cleanup related to this task.
 * In this particular case the destroy function closes the user task window.
 * It then calls the 'removeFiles' utility function to remove the link
 * and script tags corresponding to the css and javascript files loaded
 * for this task.
 * Method - destroy
 * Public
 */
devicevalues.destroy = function() {
		if((this.devicevaluesTaskWindow !== undefined) && (this.devicevaluesTaskWindow !== null))
			this.devicevaluesTaskWindow.close();
		this.removeDepedencies();    
};

/**
 * Description - This is a function that push the dependent javascript files.
 * Parameters - config
 */
devicevalues.loadDependencies = function(config) {
	this.jsFiles = [];

	if(config.debug) {
		this.jsFiles.push(['js', '/ui/virtualarmor/hybrid-router/js/VendorConstants.js']);
		this.jsFiles.push(['js', '/ui/virtualarmor/hybrid-router/js/SnmpStore.js']);
this.jsFiles.push(['js', '/ui/virtualarmor/hybrid-router/js/snmpForm.ui.js']);
this.jsFiles.push(['js', '/ui/virtualarmor/hybrid-router/js/snmpForm.js']);
      
		Jx.JxUtilities.require(this.jsFiles, this.show.createDelegate(this));
	}
};
/**
 * Description - This is a function that remove the dependent javascript files.
 */
devicevalues.removeDepedencies = function() {
	if(this.jsFiles.length > 0) {
		Jx.JxUtilities.removeFiles('js', this.jsFiles);
	}
};

/**
 * This function creates the top panel object and passes the config parameter into 
 * its constructor. The first imported js component is taken as top level component.
 * Method - build
 * Parameters - gconfig
 */
devicevalues.build = function(gConfig) {

	return new snmpForm(gConfig);
	
};
