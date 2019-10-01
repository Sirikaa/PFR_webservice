package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.TypeAffectation;

public class TypeAffectationDao extends AbstractDao{

	/*
	 * Méthode pour récupérer la liste de tous les clients dans la table Client de la BDD
	 */
	public List<TypeAffectation> getAll(){
		List<TypeAffectation> tas = new ArrayList<TypeAffectation>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from typeaffectation")){
				while(rs.next()) {
					tas.add(buildTypeInterface(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return tas;
	}
	
	public TypeAffectation getTypeAffectation(int id){
		TypeAffectation ta = new TypeAffectation();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from typeaffectation where id ="+id)){
				if(rs.next()) {
					ta = buildTypeInterface(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return ta;
	}
	
	private TypeAffectation buildTypeInterface(ResultSet rs) throws SQLException{
		return new TypeAffectation(rs.getInt("id"), rs.getString("libelle")); 
	}
	
	/*
	 * Méthode pour insérer un type d'affectation dans la table typeaffectation
	 * Prend un type d'affectation en argument
	 */
	public void create(TypeAffectation ta){
		String sql = "insert into typeaffectation (libelle) values (?)";
		boolean isTransactionOk = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, ta.getLibelle());
				stmt.executeUpdate();
				try(ResultSet rs = stmt.getGeneratedKeys()){
					if(rs.next()) {
						ta.setId(rs.getInt(1));
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
	
	public boolean update(TypeAffectation ta) {
		String sql = "update typeaffectation set libelle = ? where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeUpdateSomething = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, ta.getLibelle());
				stmt.setInt(2, ta.getId());
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
	 * Méthode pour supprimer un type d'affectation de la table typeaffectation
	 * Prend un type d'affectation en paramètre
	 */
	public boolean delete(int id) {
		String sql = "delete from typeaffectation where id = ?";
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
