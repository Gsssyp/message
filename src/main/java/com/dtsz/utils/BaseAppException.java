package com.dtsz.utils;

public class BaseAppException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String messageText;
	private Throwable throwable;

	public BaseAppException(String messageText) {
		this.messageText = messageText;
	}

	public BaseAppException(Throwable throwable) {
		this.throwable = throwable;
	}

	public BaseAppException(String messageText, Throwable throwable) {
		this.messageText = messageText;
		this.throwable = throwable;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

}
