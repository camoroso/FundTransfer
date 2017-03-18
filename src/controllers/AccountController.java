package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import models.Account;
import models.Consumer;

public class AccountController {
	 	
	 	BDDController bddControl;
	 	ResultSet results;
	 	int result;
	 	Account account;
	 	static Random  rnd;
	 	
	 	public AccountController() throws ClassNotFoundException {
			this.bddControl = new BDDController();
			rnd = new Random();
		}
	 	
		//On debite le compte du montant passe en parametre
	 	public int debitAccount(Account account, int amount) throws SQLException {
	 		int result = 0;
	 		
	 		//Si la condition du 5 fois superieur est validee (voir fonction isTheAmountOk)
	 		if (this.isTheAmountOk(account, amount)) {
	 			String query = "UPDATE account SET remain_balance = remain_balance - " + amount + 
		 				" WHERE card_number = '" + account.getCardNumber() + "'";
		 		result = bddControl.queryUpdate(query);
		 		
		 		//On affiche à l'utilisateur le résultat
		 		System.out.println("Le solde du compte a ete debite de " + amount + "€.");
		 		
		 		result = this.insertTransaction(account, amount);
	 		} else {
	 			System.out.println("Ouuuups .. Solde insuffisant !");
	 		}
	 		return result;
	 	}
	 	
	 	//On vrifie que le compte ait un montant suffisant avant la transaction
	 	public boolean isTheAmountOk(Account account, int amount) throws SQLException {
	 		boolean isOk = false;
	 		String query = "SELECT remain_balance FROM account WHERE card_number = '" + account.getCardNumber() + "'";
	 		ResultSet results = bddControl.querySelect(query);
	 		float balance = 0;
	 		
	 		//On rcupre le montant du compte
	 		while (results.next()) {
	 			balance = results.getFloat(1);
	 		}
	 		
	 		//Si le montant du compte est 5 fois suprieur au montant de la transaction, on valide
	 		if (balance >= (amount * 5)) {
	 			isOk = true;
	 		}

	 		return isOk;
	 	}
	 	
	 	//On vrifie que la carte entree soit valide (chiffres uniquement)
	 	public boolean isOnlyNumerics(String cardNumber) {
	 		boolean isOk = false;
	 		//Regex pour les chiffres uniquement
	 		String regex = "[0-9]+";
	 		if (cardNumber != null && cardNumber.matches(regex)) {
	 			isOk = true;
	 			//Longueur de 14 max
	 		} else if (cardNumber != null && cardNumber.length()!=15) {
	 			isOk = false;
	 			System.out.println("Format de carte incorrect !");
	 		}
	 		return isOk;
	 	}
	 	
	 	//On verifie que la date d'expiration soit valide
	 	public boolean verifyExpirationDate(String expirationMonth, String expirationYear) {
	 		boolean isOk = false;
	 		int month = 0, year = 0;
	 		
	 		//Si l'annee a ete donnee au format YYYY, on tronque les 2 derniers chiffres
	 		if (expirationYear != null && expirationYear.length() > 2) {
	 			expirationYear = expirationYear.substring(expirationYear.length()-2, expirationYear.length());
	 		}
	 		
	 		try {
	 			month = Integer.parseInt(expirationMonth);
		 		year = Integer.parseInt(expirationYear);
	 		} catch (NumberFormatException e) {
	 			e.getMessage();
	 			return false;
	 		}
	 		
	 		//On recupere la date courante
	 		DateFormat dateFormat = new SimpleDateFormat("yy/MM");
	 		Date date = new Date();
	 		String currentDate = dateFormat.format(date);
	 		
	 		int currentMonth = Integer.parseInt(currentDate.split("/")[1]);
	 		int currentYear = Integer.parseInt(currentDate.split("/")[0]);
	 		
	 		//On verifie que le mois entre soit juste
	 		if (month > 0 && month < 13) {
	 			isOk = true;
	 		}
	 		
	 		//On vrifie que l'annee ne soit pas depassee
	 		if (year > currentYear) {
	 			isOk = true;
	 			//Ou que si c'est l'annee en cours, le mois ne soit pas depasse
	 		} else if (year == currentYear) {
	 			if (month == currentMonth) {
	 				isOk = false;
	 			} else {
	 				isOk = true;
	 			}
	 		} else {
	 			isOk = false;
	 		}
	 		
	 		return isOk;
	 	}
	 	
	 	//On verifie que la carte soit bien enregistree en base
	 	public boolean verifyCard(String cardNumber) throws SQLException {
	 		boolean isOk = false;
	 		
	 		String query = "SELECT * FROM account WHERE card_number = '" + cardNumber + "'";
	 		results = bddControl.querySelect(query);
	 		if (results.next()) {
	 			isOk = true;
	 		} 
	 		
	 		return isOk;
	 	}
	 	
	 	//On verifie que toutes les donnees du compte sont en coherence avec celles enregistrees en base
	 	public boolean verifyAccount(Account account) throws SQLException {
	 		boolean isOk = false;
	 		
	 		if (account != null && account.getCardNumber() != null) {
	 			//Si le 0 en debut de mois a ete tronque, on le rajoute
		 		if (account.getExpirationMonth().length()==1) {
		 			account.setExpirationMonth("0"+account.getExpirationMonth());
		 		}
		 		
		 		String query = "SELECT * FROM account WHERE card_number = '" + account.getCardNumber() + "'"
		 				+ " AND expiration_month = '" + account.getExpirationMonth() + "'"
		 						+ " AND expiration_year = '" + account.getExpirationYear().trim() + "'"
		 								+ " AND cvv = '" + account.getCVV() + "'";
		 		results = bddControl.querySelect(query);
		 		
		 		if (results.next()) {
		 			isOk = true;
		 		} 
	 		}
	 		
	 		return isOk;
	 	}
	 	
	 	//On verifie que le cvv soit correcte
	 	public boolean verifyCvv(String cvv) {
	 		boolean isOk = false;
	 		String regex="[0-9]+";
	 		//Le cvv doit etre compose de 3 chiffres
	 		if (cvv.length()<3 || cvv.matches(regex)){
	 			isOk = true;
	 		} else {
	 			System.out.println("Format de CVV incorrect !");
	 		}
	 		return isOk;
	 	}
	 	
	 	//On ajoute la transaction en base
	 	public int insertTransaction(Account account, int amount) throws SQLException {
	 		int result = 0; 
	 		Consumer consumer = this.retrieveConsumer(account);
	 		if (consumer != null) {
	 			String query = "INSERT INTO transaction (consumer_id, account_id, balance) VALUES ('"+consumer.getId()+"','"+account.getCardNumber()+"',"+amount+")";
		 		result = bddControl.queryUpdate(query);
	 		}
	 		return result;
	 	}
	 	
	 	//On cherche le client associe au compte
	 	public Consumer retrieveConsumer(Account account) throws SQLException {
	 		if (account != null) {
	 			Consumer consumer = new Consumer();
	 			String query = "SELECT consumer.id, consumer.last_name, consumer.first_name "
		 				+ "FROM consumer, account "
		 				+ "WHERE account.consumer_id = consumer.id "
		 				+ "AND account.card_number = '" + account.getCardNumber() + "' "
		 						+ "AND account.expiration_month = '" + account.getExpirationMonth() + "' "
		 								+ "AND account.expiration_year = '" + account.getExpirationYear() + "' "
		 										+ " AND account.cvv = '" + account.getCVV() + "' "; 
		 		ResultSet results = bddControl.querySelect(query);
		 		
		 		//S'il n'y a pas de resultat
		 		if (!results.isBeforeFirst()) {
		 			return null;
		 		}
		 		
		 		//S'il y a un resutat, on set les donnees du client
		 		while (results.next()) {
		 			consumer.setFirstname(results.getString(3));
			 		consumer.setLastname(results.getString(2));
			 		consumer.setId(results.getInt(1));
		 		}

		 		return consumer;
	 		}
	 		
	 		return null;
	 	}
	 	
	 }