package vnd.virtualarmor.hybridrouter.model;

import java.io.Serializable;

/**
 * Info class for Timer
 *
 */
public class PollingTimerInfo implements Serializable {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 5099362122796528817L;
	
	private String name;
	private int failedCount;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFailedCount() {
		return failedCount;
	}
	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

}
