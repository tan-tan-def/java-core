package asm03.test;

import asm02.Account;
import asm02.Bank;
import asm02.Customer;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomerTest {
    /*
    Test các hàm liên quan đến Customer:
        + Customer getCustomerById(String customerId)
        + boolean isCustomerExisted(Customer newCustomer)
        + boolean isCustomerPremium()
        + boolean validateCustomerId(String canCuocCongDan)
    */
    private Customer customer;
    private Account account;
    private Bank bank;
    @org.junit.Before
    public void setUp(){
        customer = new Customer();
        account = new Account();
        bank = new Bank();
    }
    @org.junit.After
    public void tearDown(){
        customer = null;
        account = null;
        bank = null;
    }
    //Kiểm tra hàm nhận customer dựa vào customerId
    @org.junit.Test
    public void getCustomerById() throws Exception {
        customer.setName("Tan");
        customer.setCustomerId("002135963125");
        bank.getCustomers().add(customer);
        assertEquals(customer,bank.getCustomerById("002135963125"));
    }
    //Kiểm tra khách hàng có tồn tại
    @org.junit.Test
    public void isCustomerExistedOne() throws Exception {
        customer.setName("Tan");
        customer.setCustomerId("002135963125");
        Customer customer1 = new Customer("Ha", "002563125785");
        Customer customer2 = new Customer("Thu", "002785463125");
        bank.getCustomers().add(customer);
        bank.getCustomers().add(customer1);
        bank.getCustomers().add(customer2);
        assertTrue(bank.isCustomerExist("002135963125"));
        assertTrue(bank.isCustomerExist("002563125785"));
        assertTrue(bank.isCustomerExist("002785463125"));

    }
    //Kiểm tra khách hàng không tồn tại
    @org.junit.Test
    public void isCustomerExistedTwo() throws Exception {
        customer.setName("Tan");
        customer.setCustomerId("002135963125");
        Customer customer1 = new Customer("Ha", "002563125785");
        Customer customer2 = new Customer("Thu", "002785463125");
        bank.getCustomers().add(customer);
        bank.getCustomers().add(customer1);
        bank.getCustomers().add(customer2);
        assertFalse(bank.isCustomerExist("001235463125"));
    }
    //Kiểm tra khách hàng là Premium
    @org.junit.Test
    public void isCustomerPremiumOne(){
        account.setBalance(15_000_000);
        account.setAccountNumber("123456");
        customer.addAccount(account);
        Account account1 = new Account();
        account1.setAccountNumber("234567");
        account1.setBalance(9_000_000);
        customer.addAccount(account1);
        assertTrue(customer.isCustomerPremium());
    }
    //Kiểm tra khách hàng không phải là Premium
    @org.junit.Test
    public void isCustomerPremiumTwo(){
        account.setBalance(8_000_000);
        account.setAccountNumber("123456");
        customer.addAccount(account);
        Account account1 = new Account();
        account1.setAccountNumber("234567");
        account1.setBalance(9_000_000);
        customer.addAccount(account1);
        assertFalse(customer.isCustomerPremium());
    }
    //Kiểm tra CCCD hợp lệ
    @org.junit.Test
    public void validateCustomerIdOne(){
        assertTrue(customer.validateCustomerId("004125632489"));
    }
    //Kiểm tra CCCD không hợp lệ
    @org.junit.Test
    public void validateCustomerIdTwo(){
        assertFalse(customer.validateCustomerId("003125632489"));
        assertFalse(customer.validateCustomerId("0031256"));
        assertFalse(customer.validateCustomerId("0031256324896165"));
        assertFalse(customer.validateCustomerId("0031256adsfdf"));

    }
}
