package asm03.test;

import asm03.Asm03;
import asm03.DigitalCustomer;
import asm03.LoansAccount;

import static org.junit.Assert.*;
/*
    Test các hàm của class LoanAccount:
        + boolean withdraw(double amount)
        + boolean isAccepted(double amount)
        + double getFee(double amount)
*/

public class LoanAccountTest {
    private LoansAccount loansAccount;

    @org.junit.Before
    public void setUp(){
        loansAccount = new LoansAccount();
        Asm03.activeBank.addCustomer("002135967485","Jenifer Huynh");
    }
    //Kiểm tra rút tiền hợp lệ
    @org.junit.Test
    public void withdrawLoanOne() {
        DigitalCustomer.setAccount = "153842";
        LoansAccount loansAccount1 = new LoansAccount();
        loansAccount1.setBalance(15_000_000);
        loansAccount1.setAccountNumber("153842");
        Asm03.activeBank.addAccount("002135967485",loansAccount1);
        assertTrue(loansAccount1.withdraw(5_000_000));
    }

    //Kiểm tra rút tiền hợp không lệ
    @org.junit.Test
    public void withdrawLoanTwo() {
        DigitalCustomer.setAccount = "153842";
        LoansAccount loansAccount1 = new LoansAccount();
        loansAccount1.setAccountNumber("153842");
        Asm03.activeBank.addAccount("002135967485",loansAccount1);
        assertFalse(loansAccount1.withdraw(9_900_000));
    }
    //Kiểm tra hạn mức hợp lệ
    @org.junit.Test
    public void isAcceptedLoanOne() {
        double validAmount = 50_000_000;//Số tiền rút hợp lệ
        boolean result1 = loansAccount.isAccepted(validAmount);
        assertTrue(result1);
    }
    //Kiêm tra hạn mức không hợp lệ
    @org.junit.Test
    public void isAcceptedLoanTwo() {
        double exceedingAmount = 150_000_000;//Số tiền rút không hợp lệ
        boolean result2 = loansAccount.isAccepted(exceedingAmount);
        assertFalse(result2);
    }
    //Kiểm tra phí rút tài khoản Premium
    @org.junit.Test
    public void getFeeLoanPremium(){
        loansAccount.setBalance(20_000_000);
        loansAccount.setAccountNumber("123456");
        Asm03.activeBank.addAccount("002135967485",loansAccount);
        assertEquals(50_000,loansAccount.getFee(5_000_000), 0.01);
    }
    //Kiểm tra phí rút tài khoản Normal
    @org.junit.Test
    public void getFeeLoanNormal(){
        loansAccount.setBalance(6_000_000);
        loansAccount.setAccountNumber("234567");
        Asm03.activeBank.addAccount("002135967485",loansAccount);
        assertEquals(200_000,loansAccount.getFee(4_000_000), 0.01);
    }
    @org.junit.After
    public void tearDown(){
        loansAccount = null;
    }
}
