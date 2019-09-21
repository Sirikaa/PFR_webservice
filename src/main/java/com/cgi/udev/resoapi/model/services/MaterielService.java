package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.MaterielDao;
import com.cgi.udev.resoapi.model.Materiel;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;

public class MaterielService {
	
private MaterielDao dao = new MaterielDao();
	
	/*
	 * Retourne la liste des matériels de la table Materiel
	 * throw InexistantException si la table est vide
	 */
	public List<Materiel> getAll() throws InexistantException{
		List<Materiel> liste = dao.getAll();
		if(!liste.isEmpty()) {
			return liste;
		}else {
			throw new InexistantException("Aucun matériel à afficher !");
		}
	}
	
	public Materiel getMateriel(int id) throws InexistantException{
		Materiel m = dao.getMateriel(id);
		if(m.getId() != 0) {
			return m;
		}else {
			throw new InexistantException("Ce matériel n'existe pas !");
		}
	}

}
