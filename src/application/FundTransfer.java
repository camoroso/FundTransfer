package application;

import java.io.Console;
import java.sql.SQLException;
import java.util.Scanner;

import controllers.AccountController;
import models.Account;
import models.Consumer;

public class FundTransfer {
	 
	 	private static Consumer consumer;
	 	private static Account account;
	 	private static int amount;
	 	private static AccountController accountCtrl;
	 	
	 	public static void main(String[] args) throws SQLException, ClassNotFoundException {
	 
	 		consumer = new Consumer();
	 		account = new Account();
	 		accountCtrl = new AccountController();
	 		boolean isOk;
	 		
	 		Scanner sc = new Scanner(System.in);
	 		
	 		System.out.println("Montant du virement :");
	 		amount = sc.nextInt();
	 		
	 		System.out.println("Veuillez entrer les informations de votre carte bancaire.");
	 		
	 		System.out.print("Numéro de la carte :");
	 		account.setCardNumber(sc.nextLine());
	 		
	 		isOk = accountCtrl.verifyCard(account.getCardNumber()); 
	 		if (!isOk) {
	 			System.exit(1);
	 		}
	 		
	 		System.out.print("Mois d'expiration (format MM) :");
	 		account.setExpirationMonth(sc.nextLine());
	 		
	 		System.out.print("Année d'expiration (format AA) :");
	 		account.setExpirationYear(sc.nextLine());
	 		
	 		if (!accountCtrl.verifyExpirationDate(account.getExpirationMonth(), account.getExpirationYear())) {
	 			System.exit(1);
	 		}
	 		
	 		System.out.print("CVV :");
	 		account.setCVV(sc.nextLine());
	 
	 	}
	 
	 }