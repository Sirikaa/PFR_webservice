package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
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
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<TypeAffectation> tas = new ArrayList<TypeAffectation>();
			try(ResultSet rs = stmt.executeQuery("select * from typeaffectation")){
				while(rs.next()) {
					TypeAffectation ta = new TypeAffectation(rs.getInt("id"), rs.getString("libelle"));
					tas.add(ta);
				}
			}
			return tas;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public TypeAffectation getTypeAffectation(int id){
		TypeAffectation ta = new TypeAffectation();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from typeaffectation where id ="+id)){
				if(rs.next()) {
					ta.setId(id);
					ta.setLibelle(rs.getString("libelle"));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return ta;
	}
}
