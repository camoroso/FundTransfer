package application;

import models.Account;
import models.Consumer;

public class FundTransfer {
	 
	 	private static Consumer consumer;
	 	private static Account account;
	 	
	 	public static void main(String[] args) {
	 
	 		consumer = new Consumer();
	 		account = new Account();
	 		
	 		System.out.println("Veuillez entrer les informations de votre carte bancaire.");
	 		
	 		System.out.println("Numéro de la carte :");
	 		account.setCardNumber(System.console().readLine());
	 		
	 		System.out.println("Mois d'expiration (format MM) :");
	 		account.setExpirationMonth(System.console().readLine());
	 		
	 		System.out.println("Année d'expiration (format AA) :");
	 		account.setExpirationYear(System.console().readLine());
	 		
	 		System.out.println("CVV :");
	 		account.setCVV(System.console().readLine());
	 
	 	}
	 
	 }