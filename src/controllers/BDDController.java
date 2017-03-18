package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BDDController {
	 	
	 	public BDDController() throws ClassNotFoundException {
	 		connectToBDD();
	 	}

		Statement statement;
	 	Connection cnx;
	 	
	 	//Connexion a la base de donnees en local root pas de mdp
	 	public Connection connectToBDD() throws ClassNotFoundException {
	 		
	 		Class.forName("com.mysql.jdbc.Driver");
	 		
	 		String url = "jdbc:mysql://localhost:3306/fund_transfer";
	 		String user = "root";
	 		String pwd = "";
	 		cnx = null;
	 		
	 		try {
	 		    cnx = DriverManager.getConnection(url, user, pwd);
	 		} catch (SQLException e) {
	 			e.printStackTrace();
	 		}
	 		
	 		return cnx;
	 	}
	 	
	 	//Selection d'un object en base
	 	public ResultSet querySelect(String query) throws SQLException {
	 		// Création de l'objet gérant les requêtes
	 		statement = cnx.createStatement();
	 		
	 		// Exécution d'une requête de lecture
	 		ResultSet result = statement.executeQuery(query);
	 		
	 		return result;
	 	}
	 	
	 	//Modification ou insertion en base
	 	public int queryUpdate(String query) throws SQLException {
	 		// Création de l'objet gérant les requêtes
	 		statement = cnx.createStatement();
	 				
	 		// Exécution d'une requête d'écriture
	 		int statut = statement.executeUpdate(query);
	 		
	 		return statut;
	 	}
	 	
	 	//Insertion des donnes de test
	 	public void insertData() throws SQLException, URISyntaxException, FileNotFoundException {
	 		//On vide la base en premier
	 		dropDB();
	 		
	 		//Fichier contenant les jeux de test
	 		BufferedReader br = new BufferedReader(new FileReader("./data.txt"));
	 	    
	 	    PreparedStatement pstmt = null;
	 	    Connection conn = null;
	 	    int id;
	 	    String lastname, firstname, cardNumber, expirationMonth, expirationYear, cvv, query;
	 	    try {
	 	      conn = this.connectToBDD();
	 	      
	 	      String line = null;
	 	      
	 	      //On lit le fichier data.txt pour inserer en base
	 	      while((line=br.readLine()) != null) {
	 	    	  String tmp[]=line.split(",");
	 	    	  id=Integer.parseInt(tmp[0]);
			 	  lastname=tmp[1];
			 	  firstname=tmp[2];
			 	  cardNumber=tmp[3];
			 	  expirationMonth=tmp[4];
			 	  expirationYear=tmp[5];
			 	  cvv=tmp[6];
			 	  
			 	  //Insertion des clients
			 	  query = "INSERT INTO consumer VALUES("+ id + ",'" +firstname+"','"+lastname+"')";
			 	 
			 	  pstmt = conn.prepareStatement(query);
			 	  pstmt.executeUpdate();
			 	 
			 	  
			 	  //Insertion des comptes
			 	  query = "INSERT INTO account (card_number, expiration_month, expiration_year, cvv, remain_balance, consumer_id) "
		 				+ "VALUES ('"+cardNumber+"','"+expirationMonth+"','"+expirationYear+"','"+cvv+"',300,"+id+")";
			 	
			 	  pstmt = conn.prepareStatement(query);
			 	  pstmt.executeUpdate();
	 	      }
	 	      
	 	      
	 	    } catch (Exception e) {
	 	      System.err.println("Error: " + e.getMessage());
	 	      e.printStackTrace();
	 	    }
	 	  }
	 	
	 	//On vide les 3 tables de la base
	 	public void dropDB() throws SQLException {
	 		Statement stmt = cnx.createStatement();
	 		String[] tables = {"account", "consumer", "transaction"};
	 		for (int i=0; i<3;i++) {
	 			String query = "DELETE FROM " + tables[i];
	 			stmt.executeUpdate(query);
	 		}
	 	}
	}