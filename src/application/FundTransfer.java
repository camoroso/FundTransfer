package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;

import controllers.AccountController;
import controllers.BDDController;
import models.Account;
import models.Consumer;

public class FundTransfer {
	 
	 	private static Consumer consumer;
	 	private static Account account;
	 	private static int amount;
	 	private static AccountController accountCtrl;
	 	private static BDDController bddCtrl;
	 	
	 	public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException, URISyntaxException {
	 
	 		consumer = new Consumer();
	 		account = new Account();
	 		accountCtrl = new AccountController();
	 		bddCtrl = new BDDController();
	 		boolean isOk;
	 		
	 		Scanner sc = new Scanner(System.in);
	 		
	 		System.out.println("--- INJECTION EN BASE DE DONNEES ---");
	 		bddCtrl.insertData();
	 		
	 		System.out.println("--- TRANSACTION ---");
	 		System.out.println("Montant du paiement :");
	 		amount = sc.nextInt();
	 		
	 		System.out.println("Veuillez entrer les informations de votre carte bancaire.");
	 		
	 		System.out.print("Numero de la carte :");
	 		account.setCardNumber(sc.next());
	 		
	 		isOk = accountCtrl.verifyCard(account.getCardNumber()); 
	 		if (!isOk) {
	 			System.out.println("Numero de carte incorrect.");
	 			System.exit(1);
	 		}
	 		
	 		System.out.print("Mois d'expiration (format MM) :");
	 		account.setExpirationMonth(sc.next());
	 		
	 		System.out.print("Annee d'expiration (format AA) :");
	 		account.setExpirationYear(sc.next());
	 		
	 		if (!accountCtrl.verifyExpirationDate(account.getExpirationMonth(), account.getExpirationYear())) {
	 			System.out.println("Date d'expiration incorrecte.");
	 			System.exit(1);
	 		}
	 		
	 		System.out.print("CVV :");
	 		account.setCVV(sc.next());
	 		
	 		if (!accountCtrl.verifyAccount(account)) {
	 			System.out.println("Les informations entrees ne correspondent pas.");
	 			System.exit(1);
	 		} else {
	 			System.out.println("Les informations entrees sont correctes.");
	 		}
	 		
	 		System.out.println("--- TRANSFERT EN COURS ---");
	 		accountCtrl.debitAccount(account, amount);
	 		
	 		System.out.println("--- TRANSFERT TERMINE ---");
	 	}
	 
	 }