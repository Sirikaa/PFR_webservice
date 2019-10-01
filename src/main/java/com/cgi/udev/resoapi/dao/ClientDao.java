package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cgi.udev.resoapi.model.Client;

public class ClientDao extends AbstractDao{
	
	/*
	 * Méthode pour récupérer la liste de tous les clients dans la table Client de la BDD
	 */
	public List<Client> getAll(){
		List<Client> clients = new ArrayList<Client>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from client")){
				while(rs.next()) {
					clients.add(buildClient(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return clients;
	}
	
	public Client getClient(int id){
		Client c = new Client();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from client where id ="+id)){
				if(rs.next()) {
					c = buildClient(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return c;
	}
	
	private Client buildClient(ResultSet rs) throws SQLException{
		PersonneDao pDao = new PersonneDao();
		MaterielDao mDao = new MaterielDao();
		VilleDao vDao = new VilleDao();
		return new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("matricule"), rs.getString("password"), rs.getString("adresse1"), rs.getString("adresse2"), vDao.getVilleOfClient(rs.getInt("id")), pDao.getPersonnesOfClient(rs.getInt("id")), mDao.getMaterielsOfClient(rs.getInt("id")));
	}
	
	public void create(Client c) {
		String sql = "";
		boolean isCpVilleSet = false;
		if(c.getVille() != null) {
			sql = "insert into client (nom, matricule, password, adresse1, adresse2, idcpville) values (?, ?, ?, ?, ?, ?)";
			isCpVilleSet = true;
		}else {
			sql = "insert into client (nom, matricule, password, adresse1, adresse2) values (?, ?, ?, ?, ?)";
			isCpVilleSet = false;
		}
		boolean isTransactionOk = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, c.getNom());
				stmt.setString(2, c.getMatricule());
				stmt.setString(3, c.getPassword());
				stmt.setString(4, c.getAdresse1());
				stmt.setString(5, c.getAdresse2());
				if(isCpVilleSet) {
					stmt.setInt(6, c.getVille().getId());
				}
				stmt.executeUpdate();
				try(ResultSet rs = stmt.getGeneratedKeys()){
					if(rs.next()) {
						c.setId(rs.getInt(1));
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
	
	public boolean update(Client c) {
		String sql = "";
		boolean isCpVilleSet = false;
		if(c.getVille() != null) {
			sql = "update client set nom = ?, matricule = ?, password = ?, adresse1 = ?, adresse2 = ?, idcpville = ? where id = ?";
			isCpVilleSet = true;
		}else {
			sql = "update client set nom = ?, matricule = ?, password = ?, adresse1 = ?, adresse2 = ? where id = ?";
			isCpVilleSet = false;
		}
		boolean isTransactionOk = false;
		boolean haveWeUpdateSomething = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, c.getNom());
				stmt.setString(2, c.getMatricule());
				stmt.setString(3, c.getPassword());
				stmt.setString(4, c.getAdresse1());
				stmt.setString(5, c.getAdresse2());
				if(isCpVilleSet) {
					stmt.setInt(6, c.getVille().getId());
					stmt.setInt(7, c.getId());
				}else {
					stmt.setInt(6, c.getId());
				}
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
	 * Méthode pour supprimer un client de la table client
	 * Prend un client en paramètre
	 */
	public boolean delete(int id) {
		String deleteIp = "delete from adresseip where idinterface in (select id from interface where idmateriel in (select id from materiel where idclient = ?));";
		String deleteInterface = "delete from interface where idmateriel in (select id from materiel where idclient = ?);";
		String deleteMateriel = "delete from materiel where idclient = ?;";
		String deleteAppartient = "delete from appartient where idclient = ?;";
		String deleteClient = "delete from client where id = ?";
		String sql = deleteIp+deleteInterface+deleteMateriel+deleteAppartient+deleteClient;
		boolean isTransactionOk = false;
		boolean haveWeDeleteSomething;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql)) {
				stmt.setInt(1, id);
				stmt.setInt(2, id);
				stmt.setInt(3, id);
				stmt.setInt(4, id);
				stmt.setInt(5, id);
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
	
	/*
	 * Méthode qui vérifie si une personne existe dans la table personne
	 * prend en paramètre un matricule et un password
	 * Utilisé pour l'authentification
	 * Retourne l'objet personne
	 */
	public Client login(String matricule, String password) {
		String sql = "select * from client where matricule = ? and password = ?";
		Client c = new Client();
		try(Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql)){
				stmt.setString(1, matricule);
				stmt.setString(2, password);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						c = buildClient(rs);
					}
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return c;
	}
}
