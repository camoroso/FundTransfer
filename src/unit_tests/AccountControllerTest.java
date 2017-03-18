package unit_tests;

import java.sql.SQLException;

import org.junit.Test;

import controllers.AccountController;
import junit.framework.TestCase;
import models.Account;
import models.Consumer;

public class AccountControllerTest extends TestCase {

    private AccountController accountController;
    private String cardNumber;
    private Consumer consumer;
    private Account account, accountFailed, accountEmpty;
    
    @Override
    public void setUp() throws ClassNotFoundException {
    	accountController = new AccountController();
    	prepareData();
    }
    
    @Override
    public void tearDown() {
    	accountController = null;
    	assertNull(accountController);
    }
    
    public void prepareData() {
    	cardNumber = "000011110001111";
        consumer = new Consumer(0, "consumer", "test");
        account = new Account(cardNumber,"123","01","99",2.3F,consumer);
        accountFailed = new Account("000011110000","13","00","2013",2.3F,consumer);
        accountEmpty = new Account();
    }

    
    @Test
    public void testFindRelatedAccount() throws SQLException {
    	
    	String i = null;
    	
    	assertNull(accountController.findRelatedAccount("0078986471111000"));
    	assertNull(accountController.findRelatedAccount("000011kjjm11000"));
    	assertNull(accountController.findRelatedAccount(null));
    	assertNull(accountController.findRelatedAccount(i));

    }

    @Test
    public void testInsertTransaction() throws SQLException{

    	String s = "1t2o3to";
    	
        assertEquals(1, accountController.insertTransaction(account, 300));
        //assertEquals(0, accountController.insertTransaction(account, i));
        //assertEquals(0, accountController.insertTransaction(account, Integer.parseInt(s)));
        assertEquals(0, accountController.insertTransaction(accountFailed, 300));
        //assertEquals(0, accountController.insertTransaction(accountFailed, i));
        //assertEquals(0, accountController.insertTransaction(accountFailed, Integer.parseInt(s)));
        assertEquals(0, accountController.insertTransaction(accountEmpty, 300));

    }

    @Test
    public void testDebitAccount() throws SQLException{

    	String s = "toto";
    	
    	assertEquals(0, accountController.debitAccount(account, 20));
    	//assertEquals(0, accountController.debitAccount(account, i));
    	//assertEquals(0, accountController.debitAccount(account, Integer.parseInt(s)));
    	assertEquals(0, accountController.debitAccount(accountFailed, 300));
    	//assertEquals(0, accountController.debitAccount(accountFailed, i));
    	//assertEquals(0, accountController.debitAccount(accountFailed, Integer.parseInt(s)));
    	//assertEquals(0, accountController.debitAccount(accountEmpty, i));

    }

    @Test
    public void testVerifyCard() throws SQLException{

    	String card = "";
    	
    	assertTrue(accountController.verifyCard(cardNumber));
    	assertFalse(accountController.verifyCard(card));
    	assertFalse(accountController.verifyCard("00001111000"));
    	assertFalse(accountController.verifyCard("000011kjjm11000"));
    	assertFalse(accountController.verifyCard(null));

    }

    @Test
    public void testVerifyExpirationDate(){

    	String month = "", year = "";
    	
    	assertTrue(accountController.verifyExpirationDate("01", "2020"));
    	assertTrue(accountController.verifyExpirationDate("01", "20"));
    	assertFalse(accountController.verifyExpirationDate("AA", "2020"));
    	assertFalse(accountController.verifyExpirationDate("01", "AA"));
    	assertFalse(accountController.verifyExpirationDate(month, year));
    	assertFalse(accountController.verifyExpirationDate("", ""));
    	assertFalse(accountController.verifyExpirationDate("01", null));
    	assertFalse(accountController.verifyExpirationDate(null, "2020"));
    	assertFalse(accountController.verifyExpirationDate(null, null));
    	assertFalse(accountController.verifyExpirationDate("03", "2012"));

    }

    @Test
    public void testIsOnlyNumerics(){

    	String s = "toto";
    	
    	assertTrue(accountController.isOnlyNumerics("03"));
    	assertFalse(accountController.isOnlyNumerics("0jedfhgizs3"));
    	assertFalse(accountController.isOnlyNumerics(null));
    	assertFalse(accountController.isOnlyNumerics(s));

    }

    @Test
    public void testVerifyAccount() throws SQLException{

    	assertTrue(accountController.verifyAccount(account));
    	assertFalse(accountController.verifyAccount(accountFailed));
    	assertFalse(accountController.verifyAccount(null));
    	assertFalse(accountController.verifyAccount(accountEmpty));

    }

    @Test
    public void testRetrieveConsumer() throws SQLException{

    	assertNotNull(accountController.retrieveConsumer(account));
    	assertNull(accountController.retrieveConsumer(accountFailed));
    	assertNull(accountController.retrieveConsumer(null));

    }

    @Test
    public void testGetRandomNumber(){

    	int i;
    	
    	assertEquals("", AccountController.getRandomNumber(0));
    	assertEquals("", AccountController.getRandomNumber(15));
    	//fail(AccountController.getRandomNumber(i));
    }

}
