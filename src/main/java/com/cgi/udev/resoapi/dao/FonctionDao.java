package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.Fonction;

public class FonctionDao extends AbstractDao{

	/*
	 * Méthode pour récupérer la liste de tous les clients dans la table Client de la BDD
	 */
	public List<Fonction> getAll(){
		List<Fonction> fonctions = new ArrayList<Fonction>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from fonction")){
				while(rs.next()) {
					fonctions.add(buildFonction(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return fonctions;
	}
	
	public Fonction getFonction(int id){
		Fonction f = new Fonction();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from fonction where id ="+id)){
				if(rs.next()) {
					f = buildFonction(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return f;
	}
	
	/*
	 * Méthode pour récupérer la fonction d'une personne selon le client
	 * car une personne peut travailler pour plusieurs clients
	 * Prend en paramètre l'id de la personne et l'id du client.
	 */
	public Fonction getFonctionOfPersonneForClient(int idPersonne, int idClient) {
		Fonction f = new Fonction();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from fonction f "
												+ "left join appartient a "
												+ "on f.id = a.idfonction "
												+ "where idclient ="+idClient+" and idpersonne = "+idPersonne)){
				if(rs.next()) {	
					f = buildFonction(rs);
				}
			}
			return f;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Fonction buildFonction(ResultSet rs) throws SQLException{
		return new Fonction(rs.getInt("id"), rs.getString("libelle"));
	}
}
