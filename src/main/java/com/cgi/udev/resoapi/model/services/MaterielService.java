package com.cgi.udev.resoapi.model.services;

import java.util.List;
import com.cgi.udev.resoapi.dao.ClientDao;
import com.cgi.udev.resoapi.dao.MaterielDao;
import com.cgi.udev.resoapi.model.Materiel;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

public class MaterielService extends AbstractService{
	
private MaterielDao dao = new MaterielDao();
private ClientDao cDao = new ClientDao();
	
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
	
	/*
	 * Méthode pour créer une materiel dans la table materiel
	 * @params materiel
	 * @params id client
	 */
	public void create(Materiel materiel, int idClient) throws RequeteInvalideException, InexistantException{
		if(isExisting(materiel)) {
			if(cDao.getClient(idClient).getId() != 0) {
				if(areFieldsFilled(materiel, idClient)) {
					if(!dao.create(materiel, idClient)) {
						throw new RequeteInvalideException("Erreur lors de la création du matériel");
					}
				}else {
					throw new RequeteInvalideException("Il manque un champ");
				}
			}else {
				throw new InexistantException("Impossible de créer le matériel car le client n'existe pas !");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucun materiel à créer !");
		}
	}
	
	/*
	 * Méthode pour mettre à jour une materiel dans la table materiel
	 * @params materiel
	 * @params id client
	 */
	public void update(Materiel m, int idClient) throws RequeteInvalideException, InexistantException{
		if(isExisting(m)) {
			if(cDao.getClient(idClient).getId() != 0) {
				if(areFieldsFilled(m, idClient)) {
					if(!dao.update(m, idClient)) {
						throw new InexistantException("Le matériel n'existe pas en base !");
					}
				}else {
					throw new RequeteInvalideException("Il manque un champ");
				}
			}else {
				throw new InexistantException("Impossible de mettre à jour le matériel car le client n'existe pas !");
			}
		}else {
			throw new InexistantException("Vous n'avez renseigné aucune materiel à mettre à jour !");
		}
	}
	
	/*
	 * Méthode pour supprimer une materiel dans la table materiel
	 */
	public void delete(int id) throws InexistantException{
		if(!dao.delete(id)) {
			throw new InexistantException("Le matériel n'existe pas en base !");
		}
	}
	
	/*
	 * Méthode qui vérifie que les champs obligatoires à la création d'une materiel ont bien été renseignés
	 */
	private boolean areFieldsFilled(Materiel m, int idClient){
		boolean isGood = false;
		if(m != null) {
			if(m.getLibelle() != null && m.getSerial() != null && m.getType() != null && idClient != 0) {
				isGood = true;
			}else {
				isGood = false;
			}
		}
		return isGood;
	}

}
