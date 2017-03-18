package unit_tests;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.junit.Test;

import controllers.AccountController;
import controllers.BDDController;
import junit.framework.TestCase;
import models.Account;
import models.Consumer;

public class AccountControllerTest extends TestCase {

    private AccountController accountCtrl;
    private BDDController bddCtrl;
    private Consumer consumer;
    private Account account, accountFailed, accountEmpty;
    
    @Override
    public void setUp() throws ClassNotFoundException, FileNotFoundException, SQLException, URISyntaxException {
    	accountCtrl = new AccountController();
    	bddCtrl = new BDDController();
    	bddCtrl.insertData();
    	prepareData();
    }
    
    @Override
    public void tearDown() {
    	accountCtrl = null;
    	assertNull(accountCtrl);
    }
    
    public void prepareData() {
        consumer = new Consumer(9, "TINTIN", "Danielle");
        account = new Account("656647384765","721","05","19",2.3F,consumer);
        accountFailed = new Account("098773693846","114","17","13",2.3F,consumer);
        accountEmpty = new Account();
    }

    

    @Test
    public void testInsertTransaction() throws SQLException{

        assertEquals(1, accountCtrl.insertTransaction(account, 300));
        assertEquals(0, accountCtrl.insertTransaction(accountFailed, 300));
        assertEquals(0, accountCtrl.insertTransaction(accountEmpty, 300));

    }

    @Test
    public void testDebitAccount() throws SQLException{

    	assertEquals(1, accountCtrl.debitAccount(account, 20));
    	assertEquals(0, accountCtrl.debitAccount(accountFailed, 300));
    	assertEquals(0, accountCtrl.debitAccount(accountEmpty, 20));

    }

    @Test
    public void testVerifyCard() throws SQLException{

    	assertTrue(accountCtrl.verifyCard(account.getCardNumber()));
    	assertFalse(accountCtrl.verifyCard(""));
    	assertFalse(accountCtrl.verifyCard("00001111000"));
    	assertFalse(accountCtrl.verifyCard("000011kjjm11000"));
    	assertFalse(accountCtrl.verifyCard(null));

    }

    @Test
    public void testVerifyExpirationDate(){

    	assertTrue(accountCtrl.verifyExpirationDate("01", "2020"));
    	assertTrue(accountCtrl.verifyExpirationDate("01", "20"));
    	assertFalse(accountCtrl.verifyExpirationDate("AA", "2020"));
    	assertFalse(accountCtrl.verifyExpirationDate("01", "AA"));
    	assertFalse(accountCtrl.verifyExpirationDate("", ""));
    	assertFalse(accountCtrl.verifyExpirationDate("01", null));
    	assertFalse(accountCtrl.verifyExpirationDate(null, "2020"));
    	assertFalse(accountCtrl.verifyExpirationDate(null, null));
    	assertFalse(accountCtrl.verifyExpirationDate("03", "2012"));

    }

    @Test
    public void testIsOnlyNumerics(){

    	String s = "toto";
    	
    	assertTrue(accountCtrl.isOnlyNumerics("03"));
    	assertFalse(accountCtrl.isOnlyNumerics("0jedfhgizs3"));
    	assertFalse(accountCtrl.isOnlyNumerics(null));
    	assertFalse(accountCtrl.isOnlyNumerics(s));

    }

    @Test
    public void testVerifyAccount() throws SQLException{

    	assertTrue(accountCtrl.verifyAccount(account));
    	assertFalse(accountCtrl.verifyAccount(accountFailed));
    	assertFalse(accountCtrl.verifyAccount(null));
    	assertFalse(accountCtrl.verifyAccount(accountEmpty));

    }

    @Test
    public void testRetrieveConsumer() throws SQLException{

    	assertNotNull(accountCtrl.retrieveConsumer(account));
    	assertNull(accountCtrl.retrieveConsumer(accountFailed));
    	assertNull(accountCtrl.retrieveConsumer(null));

    }

}
