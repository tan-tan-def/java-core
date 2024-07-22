package asm04.test;

import asm02.Account;
import asm03.Transaction;
import asm04.common.TransactionType;
import asm04.dao.AccountDao;
import asm04.dao.TransactionDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AccountTest {
    /*
    Kiểm tra các hàm sau
     - boolean isSameAccount(String accountNumber)
     - boolean validateAccount(String accountNumber)
     - List<Transaction> getTransactions()
     - Lưu ý: Xóa hết dữ liệu trong accounts.dat, cuctomers.dat, transactions.dat trước mỗi lần chạy test
     */


    /*
       + Cần xóa dữ liệu accounts.dat, customers.dat, transactions.dat trước và sau khi thực hiện xong 1 lần test, khi dùng multiThread
     */
    private List<Account> accounts;
    private Account account;

    @org.junit.Before
    public void setUp() {
        accounts = new ArrayList<>();
        account = new Account();
    }

    @org.junit.After
    public void tearDown() {
        accounts = null;
        account = null;
    }
    @org.junit.Test
    public void isSameAccount() {
        List<Account> accounts1 = new ArrayList<>();
        account.setAccountNumber("000111");
        account.setBalance(200000000);
        account.setCustomerId("004125786941");
        accounts.add(account);
        AccountDao.save(accounts);
        assertTrue(account.isSameAccount("000111"));
        AccountDao.save(accounts1);
    }
    @org.junit.Test
    public void validateAccount(){
        assertTrue(account.validateAccount("235746"));
    }
    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
        return formattedDate;
    }
    @org.junit.Test
    public void getTransactions(){
        account.setAccountNumber("000222");
        AccountDao.update(account);
        Transaction transaction = new Transaction("000222",200000000,900000000, TransactionType.WITHDRAW,true,getTime());
        Transaction transaction1 = new Transaction("000222",400000000,1000000000, TransactionType.TRANSFER,true,getTime());
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        transactions.add(transaction1);
        TransactionDao.save(transactions);
        assertArrayEquals(account.getTransactions().toArray(),transactions.toArray());
    }
}