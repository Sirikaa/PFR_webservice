package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.TypeInterface;

public class TypeInterfaceDao extends AbstractDao{

	/*
	 * Méthode pour récupérer la liste de tous les clients dans la table Client de la BDD
	 */
	public List<TypeInterface> getAll(){
		List<TypeInterface> tis = new ArrayList<TypeInterface>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from typeif")){
				while(rs.next()) {
					tis.add(buildTypeInterface(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return tis;
	}
	
	public TypeInterface getTypeInterface(int id){
		TypeInterface ti = new TypeInterface();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from typeif where id ="+id)){
				if(rs.next()) {
					ti = buildTypeInterface(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return ti;
	}
	
	/*
	 * Méthode qui retourne le type d'inteface d'une interface
	 * il prend en paramètre l'id de l'inteface
	 */
	public TypeInterface getTypeInterfaceOfInterface(int idInterface){
		TypeInterface ti = new TypeInterface();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from typeif ti "
												+ "left join interface i "
												+ "on ti.id = i.idtype "
												+ "where i.id ="+idInterface)){
				if(rs.next()) {
					ti = buildTypeInterface(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return ti;
	}
	
	private TypeInterface buildTypeInterface(ResultSet rs) throws SQLException{
		return new TypeInterface(rs.getInt("id"), rs.getString("libelle")); 
	}
	
	/*
	 * Méthode pour insérer un type d'inteface dans la table typeif
	 * Prend un type d'interface en argument
	 */
	public void create(TypeInterface ti){
		String sql = "insert into typeif (libelle) values (?)";
		boolean isTransactionOk = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, ti.getLibelle());
				stmt.executeUpdate();
				try(ResultSet rs = stmt.getGeneratedKeys()){
					if(rs.next()) {
						ti.setId(rs.getInt(1));
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
	
	public boolean update(TypeInterface ti) {
		String sql = "update typeif set libelle = ? where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeUpdateSomething = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, ti.getLibelle());
				stmt.setInt(2, ti.getId());
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
	 * Méthode pour supprimer un type d'interface de la table typeif
	 * Prend un type d'interface en paramètre
	 */
	// Créer le delete sur les tables en lien avec personne, avec des clés étrangères puis y faire appel.
	public boolean delete(int id) {
		String sql = "delete from typeif where id = ?";
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
