package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		return new Materiel(rs.getInt("id"), rs.getString("libelle"), rs.getString("numserie"), tmDao.getTypeMaterielOfMateriel(rs.getInt("id")), iDao.getInterfacesOfMateriel(rs.getInt("id")));
	}
	
	/*
	 * Méthode pour insérer un matériel dans la table materiel
	 * Prend un matériel en argument et l'id du client
	 */
	public void create(Materiel m, int idClient){
		String sql = "insert into materiel (libelle, numserie, idclient, idtype) values (?, ?, ?, ?)";
		boolean isTransactionOk = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, m.getLibelle());
				stmt.setString(2, m.getSerial());
				stmt.setInt(3, idClient);
				stmt.setInt(4, m.getType().getId());
				stmt.executeUpdate();
				try(ResultSet rs = stmt.getGeneratedKeys()){
					if(rs.next()) {
						m.setId(rs.getInt(1));
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
	
	public boolean update(Materiel m, int idClient) {
		String sql = "update materiel set libelle = ?, numserie = ?, idclient = ?, idtype = ? where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeUpdateSomething = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, m.getLibelle());
				stmt.setString(2, m.getSerial());
				stmt.setInt(3, idClient);
				stmt.setInt(4, m.getType().getId());
				stmt.setInt(5, m.getId());
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
	 * Méthode pour supprimer un materiel de la table materiel
	 * Prend l'id du matériel en paramètre
	 */
	public boolean delete(int id) {
		String sql = "delete from adresseip where idinterface in (select id from interface where idmateriel = ?);delete from interface where idmateriel = ?;delete from materiel where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeDeleteSomething;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql)) {
				stmt.setInt(1, id);
				stmt.setInt(2, id);
				stmt.setInt(3, id);
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
