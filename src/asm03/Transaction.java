package asm03;

import asm04.common.TransactionType;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private double balance;
    private boolean status;
    private TransactionType type;
    public Transaction(String accountNumber, double amount, double balance) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balance = balance;
    }

    public Transaction(String accountNumber, double amount, double balance, TransactionType type, boolean status, String time) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balance = balance;
        this.type = type;
        this.status=status;
        this.time=time;
    }

    public Transaction() {
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = String.valueOf(UUID.randomUUID());
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
        this.time = formattedDate;
        return time;
    }

    public boolean isStatus() {
        return true;
    }
    //Asm04
    public String getAccountNumber() {
        return accountNumber;
    }
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        //Hiển thị lịch sử giao dịch khi rút hoặc chuyển tiền
        if (amount != 0) {
            return String.format("%4s  %6s | %-12s  | %-16s  %-1s | %21s",
                    "[GD]",accountNumber,type, decimalFormat.format(amount),"đ", time);
        }
        //Hiển thị lịch sử giao dịch khi nạp tiền
        return String.format("%4s  %6s | %-12s  | %-16s  %-1s | %21s",
                "[GD]", accountNumber,type, decimalFormat.format(balance),"đ", time);
    }
    //Phục vụ cho viết test
    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(obj instanceof Transaction){
            Transaction otherTransaction = ((Transaction) obj);
            if(otherTransaction.getAccountNumber().equals(this.accountNumber)
                    &&otherTransaction.getAmount()==this.amount
                    &&otherTransaction.getBalance()==this.balance
                    &&otherTransaction.type.equals(this.type)){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
//Phục vụ cho viết test
    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
