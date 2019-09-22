package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
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
		Interface i = new Interface(rs.getInt("id"), rs.getString("nom"), rs.getString("mac"));
		i.setType(tiDao.getTypeInterfaceOfInterface(rs.getInt("id")));
		i.setAdressesIp(ipDao.getAdresseIpOfInterface(rs.getInt("id")));
		return i;
	}
}
