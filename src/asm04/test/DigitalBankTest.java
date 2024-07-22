package asm04.test;

import asm02.Account;
import asm02.Customer;
import asm03.DigitalBank;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DigitalBankTest {
    /*
    Kiểm tra phương thưc trong class DigitalBank
     - boolean checkNumberProvince(String cccdPrefix, String[] numberProvince)
     - boolean isAccountExisted(List<Account> accountList, String accountNumber)
     - boolean isAccountExisted(List<Account> accountsList, Account newAccount)
     - Customer getCustomerById(List<Customer> customerList, String customerId)
     - boolean isCustomerExisted(List<Customer> customers, Customer newCustomer)
    */
    private DigitalBank digitalBank;
    private List<Account> accountList;
    private Account account;
    private List<Customer>customers;
    @org.junit.Before
    public void setUp() {
        digitalBank = new DigitalBank();
        accountList = new ArrayList<>();
        account = new Account();
        customers = new ArrayList<>();
    }

    @org.junit.After
    public void tearDown() {
        digitalBank = null;
        accountList = null;
        account=null;
        customers=null;
    }
    @org.junit.Test
    public void checkNumberProvince() {
        String [] numberProvince = {"001", "002", "004", "006", "008", "010", "011", "012", "014",
                "015", "017", "019", "020", "022", "024", "025", "026", "096",
                "027", "030", "031", "033", "034", "035", "036", "037", "038",
                "040", "042", "044", "045", "046", "048", "049", "051", "052",
                "054", "056", "058", "060", "062", "064", "066", "067", "068",
                "070", "072", "074", "075", "077", "079", "080", "082", "083",
                "084", "086", "087", "089", "091", "092", "093", "094", "095"};
        assertTrue(digitalBank.checkNumberProvince("037863145782".substring(0,3),numberProvince));

    }
    @org.junit.Test
    public void isAccountExistedForString(){
        account.setAccountNumber("123456");
        account.setBalance(200000000);
        account.setCustomerId("004125786941");
        accountList.add(account);
        assertFalse(digitalBank.isAccountExisted(accountList,"123457"));
    }
    @org.junit.Test
    public void isAccountExistedForAccount(){
        account.setAccountNumber("123456");
        account.setBalance(200000000);
        account.setCustomerId("004125786941");
        accountList.add(account);
        assertTrue(digitalBank.isAccountExisted(accountList,account));
    }
    @org.junit.Test
    public void getCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId("002137856914");
        customer.setName("Ha Van");
        customers.add(customer);
        assertEquals(digitalBank.getCustomerById(customers,customer.getCustomerId()),customer);
    }
    @org.junit.Test
    public void isCustomerExisted() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId("002137856914");
        customer.setName("Ha Van");
        customers.add(customer);
        assertTrue(digitalBank.isCustomerExisted(customers,customer));
    }
}