package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import models.Account;

public class AccountController {
	 	
	 	BDDController bddControl;
	 	ResultSet results;
	 	int result;
	 	Account account;
	 	
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
	 		String query = "UPDATE Account SET ramain_balance = ramain_balance + " + amount + 
	 				" WHERE card_number = " + account.getCardNumber();
	 		result = bddControl.queryUpdate(query);
	 		
	 	}
	 	
	 	public void debitAccount(Account account, int amount) throws SQLException {
	 		String query = "UPDATE Account SET ramain_balance = ramain_balance - " + amount + 
	 				" WHERE card_number = " + account.getCardNumber();
	 		result = bddControl.queryUpdate(query);
	 	}
	 
	 }