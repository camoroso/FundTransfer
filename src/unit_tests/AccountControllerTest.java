package unit_tests;

import static org.junit.Assert.*;
import controllers.AccountController;
import models.Account;
import models.Consumer;
import org.junit.Test;

import java.sql.SQLException;

public class AccountControllerTest {

    private AccountController accountController;

    private String validCardNumber = "000011110001111";
    private Consumer consumer = new Consumer(0, "consumer", "test");
    private Account validAccount = new Account(
            validCardNumber,
            "123",
            "01",
            "99",
            2.3F,
            consumer
    );

    private Account unvalidAccount = new Account(
            "000011110000",
            "13",
            "00",
            "2013",
            2.3F,
            consumer
    );




    @Test
    public void findRelatedAccountTest() {

        try {
            accountController.findRelatedAccount(validCardNumber);
        } catch (SQLException e) {
            fail("findRelatedAccount : Ne devrait pas apparaitre");
        }

        try {
            accountController.findRelatedAccount(000011110001111);
        } catch (SQLException e) {
            fail("findRelatedAccount : Not valid statement -- Integer");
        }

        try {
            accountController.findRelatedAccount("00001111000");
        } catch (SQLException e) {
            fail("findRelatedAccount : Not enough characters");
        }

        try {
            accountController.findRelatedAccount("000011kjjm11000");
        } catch (SQLException e) {
            fail("findRelatedAccount : Not valid characters");
        }

        try {
            accountController.findRelatedAccount(null);
        } catch (SQLException e) {
            fail("findRelatedAccount : Not valid statement -- null");
        }

        try {
            accountController.findRelatedAccount();
        } catch (SQLException e) {
            fail("findRelatedAccount : Not valid statement -- empty");
        }

    }

    @Test
    public void refundAccountTest(){

        try {
            accountController.refundAccount(validAccount, 300);
        } catch (SQLException e) {
            fail("Not valid statement : NInt");
        }

        try {
            accountController.refundAccount(validAccount, 300.5);
        } catch (SQLException e) {
            fail("Not valid statement : Double");
        }

        try {
            accountController.refundAccount(validAccount, 300.5F);
        } catch (SQLException e) {
            fail("Not valid statement : Float");
        }

        try {
            accountController.refundAccount(validAccount, "300");
        } catch (SQLException e) {
            fail("Not valid statement : String");
        }

        try {
            accountController.refundAccount(validAccount);
        } catch (SQLException e) {
            fail("Not valid statement : amount missing");
        }

        try {
            accountController.refundAccount(validAccount, null);
        } catch (SQLException e) {
            fail("Not valid statement : amount null");
        }


        try {
            accountController.refundAccount(unvalidAccount, 300);
        } catch (SQLException e) {
            fail("Not valid statement : Int and unvalid account");
        }

        try {
            accountController.refundAccount(unvalidAccount, 300.5);
        } catch (SQLException e) {
            fail("Not valid statement : Double and unvalid account");
        }

        try {
            accountController.refundAccount(unvalidAccount, 300.5F);
        } catch (SQLException e) {
            fail("Not valid statement : Float and unvalid account");
        }

        try {
            accountController.refundAccount(unvalidAccount, "300");
        } catch (SQLException e) {
            fail("Not valid statement : String and unvalid account");
        }

        try {
            accountController.refundAccount(unvalidAccount);
        } catch (SQLException e) {
            fail("Not valid statement : amount missing and unvalid account");
        }

        try {
            accountController.refundAccount(unvalidAccount, null);
        } catch (SQLException e) {
            fail("Not valid statement : amount null and unvalid account");
        }

        try {
            accountController.refundAccount(null, null);
        } catch (SQLException e) {
            fail("Not valid statement : amount null and account null");
        }

    }

    @Test
    public void refundAccountTest(){

        try {
            accountController.debitAccount(validAccount, 300);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- Int");
        }

        try {
            accountController.debitAccount(validAccount, 300.5);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- Double");
        }

        try {
            accountController.debitAccount(validAccount, 300.5F);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- Float");
        }

        try {
            accountController.debitAccount(validAccount, "300");
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- String");
        }

        try {
            accountController.debitAccount(validAccount);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- amount missing");
        }

        try {
            accountController.debitAccount(validAccount, null);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- amount null");
        }


        try {
            accountController.debitAccount(unvalidAccount, 300);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- Int and unvalid account");
        }

        try {
            accountController.debitAccount(unvalidAccount, 300.5);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- Double and unvalid account");
        }

        try {
            accountController.debitAccount(unvalidAccount, 300.5F);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- Float and unvalid account");
        }

        try {
            accountController.debitAccount(unvalidAccount, "300");
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- String and unvalid account");
        }

        try {
            accountController.debitAccount(unvalidAccount);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- amount missing and unvalid account");
        }

        try {
            accountController.debitAccount(unvalidAccount, null);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- amount null and unvalid account");
        }

        try {
            accountController.debitAccount(null, null);
        } catch (SQLException e) {
            fail("debitAccount : Not valid statement -- amount null and account null");
        }

    }

    @Test
    public void verifyCardTest(){

        try {
            boolean isOk = accountController.verifyCard(validCardNumber);
            if(!isOk)
                fail("verifyCard : Should be ok");
        } catch (SQLException e) {
            fail("verifyCard : Ne devrait pas apparaitre");
        }

        try {
            boolean isOk = accountController.verifyCard(000011110001111);
            if(isOk)
                fail("verifyCard : Shouldn't be ok");
        } catch (SQLException e) {
            fail("verifyCard : Not valid statement -- Integer");
        }

        try {
            boolean isOk = accountController.verifyCard("00001111000");
            if(isOk)
                fail("verifyCard : Shouldn't be ok");
        } catch (SQLException e) {
            fail("verifyCard : Not enough characters");
        }

        try {
            boolean isOk = accountController.verifyCard("000011kjjm11000");
            if(isOk)
                fail("verifyCard : Shouldn't be ok");
        } catch (SQLException e) {
            fail("verifyCard : Not valid characters -- String");
        }

        try {
            boolean isOk = accountController.verifyCard(null);
            if(isOk)
                fail("verifyCard : Shouldn't be ok");
        } catch (SQLException e) {
            fail("verifyCard : Not valid statement -- null");
        }

        try {
            boolean isOk = accountController.verifyCard();
            if(isOk)
                fail("verifyCard : Shouldn't be ok");
        } catch (SQLException e) {
            fail("verifyCard : Not valid statement -- empty");
        }

    }

    @Test
    public void verifyExpirationDateTest(){

        try {
            boolean isOk = accountController.verifyExpirationDate("01", "2020");
            if(!isOk)
                fail("findRelatedAccount : Should be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : Ne devrait pas apparaitre");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate("01", "20");
            if(!isOk)
                fail("findRelatedAccount : Should be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : Ne devrait pas apparaitre");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate(01, "2020");
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : Int and String -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate(01, "n0");
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : Int and String (avec lettre) -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate("n0", "20");
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : String (with letters) and String -- should be String (without letters) and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate(01, 20);
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : Int and Int -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate("01", 200);
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : String and Int -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate(null, "20");
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : null and String -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate(null, 20);
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : null and Int -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate("01", null);
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : String and null -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate(01, null);
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : Int and null -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate("", "");
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : empty String and empty String -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate("", "2020");
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : empty String and String -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate("03", "");
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : String and empty String -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate("3", "12");
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : invalid String and invalid String -- should be String and String");
        }

        try {
            boolean isOk = accountController.verifyExpirationDate("03", "2012");
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : String and invalid String -- should be String and String");
        }
    }

    @Test
    public void isOnlyNumericsTest(){

        try {
            boolean isOk = accountController.isOnlyNumerics("03");
            if(!isOk)
                fail("findRelatedAccount : Should be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : Ne devrait pas arrivé");
        }

        try {
            boolean isOk = accountController.isOnlyNumerics("0jedfhgizs3");
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : unvalid String");
        }

        try {
            boolean isOk = accountController.isOnlyNumerics(123);
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : Int -- should be String");
        }

        try {
            boolean isOk = accountController.isOnlyNumerics(null);
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : null -- should be String");
        }

        try {
            boolean isOk = accountController.isOnlyNumerics();
            if(isOk)
                fail("findRelatedAccount : Shouldn't be ok");
        } catch (SQLException e) {
            fail("findRelatedAccount : empty -- should be String");
        }


    }

    @Test
    public void verifyAccountTest(){

        try {
            Boolean isOk = accountController.verifyAccount(validAccount);
            if(!isOk)
                fail("findRelatedAccount : Ne devrait pas apparaitre");
        } catch (SQLException e) {
            fail("findRelatedAccount : Ne devrait pas apparaitre");
        }

        try {
            Boolean isOk = accountController.verifyAccount(unvalidAccount);
            if(isOk)
                fail("findRelatedAccount : Ne devrait pas apparaitre");
        } catch (SQLException e) {
            fail("findRelatedAccount : Ne devrait pas apparaitre");
        }

        try {
            Boolean isOk = accountController.verifyAccount(null);
            if(isOk)
                fail("findRelatedAccount : Ne devrait pas apparaitre");
        } catch (SQLException e) {
            fail("findRelatedAccount : Ne devrait pas apparaitre");
        }

        try {
            Boolean isOk = accountController.verifyAccount();
            if(isOk)
                fail("findRelatedAccount : Ne devrait pas apparaitre");
        } catch (SQLException e) {
            fail("findRelatedAccount : Ne devrait pas apparaitre");
        }

        try {
            Boolean isOk = accountController.verifyAccount("unvalid");
            if(isOk)
                fail("findRelatedAccount : Ne devrait pas apparaitre");
        } catch (SQLException e) {
            fail("findRelatedAccount : Ne devrait pas apparaitre");
        }

        try {
            Boolean isOk = accountController.verifyAccount(123);
            if(isOk)
                fail("findRelatedAccount : Ne devrait pas apparaitre");
        } catch (SQLException e) {
            fail("findRelatedAccount : Ne devrait pas apparaitre");
        }


    }
}
