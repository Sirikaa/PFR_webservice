package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.cgi.udev.resoapi.model.Personne;

/*
 * Data Access Object de la table Personne
 * AbstractDao contient des méthodes utiles à tous les DAO
 */
public class PersonneDao extends AbstractDao {

	/*
	 * Méthode qui retourne la liste de toutes les personnes de la table personne
	 */
	public List<Personne> getAll(){
		List<Personne> personnes = new ArrayList<Personne>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from personne")){
				while(rs.next()) {
					personnes.add(buildPersonne(rs));
					}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return personnes;
	}
	
	/*
	 * Méthode qui retourne une personne grâce à son paramètre id
	 */
	public Personne getPersonne(int id){
		Personne p = new Personne();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from personne where id ="+id)){
				if(rs.next()) {
					p = buildPersonne(rs);
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return p;
	}
	
	/*
	 * Méthode qui retourne la liste de tous les contacts d'un client
	 */
	public List<Personne> getPersonnesOfClient(int idClient){
		List<Personne> personnes = new ArrayList<Personne>();
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from personne p " + 
												 "left join appartient a " + 
												 "on p.id = a.idpersonne " + 
												 "left join client c " + 
												 "on c.id = a.idclient " + 
												 "where idclient = "+idClient)){
				while(rs.next()) {
					personnes.add(buildPersonne(rs));
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return personnes;
	}
	
	/*
	 * Méthode pour insérer une personne dans la table personne
	 * Prend une personne en argument
	 */
	public void create(Personne p){
		String sql = "insert into personne (nom, prenom, telephone, email) values (?, ?, ?, ?)";
		boolean isTransactionOk = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, p.getNom());
				stmt.setString(2, p.getPrenom());
				stmt.setString(3, p.getTelephone());
				stmt.setString(4, p.getEmail());
				stmt.executeUpdate();
				try(ResultSet rs = stmt.getGeneratedKeys()){
					if(rs.next()) {
						p.setId(rs.getInt(1));
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
	
	public boolean update(Personne p) {
		String sql = "update personne set nom = ?, prenom = ?, telephone = ?, email = ? where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeUpdateSomething = false;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, p.getNom());
				stmt.setString(2, p.getPrenom());
				stmt.setString(3, p.getTelephone());
				stmt.setString(4, p.getEmail());
				stmt.setInt(5, p.getId());
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
	 * Méthode pour supprimer une personne de la table personne
	 * Prend une personne en paramètre
	 */
	public boolean delete(int id) {
		String sql = "delete from personne where id = ?";
		boolean isTransactionOk = false;
		boolean haveWeDeleteSomething;
		try (Connection connexion = MyDataSource.getSingleton().getConnection()){
			try(PreparedStatement stmt = connexion.prepareStatement(sql)) {
				stmt.setInt(1, id);
				stmt.setInt(2, id);
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
	
	private Personne buildPersonne(ResultSet rs) throws SQLException{
		return new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"));
	}
}
