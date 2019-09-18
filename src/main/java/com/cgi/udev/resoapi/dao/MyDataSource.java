package com.cgi.udev.resoapi.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
* Un singleton pour récupérer une connexion à une base de données.
* Exemple d'utilisation :
*
* Connection c = MyDataSource.getSingleton().getConnection();
*
*/
public class MyDataSource {

	private static final String DATASOURCE_NAME = "bdd/reso";
	private static MyDataSource singleton = new MyDataSource();

	public static MyDataSource getSingleton() {
		return singleton;
	}

	private DataSource dataSource;

	private MyDataSource() {
		try {
			Context envContext = InitialContext.doLookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

}
