package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.Materiel;

public class MaterielDao extends AbstractDao{

	/*
	 * Méthode pour récupérer la liste de tous les matériels dans la table Materiel de la BDD
	 */
	public List<Materiel> getAll(){
		List<Materiel> materiels = new ArrayList<Materiel>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from materiel")){
				while(rs.next()) {
					materiels.add(buildMateriel(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return materiels;
	}
	
	public Materiel getMateriel(int id){
		Materiel m = new Materiel();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from materiel where id ="+id)){
				if(rs.next()) {
					m = buildMateriel(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return m;
	}
	
	/*
	 * Méthode qui retourne la liste de tous les contacts d'un client
	 */
	public List<Materiel> getMaterielsOfClient(int idClient){
		List<Materiel> materiels = new ArrayList<Materiel>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from materiel where idclient ="+idClient)){
				while(rs.next()) {
					materiels.add(buildMateriel(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return materiels;
	}
	
	private Materiel buildMateriel (ResultSet rs) throws SQLException{
		TypeMaterielDao tmDao = new TypeMaterielDao();
		InterfaceDao iDao = new InterfaceDao();
		Materiel  m =  new Materiel(rs.getInt("id"), rs.getString("libelle"), rs.getString("numserie"));
		m.setType(tmDao.getTypeMaterielOfMateriel(rs.getInt("id")));
		m.setInterfaces(iDao.getInterfacesOfMateriel(rs.getInt("id")));
		return m;
	}
}
