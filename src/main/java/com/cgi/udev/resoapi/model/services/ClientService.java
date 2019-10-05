package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.ClientDao;
import com.cgi.udev.resoapi.dao.PersonneDao;
import com.cgi.udev.resoapi.model.Client;
import com.cgi.udev.resoapi.model.Personne;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

public class ClientService extends AbstractService{

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
		PersonneDao pDao = new PersonneDao();
		List<Personne> liste = pDao.getPersonnesOfClient(id);
		if(!liste.isEmpty()) {
			return liste;
		}else {
			throw new InexistantException("Aucun contacts à afficher pour ce client!");
		}
	}
	
	/*
	 * Méthode pour créer un client dans la table client
	 */
	public void create(Client c) throws RequeteInvalideException, InexistantException{
		if(isExisting(c)) {
			if(areFieldsFilled(c)) {
				dao.create(c);
			}else {
				throw new RequeteInvalideException("Il manque un champ");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucun client à créer !");
		}
	}
	
	/*
	 * Méthode pour mettre à jour un client dans la table client
	 */
	public void update(Client c) throws RequeteInvalideException, InexistantException{
		if(isExisting(c)) {
			if(areFieldsFilled(c)) {
				if(!dao.update(c)) {
					throw new InexistantException("Le client n'existe pas en base !");
				}
			}else {
				throw new RequeteInvalideException("Il manque un champ");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucun client à mettre à jour !");
		}
	}
	
	/*
	 * Méthode pour supprimer un client dans la table client
	 */
	public void delete(int id) throws InexistantException{
		if(!dao.delete(id)) {
			throw new InexistantException("Le client n'existe pas en base !");
		}
	}
	
	/*
	 * Méthode qui vérifie que l'authentification retourne quelque chose
	 */
	public Client login(String matricule, String password) throws InexistantException{
		 Client c = dao.login(matricule, password);
		 if(c.getId() != 0) {
			 return c;
		 }else {
			 throw new InexistantException("Login ou mot de passe incorrect");
		 }	
	}
	
	/*
	 * Méthode qui vérifie que les champs obligatoires à la création d'un client ont bien été renseignés
	 */
	private boolean areFieldsFilled(Client c){
		boolean isGood = false;
		if(c != null) {
			if(c.getNom() != null && c.getMatricule() != null && c.getPassword() != null) {
				isGood = true;
			}else {
				isGood = false;
			}
		}
		return isGood;
	}
}
