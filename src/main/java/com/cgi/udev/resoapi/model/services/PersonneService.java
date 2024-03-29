package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.PersonneDao;
import com.cgi.udev.resoapi.model.Personne;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

public class PersonneService extends AbstractService{
	
	private PersonneDao dao = new PersonneDao();
	private String error = "";
	
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
	public Personne getPersonne(int id) throws InexistantException{
	 Personne p = dao.getPersonne(id);
	 if(p.getId() != 0) {
		 return p;
	 }else {
		 throw new InexistantException("Cette personne n'existe pas !");
	 }
		
	}
	
	/*
	 * Méthode pour créer une personne dans la table personne
	 */
	public void create(Personne p, int idClient) throws RequeteInvalideException, InexistantException{
		if(isExisting(p)) {
			if(idClient != 0) {
				if(areFieldsFilled(p)) {
					dao.create(p, idClient);
				}else {
					throw new RequeteInvalideException(this.error);
				}
			}else {
				throw new InexistantException("Vous n'avez renseigné aucun client !");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucune personne à créer !");
		}
	}
	
	/*
	 * Méthode pour mettre à jour une personne dans la table personne
	 */
	public void update(Personne p) throws RequeteInvalideException, InexistantException{
		if(isExisting(p)) {
			if(areFieldsFilled(p)) {
				if(!dao.update(p)) {
					throw new InexistantException("Erreur lors de la mise à jour !");
				}
			}else {
				throw new RequeteInvalideException(this.error);
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucune personne à mettre à jour !");
		}
	}
	
	/*
	 * Méthode pour supprimer une personne dans la table personne
	 */
	public void delete(int id) throws InexistantException{
		if(!dao.delete(id)) {
			throw new InexistantException("La personne n'existe pas en base !");
		}
	}
	
	/*
	 * Méthode qui vérifie que les champs obligatoires à la création d'une personne ont bien été renseignés
	 */
	private boolean areFieldsFilled(Personne p){
		boolean isGood = false;
		if(p != null) {
			if(p.getNom() != null && p.getPrenom() != null && p.getEmail() != null && p.getFonction() != null) {
				isGood = true;
			}else {
				if(p.getNom() == null) {
					this.error = "Merci de renseigner le nom";
				}else if( p.getPrenom() == null) {
					this.error = "Merci de renseigner le prénom";
				}else if(p.getEmail() == null) {
					this.error = "Merci de renseigner l'e-mail";
				}else if(p.getFonction() == null) {
					this.error = "Merci de renseigner la fonction";
				}
				isGood = false;
			}
		}
		return isGood;
	}

}
