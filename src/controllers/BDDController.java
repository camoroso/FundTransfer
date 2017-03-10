package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BDDController {
	 	
	 	Statement statement;
	 	Connection cnx;
	 	
	 	public void connectToBDD() {
	 		String url = "";
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
	 		/* Cr�ation de l'objet g�rant les requ�tes */
	 		statement = cnx.createStatement();
	 		
	 		/* Ex�cution d'une requ�te de lecture */
	 		//ResultSet result = statement.executeQuery( "SELECT id, email, mot_de_passe, nom  FROM Utilisateur;" );
	 		ResultSet result = statement.executeQuery(query);
	 		
	 		/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
	 		while (result.next()) {
	 		    int idUtilisateur = result.getInt("id");
	 
	 		    /* Traiter ici les valeurs r�cup�r�es. */
	 		}
	 		
	 		return result;
	 	}
	 	
	 	public int queryInsert(String query) throws SQLException {
	 		/* Cr�ation de l'objet g�rant les requ�tes */
	 		statement = cnx.createStatement();
	 				
	 		/* Ex�cution d'une requ�te d'�criture */
	 		//int statut = statement.executeUpdate( "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) VALUES ('jmarc@mail.fr', MD5('lavieestbelle78'), 'jeanmarc', NOW());" );
	 		int statut = statement.executeUpdate(query);
	 		
	 		return statut;
	 	}
	 	
	 	public int queryUpdate(String query) throws SQLException {
	 		statement = cnx.createStatement();
	 		int statut = statement.executeUpdate(query);
	 		return statut;
	 	}
	 
	 }