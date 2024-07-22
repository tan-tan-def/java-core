package asm04.test;

import asm02.Account;
import asm02.Customer;
import asm04.dao.AccountDao;
import asm04.dao.CustomerDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerTest {
    /*
    Kiểm tra các hàm sau
     - String premiumOrNormal(String customerId)
     - double getBalances(String customerId)
     - boolean isAcceptNormal(double amount)
     - boolean isAccountExist(List<Account> accounts,String accountNumber)(x)
     - boolean isSameAccount(String depositeAccount, String receiveAccount)(x)
     - boolean isAccountInSameCustomer(String customerId, String accountNumber)
     - boolean isValueDepositeAmount(String depositeAmount)(x)
     - boolean isValueRemainAmount(String depositeAccount, double amount)(x)
     - String getCustomerIdByAccountNumber(String accountNumber)(x)
     - Customer getCustomerByCustomerId(String accountNumber)
     - List<Account> getAccounts(String customerId)
     - Account getAccountByAccountNumber(List<Account> accounts, String accountNumber)
     - String getNameByCustomerId(String accountNumber)
     */




    /*
     - Lưu ý:
       + Cần xóa dữ liệu accounts.dat, customers.dat, transactions.dat trước và sau khi thực hiện xong 1 lần test nếu dùng multiThread
     */
    private List<Account> accounts;
    private Customer customer;
    private static List<Customer> customerTests=new ArrayList<>();
    @org.junit.BeforeClass
    public static void setUpBefore()  {
        Customer customer0 = new Customer("Hứa Vĩ Văn", "037156748963");
        Customer customer1 = new Customer("Hương Tràm","037156748205");
        Customer customer2 = new Customer("Hồ Ngọc Hà","037156056205");
        Customer customer3 = new Customer("Noo Phước Thịnh", "034256971852");
        Customer customer4 = new Customer("Mỹ Tâm","082741364852");
        Customer customer5 = new Customer("Văn Mai Hương","064135746982");
        Customer customer6 = new Customer("Dương Dương","067641258963");
        Customer customer7 = new Customer("Vương Nhất Bác", "084652138795");
        Customer customer8 = new Customer("Địch Lệ Nhiệt Ba", "075105632895");
        Customer customer9 = new Customer("Hoàng Thùy Linh","091423964852");
        Customer customer10 = new Customer("Hòa Minzy", "027156328746");

        customerTests.add(customer0);
        customerTests.add(customer1);
        customerTests.add(customer2);
        customerTests.add(customer3);
        customerTests.add(customer4);
        customerTests.add(customer5);
        customerTests.add(customer6);
        customerTests.add(customer7);
        customerTests.add(customer8);
        customerTests.add(customer9);
        customerTests.add(customer10);

        CustomerDao.save(customerTests);
    }
    @org.junit.Before
    public void setUp(){
        accounts=new ArrayList<>();
        customer=new Customer();
    }
    @org.junit.After
    public void tearDown(){
        accounts=null;
        customer=null;
    }
    @org.junit.AfterClass
    public static void tearDownClass(){
        customerTests=null;
    }
    @org.junit.Test
    public void premiumOrNormal(){
        Account account = new Account("111111",5000000,"037156748963");
        AccountDao.update(account);
        assertEquals(customerTests.get(0).premiumOrNormal("037156748963"), "Normal");
    }
    @org.junit.Test
    public void getBalances(){
        Account account = new Account("222222",5000000,"037156748205");
        Account account1 = new Account("333333",15000000,"037156748205");
        Account account2 = new Account("444444",9000000,"037156748205");
        AccountDao.update(account);
        AccountDao.update(account1);
        AccountDao.update(account2);
        assertEquals(customerTests.get(1).getBalances("037156748205"),29000000,0.01);

    }
    @org.junit.Test
    public void isAcceptNormal(){
        Account account = new Account("555555",9000000,"037156056205");
        AccountDao.update(account);
        assertFalse(customerTests.get(2).isAcceptNormal(7000000));
    }
    @org.junit.Test
    public void isAccountExist(){
        Account account = new Account("666666",500000000,"034256971852");
        Account account1 = new Account("777777",500000000,"034256971852");
        accounts.add(account);
        accounts.add(account1);
        assertTrue(customerTests.get(3).isAccountExist(accounts,"777777"));
    }
    @org.junit.Test
    public void isSameAccount(){
        assertFalse(customer.isSameAccount("123456","567895"));
    }
    @org.junit.Test
    public void isAccountInSameCustomer(){
        Account account = new Account("888888",600000000,"082741364852");
        Account account1 = new Account("999999",900000000,"082741364852");
        AccountDao.update(account);
        AccountDao.update(account1);
        assertTrue(customerTests.get(4).isAccountInSameCustomer("082741364852", "888888"));
    }
    @org.junit.Test
    public void isValueDepositeAmount(){
        assertTrue(customer.isValueDepositeAmount("6000000000"));
    }
    @org.junit.Test
    public void isValueRemainAmount(){
        Account account = new Account("101010", 9000000, "064135746982");
        Account account1 = new Account("202020", 8000000, "064135746982");
        AccountDao.update(account);
        AccountDao.update(account1);
        assertTrue(customerTests.get(5).isValueRemainAmount("202020",4000000));
    }
    @org.junit.Test
    public void getCustomerIdByAccountNumber(){
        Account account = new Account("303030", 9000000, "067641258963");
        AccountDao.update(account);
        assertEquals(customerTests.get(6).getCustomerIdByAccountNumber("303030"),"067641258963");
    }
    @org.junit.Test
    public void getCustomerByCustomerId(){
        Account account = new Account("404040", 9000000,"084652138795" );
        AccountDao.update(account);
        Customer customer1 = customer.getCustomerByCustomerId("404040");
        assertEquals(customer1,customerTests.get(7));
    }
    @org.junit.Test
    public void getAccounts(){
        Account account = new Account("505050", 9000000, "075105632895");
        Account account1 = new Account("606060", 9000000, "075105632895");
        AccountDao.update(account);
        AccountDao.update(account1);
        List<Account> actualAccounts = customerTests.get(8).getAccounts("075105632895");
        List<Account> expectedAccounts = new ArrayList<>();
        expectedAccounts.add(account);
        expectedAccounts.add(account1);
        assertArrayEquals(expectedAccounts.toArray(),actualAccounts.toArray());
    }
    @org.junit.Test
    public void  getAccountByAccountNumber(){
        Account account = new Account("707070",600000000,"091423964852");
        accounts.add(account);
        assertEquals(customerTests.get(9).getAccountByAccountNumber(accounts,"707070"),account);
    }
    @org.junit.Test
    public void getNameByCustomerId() {
        Account account = new Account("808080", 900000000,"027156328746");
        Account account1 = new Account("909090", 700000000,"027156328746");
        AccountDao.update(account);
        AccountDao.update(account1);
        assertEquals(customerTests.get(10).getNameByCustomerId("808080"),"Hòa Minzy");
    }
}