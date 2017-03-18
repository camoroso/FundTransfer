package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	 	
	 	public void insertData() throws SQLException, FileNotFoundException {
	 	    BufferedReader br = new BufferedReader(new FileReader("FundTransfer/src/data.txt"));
	 	    PreparedStatement pstmt = null;
	 	    Connection conn = null;
	 	    int id;
	 	    String lastname, firstname, cardNumber, expirationMonth, expirationYear, cvv, query;
	 	    try {
	 	      conn = this.connectToBDD();

	 	      String line = null;
	 	      while((line=br.readLine()) != null) {
	 	    	  String tmp[]=line.split(",");
	 	    	  id=Integer.parseInt(tmp[0]);
			 	  lastname=tmp[1];
			 	  firstname=tmp[2];
			 	  cardNumber=tmp[3];
			 	  expirationMonth=tmp[4];
			 	  expirationYear=tmp[5];
			 	  cvv=tmp[6];
			 	  
			 	 query = "INSERT INTO consumer VALUES("+ id + ",'" +firstname+"','"+lastname+"')";
			 	 this.queryInsert(query);
			 	 
			 	 conn.prepareStatement(query);
			 	 pstmt.executeUpdate();
			 	 
			 	 query = "INSERT INTO account (card_number, expiration_month, expiration_year, cvv, remain_balance, consumer_id) "
		 				+ "VALUES ('"+cardNumber+"','"+expirationMonth+"','"+expirationYear+"','"+cvv+"',300,"+id+")";
			 	
			 	 conn.prepareStatement(query);
			 	 pstmt.executeUpdate();
	 	      }
	 	      
	 	      
	 	    } catch (Exception e) {
	 	      System.err.println("Error: " + e.getMessage());
	 	      e.printStackTrace();
	 	    } finally {
	 	      pstmt.close();
	 	      conn.close();
	 	    }
	 	  }
	}