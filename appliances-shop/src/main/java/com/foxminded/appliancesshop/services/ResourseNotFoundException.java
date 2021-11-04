package com.foxminded.appliancesshop.services;

public class ResourseNotFoundException extends RuntimeException {

	public ResourseNotFoundException() {
		super();
	}

	public ResourseNotFoundException(String message) {
		super(message);
	}

	public ResourseNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourseNotFoundException(Throwable cause) {
		super(cause);
	}

	public ResourseNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
