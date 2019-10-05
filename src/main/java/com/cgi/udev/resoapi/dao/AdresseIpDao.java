package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.AdresseIp;

public class AdresseIpDao extends AbstractDao{
	InterfaceDao iDao = new InterfaceDao();
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
		return  new AdresseIp(rs.getInt("id"), rs.getString("ipV4"), rs.getString("ipV6"), rs.getString("masque"), taDao.getTypeAffectation(rs.getInt("idtypeaff")));
    }
	
	/*
	 * Méthode pour insérer une adresse IP dans la table adresseip
	 * Prend une adresse ip en argument et l'id de l'interface
	 */
	public void create(AdresseIp ip, int idInterface){
		String sql = "insert into adresseip (ipV4, ipV6, masque, idinterface, idtypeaff) values (?, ?, ?, ?, ?)";
		boolean isTransactionOk = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, ip.getIpv4());
				stmt.setString(2, ip.getIpv6());
				stmt.setString(3, ip.getMasque());
				stmt.setInt(4, idInterface);
				stmt.setInt(5, ip.getTypeAffectation().getId());
				stmt.executeUpdate();
				try(ResultSet rs = stmt.getGeneratedKeys()){
					if(rs.next()) {
						ip.setId(rs.getInt(1));
					}
				}
				isTransactionOk = true;
			}finally {
				checkTransactionAndClose(connexion, isTransactionOk);
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean update(AdresseIp ip, int idInterface) {
		String sql = "update adresseip set ipV4 = ?, ipV6 = ?, masque = ?, idinterface = ? where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeUpdateSomething = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, ip.getIpv4());
				stmt.setString(2, ip.getIpv6());
				stmt.setString(3, ip.getMasque());
				stmt.setInt(4, idInterface);
				stmt.setInt(5, ip.getId());
				if(stmt.executeUpdate() > 0) {
					haveWeUpdateSomething = true;
				}else {
					haveWeUpdateSomething = false;
				}
				isTransactionOk = true;
			}finally {
				checkTransactionAndClose(connexion, isTransactionOk);
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return haveWeUpdateSomething;
	}
	
	/*
	 * Méthode pour supprimer une interface de la table interface
	 * @params l'id de l'interface
	 */
	public boolean delete(int id) {
		String sql = "delete from adresseip where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeDeleteSomething;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql)) {
				stmt.setInt(1, id);
				if(stmt.executeUpdate() > 0) {
					haveWeDeleteSomething = true;
				}else {
					haveWeDeleteSomething = false;
				}
				isTransactionOk = true;
			}finally {
				checkTransactionAndClose(connexion, isTransactionOk);
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return haveWeDeleteSomething;
	}
}
