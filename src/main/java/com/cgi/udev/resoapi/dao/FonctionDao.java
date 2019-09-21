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
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<Fonction> fonctions = new ArrayList<Fonction>();
			try(ResultSet rs = stmt.executeQuery("select * from fonction")){
				while(rs.next()) {
					Fonction f = new Fonction(rs.getInt("id"), rs.getString("libelle"));
					fonctions.add(f);
				}
			}
			return fonctions;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Fonction getFonction(int id){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			Fonction f = new Fonction();
			try(ResultSet rs = stmt.executeQuery("select * from fonction where id ="+id)){
				if(rs.next()) {
					f.setId(rs.getInt("id"));
					f.setLibelle(rs.getString("libelle"));
				}
			}
			return f;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
