package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDao {

	/*
	 * Méthode pour vérifier qu'une requête a bien été effectuée entièrement avant de fermer la connexion à la BDD
	 * Si la transaction a réussi, un commit a lieu
	 * Si la transaction a échoué, un rollback a lieu
	 */
	protected void checkTransactionAndClose(Connection connection, boolean transactionOk) {
		try {
			if (connection !=null) {
				connection.setAutoCommit(false);
				if (transactionOk) {
					connection.commit();
				} else {
					connection.rollback();
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
