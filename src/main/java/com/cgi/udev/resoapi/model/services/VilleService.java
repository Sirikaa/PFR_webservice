package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.VilleDao;
import com.cgi.udev.resoapi.model.Ville;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

public class VilleService extends AbstractService{

	private VilleDao dao = new VilleDao();
	
	/*
	 * Retourne la liste des Villes de la table Ville
	 * throw InexistantException si la table est vide
	 */
	public List<Ville> getAll() throws InexistantException{
		List<Ville> liste = dao.getAll();
		if(!liste.isEmpty()) {
			return liste;
		}else {
			throw new InexistantException("Aucune Ville à afficher !");
		}
	}
	
	/*
	 * Retourne une Ville en fonction du paramètre id
	 * throw InexistantException si la Ville n'est pas trouvée en base
	 */
	public Ville getVille(int id) throws InexistantException{
	 Ville p = dao.getVille(id);
	 if(p.getId() != 0) {
		 return p;
	 }else {
		 throw new InexistantException("Cette Ville n'existe pas !");
	 }
		
	}
	
	/*
	 * Méthode pour créer une Ville dans la table Ville
	 */
	public void create(Ville v) throws RequeteInvalideException, InexistantException{
		if(isExisting(v)) {
			if(areFieldsFilled(v)) {
				dao.create(v);
			}else {
				throw new RequeteInvalideException("Il manque un champ");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucune Ville à créer !");
		}
	}
	
	/*
	 * Méthode pour mettre à jour une Ville dans la table Ville
	 */
	public void update(Ville v) throws RequeteInvalideException, InexistantException{
		if(isExisting(v)) {
			if(areFieldsFilled(v)) {
				if(!dao.update(v)) {
					throw new InexistantException("La Ville n'existe pas en base !");
				}
			}else {
				throw new RequeteInvalideException("Il manque un champ");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucune Ville à mettre à jour !");
		}
	}
	
	/*
	 * Méthode pour supprimer une Ville dans la table Ville
	 */
	public void delete(int id) throws InexistantException{
		if(!dao.delete(id)) {
			throw new InexistantException("La Ville n'existe pas en base !");
		}
	}
	
	/*
	 * Méthode qui vérifie que les champs obligatoires à la création d'une Ville ont bien été renseignés
	 */
	private boolean areFieldsFilled(Ville v){
		boolean isGood = false;
		if(v != null) {
			if(v.getCp() != null && v.getVille() != null) {
				isGood = true;
			}else {
				isGood = false;
			}
		}
		return isGood;
	}
}
