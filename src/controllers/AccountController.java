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
	 	
		public Account findRelatedAccount(String numberCard) throws SQLException {
	 		String query = "SELECT * FROM account WHERE card_number = '" + numberCard + "'";
	 		try {
	 			results = bddControl.querySelect(query);
	 		} catch (Exception e){
	 			return null;
	 		}
	 		
	 		if (!results.isBeforeFirst()) {
	 			return null;
	 		}
	 		
 			while (results.next()) {
 				account = new Account();
 				account.setCardNumber(numberCard);
	 			account.setCVV(results.getString(4));
	 			account.setExpirationMonth(results.getString(2));
	 			account.setExpirationYear(results.getString(3));
	 			account.setRemainBalance(results.getFloat(5));
 			}
	 		
	 		return account;
	 	}
	 	
	 	public int debitAccount(Account account, int amount) throws SQLException {
	 		int result = 0;
	 		if (this.isTheAmountOk(account, amount)) {
	 			String query = "UPDATE account SET remain_balance = remain_balance - " + amount + 
		 				" WHERE card_number = '" + account.getCardNumber() + "'";
		 		result = bddControl.queryUpdate(query);
		 		System.out.println("Le solde du compte a été débité de " + amount + "€.");
		 		
		 		result = this.insertTransaction(account, amount);
	 		}
	 		return result;
	 	}
	 	
	 	public boolean isTheAmountOk(Account account, int amount) throws SQLException {
	 		boolean isOk = false;
	 		String query = "SELECT remain_balance FROM account WHERE card_number = '" + account.getCardNumber() + "'";
	 		ResultSet results = bddControl.querySelect(query);
	 		float balance = 0;
	 		while (results.next()) {
	 			balance = results.getFloat(1);
	 		}
	 		if (balance >= (amount * 5)) {
	 			isOk = true;
	 		}

	 		return isOk;
	 	}
	 	
	 	public boolean isOnlyNumerics(String cardNumber) {
	 		boolean isOk = false;
	 		String regex = "[0-9]+";
	 		if (cardNumber != null && cardNumber.matches(regex)) {
	 			isOk = true;
	 		} else if (cardNumber != null && cardNumber.length()!=15) {
	 			isOk = false;
	 		}
	 		return isOk;
	 	}
	 	
	 	public boolean verifyExpirationDate(String expirationMonth, String expirationYear) {
	 		boolean isOk = false;
	 		int month = 0, year = 0;
	 		
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
	 		} else {
	 			isOk = false;
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
	 	
	 	public boolean verifyAccount(Account account) throws SQLException {
	 		boolean isOk = false;
	 		
	 		if (account != null && account.getCardNumber() != null) {
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
	 	
	 	public int insertTransaction(Account account, int amount) throws SQLException {
	 		int result = 0; 
	 		Consumer consumer = this.retrieveConsumer(account);
	 		if (consumer.getFirstname() != null && consumer.getLastname() != null) {
	 			String query = "INSERT INTO transaction (consumer_id, account_id, balance) VALUES ('"+consumer.getId()+"','"+account.getCardNumber()+"',"+amount+")";
		 		result = bddControl.queryInsert(query);
	 		}
	 		return result;
	 	}
	 	
	 	public Consumer retrieveConsumer(Account account) throws SQLException {
	 		Consumer consumer = new Consumer();
	 		String query = "SELECT consumer.id, consumer.last_name, consumer.first_name "
	 				+ "FROM consumer, account "
	 				+ "WHERE account.consumer_id = consumer.id "
	 				+ "AND account.card_number = '" + account.getCardNumber() + "' "
	 						+ "AND account.expiration_month = '" + account.getExpirationMonth() + "' "
	 								+ "AND account.expiration_year = '" + account.getExpirationYear() + "' "
	 										+ " AND account.cvv = '" + account.getCVV() + "' "; 
	 		ResultSet results = bddControl.querySelect(query);
	 		while (results.next()) {
	 			consumer.setFirstname(results.getString(3));
		 		consumer.setLastname(results.getString(2));
		 		consumer.setId(results.getInt(1));
	 		}
	 		
	 		return consumer;
	 	}
	 	
	 }