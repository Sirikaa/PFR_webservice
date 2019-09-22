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
		List<AdresseIp> adressesIp = new ArrayList<AdresseIp>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from adresseip")){
				while(rs.next()) {
					adressesIp.add(buildIp(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return adressesIp;
	}
	
	public AdresseIp getAdresseIp(int id){
		AdresseIp ip = new AdresseIp();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from adresseip where id ="+id)){
				if(rs.next()) {
					ip = buildIp(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return ip;
	}
	
	/*
	 * Méthode qui retourne la liste des adresses ip d'une interface
	 * Prend en paramètre l'id de l'interface
	 */
	public List<AdresseIp> getAdresseIpOfInterface(int idInterface){
		List<AdresseIp> ips = new ArrayList<AdresseIp>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from adresseip where idinterface = "+idInterface)){
				while(rs.next()) {
					ips.add(buildIp(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return ips;
	}
	
	private AdresseIp buildIp(ResultSet rs) throws SQLException{
		TypeAffectationDao taDao = new TypeAffectationDao();
		AdresseIp ip =  new AdresseIp(rs.getInt("id"), rs.getString("ipV4"), rs.getString("ipV6"), rs.getString("masque"));
		ip.setTypeAffectation(taDao.getTypeAffectation(rs.getInt("idtypeaff")));
		return ip;
    }
}
