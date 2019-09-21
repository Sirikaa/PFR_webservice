package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.AdresseIp;
import com.cgi.udev.resoapi.model.TypeAffectation;

public class AdresseIpDao extends AbstractDao{
	
	/*
	 * Méthode pour récupérer la liste de tous les clients dans la table Client de la BDD
	 */
	public List<AdresseIp> getAll(){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<AdresseIp> adressesIp = new ArrayList<AdresseIp>();
			try(ResultSet rs = stmt.executeQuery("select * from adresseip")){
				while(rs.next()) {
					AdresseIp ip = getAdresseIp(rs.getInt("id"));
					adressesIp.add(ip);
				}
			}
			return adressesIp;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public AdresseIp getAdresseIp(int id){
		AdresseIp ip = new AdresseIp();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			
			try(ResultSet rs = stmt.executeQuery("select * from adresseip where id ="+id)){
				if(rs.next()) {
					ip.setId(id);
					ip.setIpv4(rs.getString("ipV4"));
					ip.setIpv6(rs.getString("ipV6"));
					ip.setMasque(rs.getString("masque"));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		ip.setTypeAffectation(getTypeAffectation(id));
		return ip;
	}
	
	public TypeAffectation getTypeAffectation(int idAdresseIp) {
		TypeAffectationDao taDao = new TypeAffectationDao();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
			Statement stmt = connexion.createStatement()){
			TypeAffectation ta = new TypeAffectation();
			try(ResultSet rs = stmt.executeQuery("select idtypeaff from adresseip where id ="+idAdresseIp)){
				if(rs.next()) {
					ta = taDao.getTypeAffectation(rs.getInt("idtypeaff"));
				}
				return ta;
			}		
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
