package com.cgi.udev.resoapi.model.exceptions;

public class UserNotFoundException extends Exception{

	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
