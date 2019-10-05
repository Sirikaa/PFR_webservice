package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.Interface;

public class InterfaceDao extends AbstractDao{
	
	/*
	 * Méthode pour récupérer la liste de toutes les interfaces dans la table Interface de la BDD
	 */
	public List<Interface> getAll(){
		List<Interface> interfaces = new ArrayList<Interface>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from interface")){
				while(rs.next()) {
					interfaces.add(buildInterface(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return interfaces;
	}
	
	public Interface getInterface(int id){
		Interface i = new Interface();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from interface where id ="+id)){
				if(rs.next()) {
					i = buildInterface(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	
	/*
	 * Méthode qui retourne la liste de toutes les interfaces d'un matériel
	 * Prend en paramètre l'id du matériel
	 */
	public List<Interface> getInterfacesOfMateriel(int idMateriel){
		List<Interface> interfaces = new ArrayList<Interface>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from interface i " + 
												 "left join materiel m " + 
												 "on i.idmateriel = m.id " + 
												 "where m.id ="+idMateriel)){
				while(rs.next()) {
					interfaces.add(buildInterface(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return interfaces;
	}
	
	private Interface buildInterface (ResultSet rs) throws SQLException{
		TypeInterfaceDao tiDao = new TypeInterfaceDao();
		AdresseIpDao ipDao = new AdresseIpDao();
		return new Interface(rs.getInt("id"), rs.getString("nom"), rs.getString("mac"), tiDao.getTypeInterfaceOfInterface(rs.getInt("id")), ipDao.getAdresseIpOfInterface(rs.getInt("id")));
	}
	
	/*
	 * Méthode pour insérer une interface dans la table interface
	 * Prend une interface en argument et l'id du client
	 */
	public void create(Interface i, int idMateriel){
		String sql = "insert into interface (nom, mac, idtype, idmateriel) values (?, ?, ?, ?)";
		boolean isTransactionOk = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, i.getNom());
				stmt.setString(2, i.getMac());
				stmt.setInt(3, i.getType().getId());
				stmt.setInt(4, idMateriel);
				stmt.executeUpdate();
				try(ResultSet rs = stmt.getGeneratedKeys()){
					if(rs.next()) {
						i.setId(rs.getInt(1));
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
	
	public boolean update(Interface i, int idMateriel) {
		String sql = "update interface set nom = ?, mac = ?, idtype = ?, idmateriel = ? where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeUpdateSomething = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, i.getNom());
				stmt.setString(2, i.getMac());
				stmt.setInt(3, i.getType().getId());
				stmt.setInt(4, idMateriel);
				stmt.setInt(5, i.getId());
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
	 * Méthode pour supprimer une interface de la table interface
	 * @params l'id de l'interface
	 */
	public boolean delete(int id) {
		String sql = "delete from interface where id = ?";
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
