package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.TypeMateriel;

public class TypeMaterielDao extends AbstractDao{
	/*
	 * Méthode pour récupérer la liste de tous les type de matériels dans la table typemateriel
	 */
	public List<TypeMateriel> getAll(){
		List<TypeMateriel> tms = new ArrayList<TypeMateriel>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from typemateriel")){
				while(rs.next()) {
					tms.add(buildTypeMateriel(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException();
		}
		return tms;
	}
	
	/*
	 * Méthode qui retourne les informations d'un type de matériel
	 * il prend en paramètre l'id d'un type de matériel
	 */
	public TypeMateriel getTypeMateriel(int id){
		TypeMateriel tm = new TypeMateriel();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from typemateriel where id ="+id)){
				if(rs.next()) {
					tm = buildTypeMateriel(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException();
		}
		return tm;
	}
	
	/*
	 * Méthode qui retourne le type de matériel d'un matériel
	 * il prend en paramètre l'id du matériel
	 */
	public TypeMateriel getTypeMaterielOfMateriel(int idMateriel){
		TypeMateriel tm = new TypeMateriel();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from typemateriel tm "
												+ "left join materiel m "
												+ "on tm.id = m.idtype "
												+ "where m.id ="+idMateriel)){
				if(rs.next()) {
					tm = buildTypeMateriel(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return tm;
	}
	
	private TypeMateriel buildTypeMateriel (ResultSet rs) throws SQLException{
		return  new TypeMateriel(rs.getInt("id"), rs.getString("libelle"));
	}
	
	/*
	 * Méthode pour insérer une ville dans la table ville
	 * Prend une ville en argument
	 */
	public void create(TypeMateriel tm){
		String sql = "insert into typemateriel (libelle) values (?)";
		boolean isTransactionOk = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, tm.getLibelle());
				stmt.executeUpdate();
				try(ResultSet rs = stmt.getGeneratedKeys()){
					if(rs.next()) {
						tm.setId(rs.getInt(1));
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
	
	public boolean update(TypeMateriel tm) {
		String sql = "update typemateriel set libelle = ? where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeUpdateSomething = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, tm.getLibelle());
				stmt.setInt(2, tm.getId());
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
		String sql = "delete from typemateriel where id = ?";
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
