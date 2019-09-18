package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.PersonneDao;
import com.cgi.udev.resoapi.model.Personne;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

public class PersonneService {
	
	private PersonneDao dao = new PersonneDao();
	
	/*
	 * Retourne la liste des personnes de la table Personne
	 * throw InexistantException si la table est vide
	 */
	public List<Personne> getAll() throws InexistantException{
		List<Personne> liste = dao.getAll();
		if(!liste.isEmpty()) {
			return liste;
		}else {
			throw new InexistantException("Aucune personne à afficher !");
		}
	}
	
	/*
	 * Retourne une personne en fonction du paramètre id
	 * throw InexistantException si la personne n'est pas trouvée en base
	 */
	public Personne getPersonneById(int id) throws InexistantException{
	 Personne p = dao.getPersonneById(id);
	 if(p.getId() != 0) {
		 return p;
	 }else {
		 throw new InexistantException("Cette personne n'existe pas !");
	 }
		
	}
	
	public void create(Personne personne) throws RequeteInvalideException, InexistantException{
		if(isPersonneGood(personne)) {
			dao.create(personne);
		}else {
			throw new RequeteInvalideException("Il manque un champ");
		}
	}
	
	private boolean isPersonneGood(Personne p){
		boolean isGood = false;
		if(p != null) {
			if(p.getNom() != null && p.getPrenom() != null && p.getEmail() != null) {
				isGood = true;
			}else {
				isGood = false;
			}
		}
		return isGood;
	}
	
	private boolean isPersonneExisting(Personne p) {
		boolean isExisting = false;
		if(p != null) {
			isExisting = true;
		}else {
			isExisting = false;
		}
		return isExisting;
	}

}
