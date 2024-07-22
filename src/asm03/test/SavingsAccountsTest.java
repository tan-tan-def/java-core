package asm03.test;

import asm02.Customer;
import asm03.Asm03;
import asm03.DigitalCustomer;
import asm03.SavingsAccount;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SavingsAccountsTest {
    /*
    Test các hàm của class SavingsAccount:
        + boolean withdraw(double amount)
        + boolean isAccepted(double amount)
    */
    private SavingsAccount savingsAccount;
    private Customer customer;

    @org.junit.Before
    public void setUp(){
        savingsAccount = new SavingsAccount();
        customer = new Customer();
        Asm03.activeBank.addCustomer("002135967485","Jenifer Huynh");
    }

    //Kiểm tra hàm withdraw rút hợp lí
    @org.junit.Test
    public void withdrawSavingsOne() {
        DigitalCustomer.setAccount = "356974";
        savingsAccount.setAccountNumber("356974");
        savingsAccount.setBalance(5_000_000);
        Asm03.activeBank.addAccount("002135967485",savingsAccount);
        assertTrue(savingsAccount.withdraw(4_000_000));
    }
    //Kiểm tra hàm withdraw rút không hợp lệ
    @org.junit.Test
    public void withdrawSavingsTwo() {
        DigitalCustomer.setAccount = "135964";
        savingsAccount.setAccountNumber("153964");
        savingsAccount.setBalance(5_000_000);
        Asm03.activeBank.addAccount("002135967485",savingsAccount);
        assertFalse(savingsAccount.withdraw(4_990_000));
    }
    //Số tiền nhỏ hơn 50,000đ
    @org.junit.Test
    public void isAcceptedSavingsOne() {
        double amountOne = 10_000;
        boolean result1 = savingsAccount.isAccepted(amountOne);
        assertFalse(result1);
    }
    //Số tiền không phải bội số của 10,000đ
    @org.junit.Test
    public void isAcceptedSavingsTwo(){
        double amountTwo = 632_413;
        boolean result2 = savingsAccount.isAccepted(amountTwo);
        assertFalse(result2);
    }
    //Số tiền lớn hơn 5_000_000 tài khoản Normal
    @org.junit.Test
    public void isAcceptedSavingsThree(){
        savingsAccount.setAccountNumber("234567");
        savingsAccount.setBalance(9_000_000);
        Asm03.activeBank.addAccount("002135967485",savingsAccount);
        double amountThree = 6_000_000;
        boolean result3 = savingsAccount.isAccepted(amountThree);
        assertTrue(result3);
    }
    //Số tiền hợp lệ
    @org.junit.Test
    public void isAcceptedSavingsFour(){
        double amountFour = 3_000_000;
        boolean result4 = savingsAccount.isAccepted(amountFour);
        assertTrue(result4);
    }
    //Số tiền hợp lệ cho tài khoản Premium
    @org.junit.Test
    public void isAcceptedSavingsFive(){
        savingsAccount.setAccountNumber("123456");
        savingsAccount.setBalance(20_000_000);
        Asm03.activeBank.addAccount("002135967485",savingsAccount);
        double amountFive = 7_000_000;
        boolean result5 = savingsAccount.isAccepted(amountFive);
        assertTrue(result5);
    }
    @org.junit.After
    public void tearDown(){
        savingsAccount = null;
        customer = null;
    }
}