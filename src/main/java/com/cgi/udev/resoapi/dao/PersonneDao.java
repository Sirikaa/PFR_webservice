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
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			List<Personne> personnes = new ArrayList<Personne>();
			try(ResultSet rs = stmt.executeQuery("select * from personne")){
				while(rs.next()) {
					Personne p = new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"));
					if(rs.getString("telephone") != null) {
						p.setTelephone(rs.getString("telephone"));
					}
					personnes.add(p);
				}
			}
			return personnes;
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
	
	/*
	 * Méthode qui retourne une personne grâce à son paramètre id
	 */
	public Personne getPersonneById(int id){
		try(Connection connexion = MyDataSource.getSingleton().getConnection();
				Statement stmt = connexion.createStatement()){
			Personne p = new Personne();
			try(ResultSet rs = stmt.executeQuery("select * from personne where id ="+id)){
				while(rs.next()) {
					p.setId(rs.getInt("id"));
					p.setNom(rs.getString("nom"));
					p.setPrenom(rs.getString("prenom"));
					p.setTelephone(rs.getString("telephone"));
					p.setEmail(rs.getString("email"));
				}
			}
			return p;
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public void create(Personne p){
		String sql = "insert into personne (nom, prenom, telephone, email) values (?, ?, ?, ?)";
		/*try (Connection connexion = MyDataSource.getSingleton().getConnection();){
			boolean isTransactionOk = false;
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
	}*/
		try {
			boolean transactionOk = false;
			Connection connexion = MyDataSource.getSingleton().getConnection();
			try (PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, p.getNom());
				stmt.setString(2, p.getPrenom());
				stmt.setString(3, p.getTelephone());
				stmt.setString(4, p.getEmail());
				stmt.executeUpdate();
				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						p.setId(rs.getInt(1));
					}
				}
				transactionOk = true;
			} finally {
				checkTransactionAndClose(connexion, transactionOk);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
