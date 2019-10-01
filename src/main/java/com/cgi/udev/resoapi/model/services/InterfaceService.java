package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.InterfaceDao;
import com.cgi.udev.resoapi.dao.MaterielDao;
import com.cgi.udev.resoapi.model.Interface;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

public class InterfaceService extends AbstractService{

	private InterfaceDao dao = new InterfaceDao();
	private MaterielDao mDao = new MaterielDao();
		
		/*
		 * Retourne la liste des interfaces de la table Interface
		 * throw InexistantException si la table est vide
		 */
		public List<Interface> getAll() throws InexistantException{
			List<Interface> liste = dao.getAll();
			if(!liste.isEmpty()) {
				return liste;
			}else {
				throw new InexistantException("Aucune interface à afficher !");
			}
		}
		
		public Interface getInterface(int id) throws InexistantException{
			Interface i = dao.getInterface(id);
			if(i.getId() != 0) {
				return i;
			}else {
				throw new InexistantException("Cette interface n'existe pas !");
			}
		}
		
		/*
		 * Méthode pour créer une interface dans la table interface
		 * @params interface
		 * @params id matériel
		 */
		public void create(Interface i, int idMateriel) throws RequeteInvalideException, InexistantException{
			if(isExisting(i)) {
				if(mDao.getMateriel(idMateriel).getId() != 0) {
					if(areFieldsFilled(i, idMateriel)) {
						dao.create(i, idMateriel);
					}else {
						throw new RequeteInvalideException("Il manque un champ");
					}
				}else {
					throw new InexistantException("Impossible de créer l'interface car le matériel n'existe pas !");
				}
			}else {
				throw new InexistantException("Vous n'avez renseigné aucune interface à créer !");
			}
		}
		
		/*
		 * Méthode pour mettre à jour une interface dans la table interface
		 * @params interface
		 * @params id client
		 */
		public void update(Interface i, int idMateriel) throws RequeteInvalideException, InexistantException{
			if(isExisting(i)) {
				if(mDao.getMateriel(idMateriel).getId() != 0) {
					if(areFieldsFilled(i, idMateriel)) {
						if(!dao.update(i, idMateriel)) {
							throw new InexistantException("L'interface n'existe pas en base !");
						}
					}else {
						throw new RequeteInvalideException("Il manque un champ");
					}
				}else {
					throw new InexistantException("Impossible de mettre à jour l'interface car le matériel n'existe pas !");
				}
			}else {
				throw new InexistantException("Vous n'avez renseigné aucune interface à mettre à jour !");
			}
		}
		
		/*
		 * Méthode pour supprimer une interface dans la table interface
		 */
		public void delete(int id) throws InexistantException{
			if(!dao.delete(id)) {
				throw new InexistantException("L'interface n'existe pas en base !");
			}
		}
		
		/*
		 * Méthode qui vérifie que les champs obligatoires à la création d'une interface ont bien été renseignés
		 */
		private boolean areFieldsFilled(Interface i, int idMateriel){
			boolean isGood = false;
			if(i != null) {
				if(i.getNom() != null && i.getType() != null && idMateriel != 0) {
					isGood = true;
				}else {
					isGood = false;
				}
			}
			return isGood;
		}
}
