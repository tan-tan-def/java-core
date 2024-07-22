package asm03.test;

import asm02.Account;
import asm02.Customer;

import static org.junit.Assert.*;

public class AccountTest {
    /*
    Test các hàm liên quan đến Account:
        + boolean isAccountExisted(Account newAccount)
        + boolean validateAccount(String accountNumber)
        + boolean isAccountPremium()
        + double getTotalAccountBalance()
        + Account getAccountByAccountNumber(String accountNumber)
    */
    private Customer customer;
    private Account account;
    @org.junit.Before
    public void setUp(){
        customer = new Customer();
        account = new Account();
    }
    @org.junit.After
    public void tearDown(){
        customer = null;
        account = null;
    }
    //Kiểm tra số tài khoản đã tồn tại rồi
    @org.junit.Test
    public void isAccountExistedOne(){
        account.setAccountNumber("123456");
        account.setBalance(15_000_000);
        customer.getAccounts().add(account);
        assertTrue(customer.isAccountExist("123456"));
    }
    //Kiểm tra số tài khoản chưa tồn tại
    @org.junit.Test
    public void isAccountExistedTwo(){
        account.setAccountNumber("234567");
        account.setBalance(15_000_000);
        customer.getAccounts().add(account);
        assertFalse(customer.isAccountExist("152356"));
    }
    //Kiểm tra số tài khoản hợp lệ
    @org.junit.Test
    public void validateAccountOne(){
        account.setAccountNumber("234567");
        assertTrue(account.validateAccount(account.getAccountNumber()));
    }
    //Kiểm tra số tài khoản không hợp lệ vì có 7 kí tự
    @org.junit.Test
    public void validateAccountTwo(){
        account.setAccountNumber("2345689");
        assertFalse(account.validateAccount(account.getAccountNumber()));
    }
    //Kiểm tra số tài khoản không hợp lệ vì có kí tự chữ
    @org.junit.Test
    public void validateAccountThree(){
        account.setAccountNumber("2344acsd");
        assertFalse(account.validateAccount(account.getAccountNumber()));
    }
    //Kiểm tra tài khoản là Premium
    @org.junit.Test
    public void isAccountPremiumOne(){
        account.setBalance(15_000_000);
        assertTrue(account.isPremiumAccount());
    }
    //Kiểm tra tài khoản là Normal
    @org.junit.Test
    public void isAccountPremiumTwo(){
        account.setBalance(8_000_000);
        assertFalse(account.isPremiumAccount());
    }
    //Kiểm tra tính tổng các tài khoản
    @org.junit.Test
    public void getTotalAccountBalance(){
        account.setAccountNumber("123456");
        account.setBalance(15_000_000);
        customer.getAccounts().add(account);

        Account account1 = new Account();
        account1.setAccountNumber("234567");
        account1.setBalance(7_000_000);
        customer.getAccounts().add(account1);

        Account account2 = new Account();
        account2.setAccountNumber("345678");
        account2.setBalance(6_000_000);
        customer.getAccounts().add(account2);

        assertEquals(28_000_000,customer.getBalance(), 0.01);
    }
    //Kiểm tra hàm nhận account dựa vào account number
    @org.junit.Test
    public void getAccountByAccountNumber(){
        account.setAccountNumber("123456");
        account.setBalance(15_000_000);
        customer.getAccounts().add(account);
        assertEquals(account,customer.getAccountByAccountNumber("123456"));
    }
}
