package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.Ville;

public class VilleDao extends AbstractDao{

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
					v = buildVille(rs);
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
	
	/*
	 * Méthode pour insérer une ville dans la table ville
	 * Prend une ville en argument
	 */
	public void create(Ville v){
		String sql = "insert into ville (codepostal, ville) values (?, ?)";
		boolean isTransactionOk = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, v.getCp());
				stmt.setString(2, v.getVille());
				stmt.executeUpdate();
				try(ResultSet rs = stmt.getGeneratedKeys()){
					if(rs.next()) {
						v.setId(rs.getInt(1));
					}
				}
				isTransactionOk = true;
			}finally {
				checkTransactionAndClose(connexion, isTransactionOk);
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean update(Ville v) {
		String sql = "update ville set codepostal = ?, ville = ? where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeUpdateSomething = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, v.getCp());
				stmt.setString(2, v.getVille());
				stmt.setInt(3, v.getId());
				if(stmt.executeUpdate() > 0) {
					haveWeUpdateSomething = true;
				}else {
					haveWeUpdateSomething = false;
				}
				isTransactionOk = true;
			}finally {
				checkTransactionAndClose(connexion, isTransactionOk);
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return haveWeUpdateSomething;
	}
	
	/*
	 * Méthode pour supprimer une ville de la table ville
	 * Prend une ville en paramètre
	 */
	// Créer le delete sur les tables en lien avec personne, avec des clés étrangères puis y faire appel.
	public boolean delete(int id) {
		String sql = "delete from ville where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeDeleteSomething;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql)) {
				stmt.setInt(1, id);
				if(stmt.executeUpdate() > 0) {
					haveWeDeleteSomething = true;
				}else {
					haveWeDeleteSomething = false;
				}
				isTransactionOk = true;
			}finally {
				checkTransactionAndClose(connexion, isTransactionOk);
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return haveWeDeleteSomething;
	}
}
