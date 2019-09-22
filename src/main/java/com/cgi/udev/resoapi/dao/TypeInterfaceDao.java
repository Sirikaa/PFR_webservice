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
					buildTypeInterface(rs);
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
}
