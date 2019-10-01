package com.cgi.udev.resoapi.model.services;

import java.util.List;

import com.cgi.udev.resoapi.dao.AdresseIpDao;
import com.cgi.udev.resoapi.dao.InterfaceDao;
import com.cgi.udev.resoapi.model.AdresseIp;
import com.cgi.udev.resoapi.model.exceptions.InexistantException;
import com.cgi.udev.resoapi.model.exceptions.RequeteInvalideException;

public class AdresseIpService extends AbstractService{

	private AdresseIpDao dao = new AdresseIpDao();
	private InterfaceDao iDao = new InterfaceDao();
		
		/*
		 * Retourne la liste des adresses ip de la table AdresseIp
		 * throw InexistantException si la table est vide
		 */
		public List<AdresseIp> getAll() throws InexistantException{
			List<AdresseIp> liste = dao.getAll();
			if(!liste.isEmpty()) {
				return liste;
			}else {
				throw new InexistantException("Aucune adresse ip à afficher !");
			}
		}
		
		public AdresseIp getAdresseIp(int id) throws InexistantException{
			AdresseIp i = dao.getAdresseIp(id);
			if(i.getId() != 0) {
				return i;
			}else {
				throw new InexistantException("Cette adresse ip n'existe pas !");
			}
		}
		
		/*
		 * Méthode pour créer une adresse ip dans la table adresse ip
		 * @params adresse ip
		 * @params id interface
		 */
		public void create(AdresseIp ip, int idInterface) throws RequeteInvalideException, InexistantException{
			if(isExisting(ip)) {
				if(iDao.getInterface(idInterface).getId() != 0) {
					if(areFieldsFilled(ip, idInterface)) {
						dao.create(ip, idInterface);
					}else {
						throw new RequeteInvalideException("Il manque un champ");
					}
				}else {
					throw new InexistantException("Impossible de créer l'adresse ip car l'interface n'existe pas !");
				}
			}else {
				throw new InexistantException("Vous n'avez renseigné aucune adresse ip à créer !");
			}
		}
		
		/*
		 * Méthode pour mettre à jour une adresse ip dans la table adresse ip
		 * @params adresse ip
		 * @params id client
		 */
		public void update(AdresseIp ip, int idInterface) throws RequeteInvalideException, InexistantException{
			if(isExisting(ip)) {
				if(iDao.getInterface(idInterface).getId() != 0) {
					if(areFieldsFilled(ip, idInterface)) {
						if(!dao.update(ip, idInterface)) {
							throw new InexistantException("L'adresse ip n'existe pas en base !");
						}
					}else {
						throw new RequeteInvalideException("Il manque un champ");
					}
				}else {
					throw new InexistantException("Impossible de mettre à jour l'adresse ip car l'interface n'existe pas !");
				}
			}else {
				throw new InexistantException("Vous n'avez renseigné aucune adresse ip à mettre à jour !");
			}
		}
		
		/*
		 * Méthode pour supprimer une adresse ip dans la table adresse ip
		 */
		public void delete(int id) throws InexistantException{
			if(!dao.delete(id)) {
				throw new InexistantException("L'adresse ip n'existe pas en base !");
			}
		}
		
		/*
		 * Méthode qui vérifie que les champs obligatoires à la création d'une adresse ip ont bien été renseignés
		 */
		private boolean areFieldsFilled(AdresseIp i, int idInterface){
			boolean isGood = false;
			if(i != null) {
				if(i.getIpv4() != null && i.getMasque() != null && idInterface != 0 && i.getTypeAffectation().getId() != 0) {
					isGood = true;
				}else {
					isGood = false;
				}
			}
			return isGood;
		}
}
