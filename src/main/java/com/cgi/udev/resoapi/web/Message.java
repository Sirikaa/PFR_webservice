package com.cgi.udev.resoapi.web;

/**
* Exemple de bean Java qui sera transformé en document JSON par
* la ressource HelloResource.
*/
public class Message {

	private String message;

	public Message() {
	}

	public Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
