package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.Interface;
import com.cgi.udev.resoapi.model.Materiel;
import com.cgi.udev.resoapi.model.TypeMateriel;

public class MaterielDao extends AbstractDao{

	/*
	 * Méthode pour récupérer la liste de tous les materiels dans la table Materiel de la BDD
	 */
	public List<Materiel> getAll(){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<Materiel> materiels = new ArrayList<Materiel>();
			try(ResultSet rs = stmt.executeQuery("select * from materiel")){
				while(rs.next()) {
					Materiel m = getMateriel(rs.getInt("id"));
					materiels.add(m);
				}
			}
			return materiels;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Materiel getMateriel(int id){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			Materiel m = new Materiel();
			try(ResultSet rs = stmt.executeQuery("select * from materiel where id ="+id)){
				if(rs.next()) {
					m.setId(rs.getInt("id"));
					m.setLibelle(rs.getString("libelle"));
					m.setSerial(rs.getString("numserie"));
					m.setType(getTypeMateriel(rs.getInt("idtype")));
					m.setInterfaces(getInterfaces(rs.getInt("id")));
				}
			}
			return m;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public TypeMateriel getTypeMateriel(int idMateriel) {
		TypeMaterielDao tmDao = new TypeMaterielDao();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
		TypeMateriel tm = new TypeMateriel();
			try(ResultSet rs = stmt.executeQuery("select idtype from materiel where id ="+idMateriel)){
				if(rs.next()) {
					tm = tmDao.getTypeMateriel(rs.getInt("idtype"));
				}
				return tm;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Interface> getInterfaces(int idMateriel) {
		InterfaceDao iDao = new InterfaceDao();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
			Statement stmt = connexion.createStatement()){
			List<Interface> interfaces = new ArrayList<Interface>();
			try(ResultSet rs = stmt.executeQuery("select id from interface where idmateriel ="+idMateriel)){
				while(rs.next()) {
					Interface i = iDao.getInterface(rs.getInt("id"));
					interfaces.add(i);
				}
				return interfaces;
			}		
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
