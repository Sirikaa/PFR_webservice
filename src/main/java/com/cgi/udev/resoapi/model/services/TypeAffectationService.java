package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.TypeAffectationDao;
import com.cgi.udev.resoapi.model.TypeAffectation;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

public class TypeAffectationService extends AbstractService{
	private TypeAffectationDao dao = new TypeAffectationDao();
	/*
	 * Retourne la liste des TypeAffectations de la table TypeAffectation
	 * throw InexistantException si la table est vide
	 */
	public List<TypeAffectation> getAll() throws InexistantException{
		List<TypeAffectation> liste = dao.getAll();
		if(!liste.isEmpty()) {
			return liste;
		}else {
			throw new InexistantException("Aucun type d'affectation à afficher !");
		}
	}
	
	/*
	 * Retourne une TypeAffectation en fonctaon du paramètre id
	 * throw InexistantException si le TypeAffectation n'est pas trouvé en base
	 */
	public TypeAffectation getTypeAffectation(int id) throws InexistantException{
	 TypeAffectation ta = dao.getTypeAffectation(id);
	 if(ta.getId() != 0) {
		 return ta;
	 }else {
		 throw new InexistantException("Ce type d'affectation n'existe pas !");
	 }
		
	}
	
	/*
	 * Méthode pour créer une TypeAffectation dans la table TypeAffectation
	 */
	public void create(TypeAffectation ta) throws RequeteInvalideException, InexistantException{
		if(isExisting(ta)) {
			if(areFieldsFilled(ta)) {
				dao.create(ta);
			}else {
				throw new RequeteInvalideException("Il manque un champ");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucun type d'affectation à créer !");
		}
	}
	
	/*
	 * Méthode pour mettre à jour une TypeAffectation dans la table TypeAffectation
	 */
	public void update(TypeAffectation ta) throws RequeteInvalideException, InexistantException{
		if(isExisting(ta)) {
			if(areFieldsFilled(ta)) {
				if(!dao.update(ta)) {
					throw new InexistantException("Le type d'affectation n'existe pas en base !");
				}
			}else {
				throw new RequeteInvalideException("Il manque un champ");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucun type d'affectation à mettre à jour !");
		}
	}
	
	/*
	 * Méthode pour supprimer un TypeAffectation dans la table TypeAffectation
	 */
	public void delete(int id) throws InexistantException{
		if(!dao.delete(id)) {
			throw new InexistantException("Le type d'affectation n'existe pas en base !");
		}
	}
	
	/*
	 * Méthode qui vérifie que les champs obligatoires à la créataon d'une TypeAffectation ont bien été renseignés
	 */
	private boolean areFieldsFilled(TypeAffectation ta){
		boolean isGood = false;
		if(ta != null) {
			if(ta.getLibelle() != null) {
				isGood = true;
			}else {
				isGood = false;
			}
		}
		return isGood;
	}

}
