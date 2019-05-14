package com.dtsz.action.res;


public class ActionResult {
	private Object object;
	private String message;
	private boolean success;
	private String logMsg;

	public ActionResult() {

	}

	public ActionResult(Object object, String message, boolean success) {
		this.object = object;
		this.message = message;
		this.success = success;
	}

	public ActionResult(Object object, String message, boolean success,
			String logMsg) {
		this.object = object;
		this.message = message;
		this.success = success;
		this.logMsg = logMsg;
	}

	public ActionResult(Object object, boolean success) {
		this.object = object;
		this.success = success;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}

}
