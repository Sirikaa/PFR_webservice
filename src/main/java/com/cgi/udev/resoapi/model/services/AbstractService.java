package com.cgi.udev.resoapi.model.services;

public abstract class AbstractService {
	
	/*
	 * Méthode qui vérifie qu'un document JSON a bien été envoyé.
	 */
	protected boolean isExisting(Object o) {
		boolean isExisting = false;
		if(o != null) {
			isExisting = true;
		}else {
			isExisting = false;
		}
		return isExisting;
	}

}
