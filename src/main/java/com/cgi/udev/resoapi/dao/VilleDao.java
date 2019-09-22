package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.Ville;

public class VilleDao {

	/*
	 * Méthode pour récupérer la liste de toutes les villes dans la table ville de la BDD
	 */
	public List<Ville> getAll(){
		List<Ville> villes = new ArrayList<Ville>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from ville")){
				while(rs.next()) {
					villes.add(buildVille(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return villes;
	}
	
	public Ville getVille(int id){
		Ville v = new Ville();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from ville where id ="+id)){
				if(rs.next()) {
					buildVille(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return v;
	}
	
	/*
	 * Méthode pour récupérer la ville du client dans la table ville
	 * A pour paramètre l'id du client.
	 */
	public Ville getVilleOfClient(int idClient) {
		Ville v = new Ville();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from ville v "
												+ "inner join client c "
												+ "on v.id = c.idcpville "
												+ "where c.id ="+idClient)){
				if(rs.next()) {
					v = buildVille(rs);
				}	
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return v;
	}
	
	private Ville buildVille(ResultSet rs) throws SQLException{
		return new Ville(rs.getInt("id"), rs.getString("codepostal"), rs.getString("ville"));
	}
}
