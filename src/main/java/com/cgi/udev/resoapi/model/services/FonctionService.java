package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.FonctionDao;
import com.cgi.udev.resoapi.model.Fonction;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;

public class FonctionService {
	
private FonctionDao dao = new FonctionDao();
	
	/*
	 * Retourne la liste des clients de la table Client
	 * throw InexistantException si la table est vide
	 */
	public List<Fonction> getAll() throws InexistantException{
		List<Fonction> liste = dao.getAll();
		if(!liste.isEmpty()) {
			return liste;
		}else {
			throw new InexistantException("Aucune fonction à afficher !");
		}
		//		return !liste.isEmpty() ? liste : throw new InexistantException("Aucune fonction à afficher !");
	}
	
	public Fonction getFonction(int id) throws InexistantException{
		Fonction c = dao.getFonction(id);
		if(c.getId() != 0) {
			return c;
		}else {
			throw new InexistantException("Cette fonction n'existe pas !");
		}
	}

}
