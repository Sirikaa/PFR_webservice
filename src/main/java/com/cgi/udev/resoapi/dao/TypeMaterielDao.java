package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.TypeMateriel;

public class TypeMaterielDao extends AbstractDao{
	/*
	 * Méthode pour récupérer la liste de tous les clients dans la table Client de la BDD
	 */
	public List<TypeMateriel> getAll(){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<TypeMateriel> tms = new ArrayList<TypeMateriel>();
			try(ResultSet rs = stmt.executeQuery("select * from typemateriel")){
				while(rs.next()) {
					TypeMateriel tm = new TypeMateriel(rs.getInt("id"), rs.getString("libelle"));
					tms.add(tm);
				}
			}
			return tms;
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public TypeMateriel getTypeMateriel(int id){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			TypeMateriel tm = new TypeMateriel();
			try(ResultSet rs = stmt.executeQuery("select * from typemateriel where id ="+id)){
				if(rs.next()) {
					tm.setId(rs.getInt("id"));
					tm.setLibelle(rs.getString("libelle"));
				}
			}
			return tm;
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
}
