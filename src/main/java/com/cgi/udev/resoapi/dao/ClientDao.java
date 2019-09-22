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
					buildClient(rs);
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
		String sql = "insert into client (nom, matricule, password, adresse1, adresse2, idcpville) values (?, ?, ?, ?, ?, ?)";
		boolean isTransactionOk = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, c.getNom());
				stmt.setString(2, c.getMatricule());
				stmt.setString(3, c.getPassword());
				stmt.setString(4, c.getAdresse1());
				stmt.setString(5, c.getAdresse2());
				stmt.setInt(6, c.getVille().getId());
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
}
