package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.AdresseIp;

public class AdresseIpDao extends AbstractDao{
	
	/*
	 * Méthode pour récupérer la liste de toutes les adresses IP dans la table AdresseIp de la BDD
	 */
	public List<AdresseIp> getAll(){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<AdresseIp> adressesIp = new ArrayList<AdresseIp>();
			try(ResultSet rs = stmt.executeQuery("select * from adresseip")){
				while(rs.next()) {
					adressesIp.add(buildIp(rs));
				}
			}
			return adressesIp;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public AdresseIp getAdresseIp(int id){
		TypeAffectationDao taDao = new TypeAffectationDao();
		AdresseIp ip = new AdresseIp();
		int idTypeAff = 0;
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from adresseip where id ="+id)){
				if(rs.next()) {
					ip = buildIp(rs);
					idTypeAff = rs.getInt("idtypeaff");
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		ip.setTypeAffectation(taDao.getTypeAffectation(idTypeAff));
		return ip;
	}
	
	private AdresseIp buildIp(ResultSet rs) throws SQLException{
		return new AdresseIp(rs.getInt("id"), rs.getString("ipV4"), rs.getString("ipV6"), rs.getString("masque"));
    }
}
