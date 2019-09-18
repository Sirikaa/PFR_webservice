package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.Client;
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
					Client c = new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("adresse1"), rs.getString("adresse2"));
					int idCpVille = rs.getInt("idcpville");
					if(!rs.wasNull()) {
						c.setVille(getVille(idCpVille));
						c.setCp(getCp(idCpVille));
						//c.setVille("mon cul");
					}
					clients.add(c);
				}
			}
			return clients;
		}catch(SQLException e) {
			throw new RuntimeException();
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
			throw new RuntimeException();
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
			throw new RuntimeException();
		}
	}
	
	/*
	 * Fonction qui va récupérer la liste des contacts d'un client
	 * en prenant en paramètre l'id du client
	 */
	private List<Personne> getContacts(int id){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<Personne> personnes = new ArrayList<Personne>();
			try(ResultSet rs = stmt.executeQuery("select idpersonne form appartient where idclient ="+id)){
				while(rs.next()) {
					int idContact = rs.getInt("id");
					try(ResultSet rs2 = stmt.executeQuery("select * form personne where id"+idContact)){
						if(rs2.next()) {
							Personne p = new Personne()
						}
					}
				}
			}
			return personnes;
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
}
