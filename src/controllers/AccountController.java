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
	 	
	 	public void refundAccount(Account account, int amount) throws SQLException {
	 		//ON CREDITE LE COMPTE DU MONTANT PASSE EN PARAMETRE
	 		String query = "UPDATE Account SET remain_balance = remain_balance + " + amount + 
	 				" WHERE card_number = " + account.getCardNumber();
	 		result = bddControl.queryUpdate(query);
	 		System.out.println("Le solde du compte a été crédité de : " + amount + "€.");
	 		
	 	}
	 	
	 	public void debitAccount(Account account, int amount) throws SQLException {
	 		//SI LE MONTANT DU COMPTE EST 5 FOIS SUPERIEUR AU MONTANT A PAYER : OK
	 		//SI OK : DEBIT DANS 7 JOURS
	 		String query = "UPDATE account SET remain_balance = remain_balance - " + amount + 
	 				" WHERE card_number = '" + account.getCardNumber() + "'";
	 		result = bddControl.queryUpdate(query);
	 		System.out.println("Le solde du compte a été débité de " + amount + "€.");
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
		 		
		 		consumer.setFirstname(firstnames[i]);
		 		consumer.setLastname(lastnames[i]);
		 		
		 		System.out.println("--- INSERTION CONSUMER " + i + "/10 ---");
		 		System.out.println(consumer.getFirstname() + " - " + consumer.getLastname());
		 		
		 		String query = "INSERT INTO consumer (first_name, last_name) VALUES('"+consumer.getFirstname()+"','"+consumer.getLastname()+"')";
		 		bddControl.queryInsert(query);
		 		query = "SELECT id FROM consumer WHERE first_name = '" + consumer.getFirstname() + "' AND last_name = '" + consumer.getLastname() + "'";
		 		int id = 0;
		 		ResultSet results = bddControl.querySelect(query);
		 		while (results.next()) {
		 			id = results.getInt(1);
		 		}
		 		
	 			Account account = new Account();
	 			
		 		account.setCardNumber(String.valueOf(cardNumber));
		 		account.setCVV(String.valueOf(cvv));
		 		account.setExpirationMonth(String.valueOf(expirationMonth));
		 		account.setExpirationYear(String.valueOf(expirationYear));
		 		account.setRemainBalance(300);
		 		account.setRelatedConsumer(consumer);
		 		
		 		System.out.println("--- INSERTION ACCOUNT " + i + "/10 ---");
		 		System.out.println("Card number : " + account.getCardNumber() + " for consumer id " + id);
		 		
		 		query = "INSERT INTO account (card_number, expiration_month, expiration_year, cvv, remain_balance, consumer_id) "
		 				+ "VALUES ('"+account.getCardNumber()+"','"+account.getExpirationMonth()+"','"+account.getExpirationYear()+"','"+account.getCVV()+"','"+account.getRemainBalance()+"',"+id+")";
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
	 	
	 }