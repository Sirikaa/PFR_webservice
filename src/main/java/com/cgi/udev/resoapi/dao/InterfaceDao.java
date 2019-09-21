package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.AdresseIp;
import com.cgi.udev.resoapi.model.Interface;
import com.cgi.udev.resoapi.model.TypeInterface;

public class InterfaceDao extends AbstractDao{
	
	/*
	 * Méthode pour récupérer la liste de toutes les interfaces dans la table Interface de la BDD
	 */
	public List<Interface> getAll(){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<Interface> interfaces = new ArrayList<Interface>();
			try(ResultSet rs = stmt.executeQuery("select * from interface")){
				while(rs.next()) {
					Interface i = getInterface(rs.getInt("id"));
					interfaces.add(i);
				}
			}
			return interfaces;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Interface getInterface(int id){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			Interface i = new Interface();
			try(ResultSet rs = stmt.executeQuery("select * from interface where id ="+id)){
				if(rs.next()) {
					i.setId(rs.getInt("id"));
					i.setNom(rs.getString("nom"));
					i.setMac(rs.getString("mac"));
					i.setType(getTypeInterface(rs.getInt("idtype")));
					i.setAdresseIp(getAdresseIp(rs.getInt("id")));
				}
			}
			return i;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public TypeInterface getTypeInterface(int idInterface) {
		TypeInterfaceDao tiDao = new TypeInterfaceDao();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			TypeInterface ti = new TypeInterface();
			try(ResultSet rs = stmt.executeQuery("select idtype from interface where id ="+idInterface)){
				if(rs.next()) {
					ti = tiDao.getTypeInterface(rs.getInt("idtype"));
				}
				return ti;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public AdresseIp getAdresseIp(int idInterface) {
		AdresseIpDao ipDao = new AdresseIpDao();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			AdresseIp ip = new AdresseIp();
			try(ResultSet rs = stmt.executeQuery("select id from adresseip where idinterface ="+idInterface)){
				if(rs.next()) {
					ip = ipDao.getAdresseIp(rs.getInt("id"));
				}
				return ip;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
