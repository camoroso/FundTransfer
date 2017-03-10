package unit_tests;

import static java.util.ServiceLoader.fail;
import static org.junit.Assert.*;

import controllers.AccountController;
import models.Account;
import models.Consumer;
import org.junit.Test;

import java.sql.SQLException;

public class AccountControllerTest {

    private AccountController accountController;
    private String validCardNumber = "000011110001111";

    @Test
    public void findRelatedAccountTest() {

        try {
            accountController.findRelatedAccount(validCardNumber);
        } catch (SQLException e) {
            fail("Ne devrait pas apparaitre");
        }

        try {
            accountController.findRelatedAccount(000011110001111);
        } catch (SQLException e) {
            fail("Not yet statement : Integer");
        }

        try {
            accountController.findRelatedAccount("00001111000");
        } catch (SQLException e) {
            fail("Not enough characters");
        }

        try {
            accountController.findRelatedAccount("000011kjjm11000");
        } catch (SQLException e) {
            fail("Not valid characters");
        }

        try {
            accountController.findRelatedAccount(null);
        } catch (SQLException e) {
            fail("Not valid statement : null");
        }

        try {
            accountController.findRelatedAccount();
        } catch (SQLException e) {
            fail("Not valid statement : empty");
        }

    }

    @Test
    public void refundAccountTest(){

        Consumer consumer = new Consumer(0, "consumer", "test");
        Account validAccount = new Account(validCardNumber, "","","",2.3F, consumer);

        try {
            accountController.refundAccount(validAccount, 300);
        } catch (SQLException e) {
            fail("Ne devrait pas apparaitre");
        }
        /*try {
            accountController.refundAccount(validAccount, 300);
        } catch (SQLException e) {
            fail("Ne devrait pas apparaitre");
        }*/

    }
}
