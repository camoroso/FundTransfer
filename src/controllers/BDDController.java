package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BDDController {
	 	
	 	public BDDController() throws ClassNotFoundException {
	 		connectToBDD();
	 	}

		Statement statement;
	 	Connection cnx;
	 	
	 	public void connectToBDD() throws ClassNotFoundException {
	 		
	 		Class.forName("com.mysql.jdbc.Driver");
	 		
	 		String url = "jdbc:mysql://10.31.0.109:3306/fund_transfer";
	 		String user = "root";
	 		String pwd = "";
	 		cnx = null;
	 		try {
	 		    cnx = DriverManager.getConnection(url, user, pwd);
	 		} catch (SQLException e) {
	 			e.printStackTrace();
	 		}
	 	}
	 	
	 	public ResultSet querySelect(String query) throws SQLException {
	 		// Création de l'objet gérant les requêtes
	 		statement = cnx.createStatement();
	 		
	 		// Exécution d'une requête de lecture
	 		ResultSet result = statement.executeQuery(query);
	 		
	 		return result;
	 	}
	 	
	 	public int queryInsert(String query) throws SQLException {
	 		// Création de l'objet gérant les requêtes
	 		statement = cnx.createStatement();
	 				
	 		// Exécution d'une requête d'écriture
	 		int statut = statement.executeUpdate(query);
	 		
	 		return statut;
	 	}
	 	
	 	public int queryUpdate(String query) throws SQLException {
	 		// Création de l'objet gérant les requêtes
	 		statement = cnx.createStatement();
	 		
	 		// Exécution d'une requête d'écriture
	 		int statut = statement.executeUpdate(query);
	 		return statut;
	 	}
	 
	 }