package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import models.Account;

public class AccountController {
	 	
	 	BDDController bddControl;
	 	ResultSet results;
	 	int result;
	 	Account account;
	 	
	 	public AccountController() throws ClassNotFoundException {
			this.bddControl = new BDDController();
		}
	 	
		public Account findRelatedAccount(String numberCard) throws SQLException {
	 		String query = "SELECT * FROM Account WHERE cardNumber = " + numberCard;
	 		results = bddControl.querySelect(query);
	 		
	 		while (results.next()) {
	 			account.setCardNumber(numberCard);
	 			account.setCVV(results.getString(4));
	 			account.setExpirationMonth(results.getString(2));
	 			account.setExpirationYear(results.getString(3));
	 			account.setRamainBalance(results.getFloat(5));
	 		}
	 		
	 		return account;
	 	}
	 	
	 	public void refundAccount(Account account, int amount) throws SQLException {
	 		//ON CREDITE LE COMPTE DU MONTANT PASSE EN PARAMETRE
	 		String query = "UPDATE Account SET ramain_balance = ramain_balance + " + amount + 
	 				" WHERE card_number = " + account.getCardNumber();
	 		result = bddControl.queryUpdate(query);
	 		System.out.println("Le solde du compte a été crédité de : " + amount + "€.");
	 		
	 	}
	 	
	 	public void debitAccount(Account account, int amount) throws SQLException {
	 		//SI LE MONTANT DU COMPTE EST 5 FOIS SUPERIEUR AU MONTANT A PAYER : OK
	 		//SI OK : DEBIT DANS 7 JOURS
	 		String query = "UPDATE Account SET ramain_balance = ramain_balance - " + amount + 
	 				" WHERE card_number = " + account.getCardNumber();
	 		result = bddControl.queryUpdate(query);
	 		System.out.println("Le solde du compte a été débité de " + amount + "€.");
	 	}
	 	
	 	public boolean isOnlyNumerics(String cardNumber) {
	 		boolean isOk = false;
	 		String regex = "[a-zA-Z]";
	 		if (!cardNumber.matches(regex)) {
	 			isOk = true;
	 		}
	 		return isOk;
	 	}
	 	
	 	public boolean verifyExpirationDate(String expirationMonth, String expirationYear) {
	 		boolean isOk = false;
	 		int month = Integer.parseInt(expirationMonth);
	 		int year = Integer.parseInt(expirationYear);
	 		
	 		int currentMonth = Calendar.MONTH;
	 		int currentYear = Calendar.YEAR;
	 		
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
	 		boolean isOk;
	 		
	 		String query = "SELECT * FROM Account WHERE cardNumber = " + cardNumber;
	 		results = bddControl.querySelect(query);
	 		if (results != null) {
	 			isOk = true;
	 		} else {
	 			isOk = false;
	 		}
	 		
	 		return isOk;
	 	}
	 
	 }