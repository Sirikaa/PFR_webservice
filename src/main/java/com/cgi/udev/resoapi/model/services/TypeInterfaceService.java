package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.TypeInterfaceDao;
import com.cgi.udev.resoapi.model.TypeInterface;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

public class TypeInterfaceService extends AbstractService{
	
	private TypeInterfaceDao dao = new TypeInterfaceDao();
	/*
	 * Retourne la liste des TypeInterfaces de la table TypeInterface
	 * throw InexistantException si la table est vide
	 */
	public List<TypeInterface> getAll() throws InexistantException{
		List<TypeInterface> liste = dao.getAll();
		if(!liste.isEmpty()) {
			return liste;
		}else {
			throw new InexistantException("Aucun type d'interface à afficher !");
		}
	}
	
	/*
	 * Retourne une TypeInterface en fonction du paramètre id
	 * throw InexistantException si le TypeInterface n'est pas trouvé en base
	 */
	public TypeInterface getTypeInterface(int id) throws InexistantException{
	 TypeInterface ti = dao.getTypeInterface(id);
	 if(ti.getId() != 0) {
		 return ti;
	 }else {
		 throw new InexistantException("Ce type d'interface n'existe pas !");
	 }
		
	}
	
	/*
	 * Méthode pour créer une TypeInterface dans la table TypeInterface
	 */
	public void create(TypeInterface ti) throws RequeteInvalideException, InexistantException{
		if(isExisting(ti)) {
			if(areFieldsFilled(ti)) {
				dao.create(ti);
			}else {
				throw new RequeteInvalideException("Il manque un champ");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucun type d'interface à créer !");
		}
	}
	
	/*
	 * Méthode pour mettre à jour une TypeInterface dans la table TypeInterface
	 */
	public void update(TypeInterface ti) throws RequeteInvalideException, InexistantException{
		if(isExisting(ti)) {
			if(areFieldsFilled(ti)) {
				if(!dao.update(ti)) {
					throw new InexistantException("Le type d'interface n'existe pas en base !");
				}
			}else {
				throw new RequeteInvalideException("Il manque un champ");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucun type d'interface à mettre à jour !");
		}
	}
	
	/*
	 * Méthode pour supprimer un TypeInterface dans la table TypeInterface
	 */
	public void delete(int id) throws InexistantException{
		if(!dao.delete(id)) {
			throw new InexistantException("Le type d'interface n'existe pas en base !");
		}
	}
	
	/*
	 * Méthode qui vérifie que les champs obligatoires à la création d'une TypeInterface ont bien été renseignés
	 */
	private boolean areFieldsFilled(TypeInterface ti){
		boolean isGood = false;
		if(ti != null) {
			if(ti.getLibelle() != null) {
				isGood = true;
			}else {
				isGood = false;
			}
		}
		return isGood;
	}

}
