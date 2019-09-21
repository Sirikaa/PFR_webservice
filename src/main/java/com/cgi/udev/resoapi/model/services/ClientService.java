package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.ClientDao;
import com.cgi.udev.resoapi.model.Client;
import com.cgi.udev.resoapi.model.Personne;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;

public class ClientService {

	private ClientDao dao = new ClientDao();
	
	/*
	 * Retourne la liste des clients de la table Client
	 * throw InexistantException si la table est vide
	 */
	public List<Client> getAll() throws InexistantException{
		List<Client> liste = dao.getAll();
		if(!liste.isEmpty()) {
			return liste;
		}else {
			throw new InexistantException("Aucun client à afficher !");
		}
	}
	
	public Client getClient(int id) throws InexistantException{
		Client c = dao.getClient(id);
		if(c.getId() != 0) {
			return c;
		}else {
			throw new InexistantException("Ce client n'existe pas !");
		}
	}
	
	public List<Personne> getContacts(int id) throws InexistantException{
		List<Personne> liste = dao.getContacts(id);
		if(!liste.isEmpty()) {
			return liste;
		}else {
			throw new InexistantException("Aucun contacts à afficher !");
		}
	}
}
