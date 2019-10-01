package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.TypeMaterielDao;
import com.cgi.udev.resoapi.model.TypeMateriel;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

public class TypeMaterielService extends AbstractService{

	private TypeMaterielDao dao = new TypeMaterielDao();
	/*
	 * Retourne la liste des TypeMateriels de la table TypeMateriel
	 * throw InexistantException si la table est vide
	 */
	public List<TypeMateriel> getAll() throws InexistantException{
		List<TypeMateriel> liste = dao.getAll();
		if(!liste.isEmpty()) {
			return liste;
		}else {
			throw new InexistantException("Aucun type de matériel à afficher !");
		}
	}
	
	/*
	 * Retourne une TypeMateriel en fonction du paramètre id
	 * throw InexistantException si le TypeMateriel n'est pas trouvé en base
	 */
	public TypeMateriel getTypeMateriel(int id) throws InexistantException{
	 TypeMateriel tm = dao.getTypeMateriel(id);
	 if(tm.getId() != 0) {
		 return tm;
	 }else {
		 throw new InexistantException("Ce type de matériel n'existe pas !");
	 }
		
	}
	
	/*
	 * Méthode pour créer une TypeMateriel dans la table TypeMateriel
	 */
	public void create(TypeMateriel tm) throws RequeteInvalideException, InexistantException{
		if(isExisting(tm)) {
			if(areFieldsFilled(tm)) {
				dao.create(tm);
			}else {
				throw new RequeteInvalideException("Il manque un champ");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucun type de matériel à créer !");
		}
	}
	
	/*
	 * Méthode pour mettre à jour une TypeMateriel dans la table TypeMateriel
	 */
	public void update(TypeMateriel tm) throws RequeteInvalideException, InexistantException{
		if(isExisting(tm)) {
			if(areFieldsFilled(tm)) {
				if(!dao.update(tm)) {
					throw new InexistantException("Le type de matériel n'existe pas en base !");
				}
			}else {
				throw new RequeteInvalideException("Il manque un champ");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucun type de matériel à mettre à jour !");
		}
	}
	
	/*
	 * Méthode pour supprimer un TypeMateriel dans la table TypeMateriel
	 */
	public void delete(int id) throws InexistantException{
		if(!dao.delete(id)) {
			throw new InexistantException("Le type de matériel n'existe pas en base !");
		}
	}
	
	/*
	 * Méthode qui vérifie que les champs obligatoires à la création d'une TypeMateriel ont bien été renseignés
	 */
	private boolean areFieldsFilled(TypeMateriel tm){
		boolean isGood = false;
		if(tm != null) {
			if(tm.getLibelle() != null) {
				isGood = true;
			}else {
				isGood = false;
			}
		}
		return isGood;
	}
}
