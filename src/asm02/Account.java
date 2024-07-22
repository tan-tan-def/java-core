package asm02;

import asm03.SavingsAccount;
import asm03.Transaction;
import asm04.dao.AccountDao;
import asm04.dao.TransactionDao;
import asm04.common.TransactionType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private double balance;
    private String customerId;
    private List<Transaction> transactionlist;

    public Account() {
        this.transactionlist = new ArrayList<>();
    }

    public Account(String accountNumber, double balance, String customerId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public boolean isPremiumAccount(){
        return getBalance()>=10_000_000;
    }
    public boolean validateAccount(String accountNumber){
        if(accountNumber.length()==6&&accountNumber.matches("[0-9]+")){
            return true;
        }
        return false;
    }

    //Thực hiện chức năng nâng cao: Thêm list để lưu lịch sử giao dịch cho mỗi loại tài khoản
    public List<Transaction> getTransactionlist() {
        return transactionlist;
    }
    public void addTransaction(Transaction transaction){
            transactionlist.add(transaction);
    }
    //Asm04
    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
        return formattedDate;
    }
    //Asm04
    //Tạo ra thêm một giao dịch cho account và cập nhật số dư tài khoản
    public void createTransaction(String accountNumber ,double amount,double balance, TransactionType type, boolean status,String time){
        Transaction transaction = new Transaction(accountNumber,amount,balance,type,status,time);
        TransactionDao.update(transaction);
    }
    public void input(Scanner scanner, String customerId){
        String accountNumber;
        String balance;
        do{
            System.out.print("Nhập số tài khoản gồm 6 chữ số: ");
            accountNumber = scanner.nextLine().trim();
            if(!accountNumber.matches("[0-9]+")||accountNumber.length()!=6){
                System.out.println("Vui lòng nhập 6 kí tự số");
            }
            if(isSameAccount(accountNumber)){
                System.out.println("Số tài khoản " + accountNumber + " đã có, tác vụ không thành công");
            }
        }while(!accountNumber.matches("[0-9]+")||accountNumber.length()!=6||isSameAccount(accountNumber));
        do{
            System.out.print("Nhập số dư tài khoản >= 50,000đ: ");
            balance = scanner.nextLine().trim();
        }while (!balance.matches("[0-9]+")||Double.parseDouble(balance)<50_000);
        //Cập nhật tài khoản vào AccountDao
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountNumber(accountNumber);
        savingsAccount.setBalance(Double.parseDouble(balance));
        savingsAccount.setCustomerId(customerId);
        AccountDao.update(savingsAccount);
        //Cập nhật lịch sử giao dịch vào TransactionDao
        createTransaction(accountNumber,0,Double.parseDouble(balance),TransactionType.DEPOSIT,true,getTime());
    }
    //Tạo ra các giao dịch cùng STK, thực hiện chức năng nâng cao(biểu thức lamda)
    public List<Transaction> getTransactions(){
        return TransactionDao.list().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
    }
    //Hiển thị danh sách giao dịch ra màn hình
    public void displayTransactionList(){
        for(Transaction transaction:getTransactions()){
            System.out.println(transaction);
        }
    }
    //Kiểm tra tài khoản bị trùng
    public boolean isSameAccount(String accountNumber){
        return AccountDao.list()
                .stream()
                .anyMatch(account -> account.getAccountNumber().equals(accountNumber));
    }
//Phục vụ cho việc viết unit test
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if(obj instanceof Account){
            Account otherAccount = ((Account) obj);
            if(otherAccount.getCustomerId().equals(this.customerId)
                    &&(otherAccount.getBalance()==this.balance)
                    &&otherAccount.getAccountNumber().equals(this.accountNumber)){
                return true;
            }
                return false;
        }else{
            return false;
        }
    }
//Phục vụ cho việc viết unit test
    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}
