package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
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
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<TypeInterface> tis = new ArrayList<TypeInterface>();
			try(ResultSet rs = stmt.executeQuery("select * from typeif")){
				while(rs.next()) {
					TypeInterface ti = new TypeInterface(rs.getInt("id"), rs.getString("libelle"));
					tis.add(ti);
				}
			}
			return tis;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public TypeInterface getTypeInterface(int id){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			TypeInterface ti = new TypeInterface();
			try(ResultSet rs = stmt.executeQuery("select * from typeif where id ="+id)){
				if(rs.next()) {
					ti.setId(rs.getInt("id"));
					ti.setLibelle(rs.getString("libelle"));
				}
			}
			return ti;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
