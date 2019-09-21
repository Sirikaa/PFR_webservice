package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.Client;
import com.cgi.udev.resoapi.model.Materiel;
import com.cgi.udev.resoapi.model.Personne;

public class ClientDao extends AbstractDao{
	
	/*
	 * Méthode pour récupérer la liste de tous les clients dans la table Client de la BDD
	 */
	public List<Client> getAll(){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<Client> clients = new ArrayList<Client>();
			try(ResultSet rs = stmt.executeQuery("select * from client")){
				while(rs.next()) {
					Client c = getClient(rs.getInt("id"));
					clients.add(c);
				}
			}
			return clients;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Client getClient(int id){
		
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			Client c = new Client();
			try(ResultSet rs = stmt.executeQuery("select * from client where id ="+id)){
				if(rs.next()) {
					c.setId(rs.getInt("id"));
					c.setNom(rs.getString("nom"));
					c.setMatricule(rs.getString("matricule"));
					c.setPassword(rs.getString("password"));
					c.setAdresse1(rs.getString("adresse1"));
					c.setAdresse2(rs.getString("adresse2"));
					int idCpVille = rs.getInt("idcpville");
					if(!rs.wasNull()) {
						c.setVille(getVille(idCpVille));
						c.setCp(getCp(idCpVille));
					}
					c.setContacts(getContacts(rs.getInt("id")));
					c.setMateriels(getMateriels(rs.getInt("id")));
				}
			}
			return c;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * Méthode pour récupérer la ville du client dans la table Ville car
	 * conceptuellement je ne souhaitais pas d'objet Ville.
	 */
	private String getVille(int id) {
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			String ville = "";
			try(ResultSet rs = stmt.executeQuery("select ville from ville where id ="+id)){
				if(rs.next()) {
					ville = rs.getString("ville");
				}	
			}
			return ville;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * Méthode pour récupérer le CP du client dans la table Ville car
	 * conceptuellement je ne souhaitais pas d'objet Ville.
	 */
	private String getCp(int id) {
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			String cp = "";
			try(ResultSet rs = stmt.executeQuery("select codepostal from ville where id ="+id)){
				if(rs.next()) {
					cp = rs.getString("codepostal");
				}
			}
			return cp;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * Fonction qui va récupérer la liste des contacts d'un client
	 * en prenant en paramètre l'id du client
	 */
	public List<Personne> getContacts(int idClient){
		PersonneDao pDao = new PersonneDao();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<Personne> personnes = new ArrayList<Personne>();
			try(ResultSet rs = stmt.executeQuery("select idpersonne from appartient where idclient ="+idClient)){
				while(rs.next()) {
					personnes.add(pDao.getPersonne(rs.getInt("idpersonne")));
				}
			}
			return personnes;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Materiel> getMateriels(int idClient){
		MaterielDao mDao = new MaterielDao();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
			Statement stmt = connexion.createStatement()){
			List<Materiel> materiels = new ArrayList<Materiel>();
			try(ResultSet rs = stmt.executeQuery("select id from materiel where idclient ="+idClient)){
				while(rs.next()) {
					materiels.add(mDao.getMateriel(rs.getInt("id")));
				}
				return materiels;
			}		
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
