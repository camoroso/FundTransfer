package controllers;

import java.math.BigInteger;
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
	 	
		public Account findRelatedAccount(String numberCard) throws SQLException {
	 		String query = "SELECT * FROM Account WHERE cardNumber = " + numberCard;
	 		results = bddControl.querySelect(query);
	 		
	 		while (results.next()) {
	 			account.setCardNumber(numberCard);
	 			account.setCVV(results.getString(4));
	 			account.setExpirationMonth(results.getString(2));
	 			account.setExpirationYear(results.getString(3));
	 			account.setRemainBalance(results.getFloat(5));
	 		}
	 		
	 		return account;
	 	}
	 	
	 	public void debitAccount(Account account, int amount) throws SQLException {
	 		//SI LE MONTANT DU COMPTE EST 5 FOIS SUPERIEUR AU MONTANT A PAYER : OK
	 		//SI OK : DEBIT DANS 7 JOURS
	 		String query = "UPDATE account SET remain_balance = remain_balance - " + amount + 
	 				" WHERE card_number = '" + account.getCardNumber() + "'";
	 		result = bddControl.queryUpdate(query);
	 		System.out.println("Le solde du compte a été débité de " + amount + "€.");
	 		
	 		this.insertTransaction(account, amount);
	 	}
	 	
	 	public boolean isOnlyNumerics(String cardNumber) {
	 		boolean isOk = false;
	 		String regex = "[a-zA-Z]";
	 		if (!cardNumber.matches(regex)) {
	 			isOk = true;
	 		} else if (cardNumber.length()!=15) {
	 			isOk = false;
	 		}
	 		return isOk;
	 	}
	 	
	 	public boolean verifyExpirationDate(String expirationMonth, String expirationYear) {
	 		boolean isOk = false;
	 		
	 		int month = Integer.parseInt(expirationMonth);
	 		int year = Integer.parseInt(expirationYear);
	 		
	 		DateFormat dateFormat = new SimpleDateFormat("yy/MM");
	 		Date date = new Date();
	 		String currentDate = dateFormat.format(date);
	 		
	 		int currentMonth = Integer.parseInt(currentDate.split("/")[1]);
	 		int currentYear = Integer.parseInt(currentDate.split("/")[0]);
	 		
	 		if (month > 0 && month < 13) {
	 			isOk = true;
	 		}
	 		
	 		if (year > currentYear) {
	 			isOk = true;
	 		} else if (year == currentYear) {
	 			if (month == currentMonth) {
	 				isOk = false;
	 			} else {
	 				isOk = true;
	 			}
	 		}
	 		
	 		return isOk;
	 	}
	 	
	 	public boolean verifyCard(String cardNumber) throws SQLException {
	 		boolean isOk = false;
	 		
	 		String query = "SELECT * FROM account WHERE card_number = '" + cardNumber + "'";
	 		results = bddControl.querySelect(query);
	 		if (results.next()) {
	 			isOk = true;
	 		} 
	 		
	 		return isOk;
	 	}
	 	
	 	public static String getRandomNumber(int digCount) {
	 	    StringBuilder sb = new StringBuilder(digCount);
	 	    for(int i=0; i < digCount; i++)
	 	        sb.append((char)('0' + rnd.nextInt(10)));
	 	    return sb.toString();
	 	}
	 	
	 	public void insertData() throws SQLException {
	 		String[] lastnames = {"AMOROSO", "CORDANI", "DELON", "FONTAS", "DUPOND", "DURAND", "LEGRAND", "DUPONT", "HADDOCK", "TINTIN"};
	 		String[] firstnames = {"Cloe", "Anthony", "Hermine", "Julien", "Herve", "Hubert", "Melanie", "Jacques", "Paul", "Denis"};
	 		
	 		for (int i=0; i<10;i++) {
	 			BigInteger cardNumber = new BigInteger(getRandomNumber(13));
		 		Integer cvv = new Integer(getRandomNumber(3));
		 		Integer expirationMonth= 01 + rnd.nextInt(12);
		 		Integer expirationYear = 17 + rnd.nextInt(10);
	 			
		 		Consumer consumer = new Consumer();
		 		
		 		consumer.setId(i);
		 		consumer.setFirstname(firstnames[i]);
		 		consumer.setLastname(lastnames[i]);
		 		
		 		System.out.println(i+1 + "/10");
		 		
		 		String query = "INSERT INTO consumer VALUES("+ consumer.getId() + ",'" +consumer.getFirstname()+"','"+consumer.getLastname()+"')";
		 		bddControl.queryInsert(query);
		 		
	 			Account account = new Account();
	 			
		 		account.setCardNumber(String.valueOf(cardNumber));
		 		account.setCVV(String.valueOf(cvv));
		 		account.setExpirationMonth(String.valueOf(expirationMonth));
		 		account.setExpirationYear(String.valueOf(expirationYear));
		 		account.setRemainBalance(300);
		 		account.setRelatedConsumer(consumer);
		 		
		 		query = "INSERT INTO account (card_number, expiration_month, expiration_year, cvv, remain_balance, consumer_id) "
		 				+ "VALUES ('"+account.getCardNumber()+"','"+account.getExpirationMonth()+"','"+account.getExpirationYear()+"','"+account.getCVV()+"','"+account.getRemainBalance()+"',"+account.getRelatedConsumer().getId()+")";
		 		bddControl.queryInsert(query);
	 		}
	 	}
	 
	 	public boolean verifyAccount(Account account) throws SQLException {
	 		boolean isOk = false;
	 		
	 		String query = "SELECT * FROM account WHERE card_number = '" + account.getCardNumber() + "'"
	 				+ " AND expiration_month = '" + Integer.parseInt(account.getExpirationMonth()) + "'"
	 						+ " AND expiration_year = '" + account.getExpirationYear().trim() + "'"
	 								+ " AND cvv = '" + account.getCVV() + "'";
	 		results = bddControl.querySelect(query);
	 		
	 		if (results.next()) {
	 			isOk = true;
	 		} 
	 		
	 		return isOk;
	 	}
	 	
	 	public void insertTransaction(Account account, int amount) throws SQLException {
	 		Consumer consumer = this.retrieveConsumer(account);
	 		String query = "INSERT INTO transaction (consumer_id, account_id, balance) VALUES ('"+consumer.getId()+"','"+account.getCardNumber()+"',"+amount+")";
	 		bddControl.queryInsert(query);
	 	}
	 	
	 	public Consumer retrieveConsumer(Account account) throws SQLException {
	 		Consumer consumer = new Consumer();
	 		String query = "SELECT consumer.id, consumer.last_name, consumer.first_name "
	 				+ "FROM consumer, account "
	 				+ "WHERE account.consumer_id = consumer.id "
	 				+ "AND account.card_number = '" + account.getCardNumber() + "'";
	 		ResultSet results = bddControl.querySelect(query);
	 		while (results.next()) {
	 			consumer.setFirstname(results.getString(3));
		 		consumer.setLastname(results.getString(2));
		 		consumer.setId(results.getInt(1));
	 		}
	 		
	 		return consumer;
	 	}
	 	
	 }