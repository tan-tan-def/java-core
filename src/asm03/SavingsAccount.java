package asm03;

import asm02.Account;
import asm02.Customer;
import asm04.model.ITranfers;
import asm04.common.TransactionType;
import asm04.dao.AccountDao;
import asm04.service.ReceiptSavings;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingsAccount extends Account implements ReportService, Withdraw, Serializable, ITranfers {
    private static final long serialVersionUID = 1L;
    private final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5_000_000;
    private  double balance;
    private  double remainBalance;

    @Override
    public void log(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0đ");
        DecimalFormat decimalFormat1 = new DecimalFormat("-#,##0đ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
           System.out.println("+-------+------------------------+--------+");
           System.out.printf("%30s\n","BIEN LAI GIAO DICH SAVINGS");
           System.out.printf("%-10s%24s\n","NGAY G/D:", formattedDate);
           System.out.printf("%-9s%25s\n","ATM ID:", "DIGITAL-BANK-ATM 2023");
           System.out.printf("%-9s%25s\n","SO TK:",DigitalCustomer.setAccount);
           System.out.printf("%-9s%25s\n","SO TIEN:", decimalFormat.format(balance));
           System.out.printf("%-13s%21s\n","SO TIEN RUT:",decimalFormat1.format(amount));
           System.out.printf("%-9s%25s\n","SO DU:", decimalFormat.format(remainBalance));
           System.out.printf("%-11s%23s\n","PHI + VAT:","0đ");
           System.out.println("+-------+------------------------+--------+");
    }

    @Override
    public boolean withdraw(double amount) {
            if(isAccepted(amount)){
                boolean checkRemainBalance = false;
                for(Customer customer:Asm03.activeBank.getCustomers()){
                    for(Account account: customer.getAccounts()){
                        if(account.getAccountNumber().equals(DigitalCustomer.setAccount)){
                            if(account.getBalance()-amount>=50_000){
                                checkRemainBalance=true;
                                DigitalCustomer.setAccount = account.getAccountNumber();
                                balance = account.getBalance();
                                account.setBalance(account.getBalance()-amount);
                                remainBalance = account.getBalance();
                                log(amount);
                                //Thêm lịch sử giao dịch
                                Transaction transaction = new Transaction(account.getAccountNumber(),amount,balance);
                                Asm03.activeAccount.addTransaction(transaction);
                                return true;
                            }
                        }
                    }
                }
                if(!checkRemainBalance){
                    System.out.println("Số dư sau rút tối thiểu là 50,000đ");
                }
            }
        return false;
    }
    @Override
    public boolean isAccepted(double amount) {
     if(amount<50_000){
         System.out.println("Số tiền rút/chuyển tối thiểu là 50,000đ");
         return false;
     }
     if(amount%10_000!=0){
         System.out.println("Số tiền rút/chuyển phải là bội số của 10,000đ");
         return false;
     }
        return true;
    }
    //Asm04
    //Thực trừ tiền trong tài khoản và update lên file accounts.dat
    public void withdrawSecond(double amount, String accountNumber){
            for(Account account: AccountDao.list()){
                if(account.getAccountNumber().equals(accountNumber)){
                    double balanceFirst = account.getBalance();
                    account.setBalance(account.getBalance()-amount);
                    AccountDao.update(account);
                    System.out.println("Rút tiền thành công, biên lai giao dịch: ");
                    new ReceiptSavings().logWithdraw(accountNumber,amount,account.getBalance());
                    //Lưu lịch sử giao dịch của người rút tiền vào transactions.dat
                    createTransaction(accountNumber, -amount, balanceFirst, TransactionType.WITHDRAW,true, getTime());
                }
        }
    }
    //Cập nhật lại số tiền người nhận
    @Override
    public void transfer(String receiveAccount, double amount) {
        for(Account account: AccountDao.list()){
            if(account.getAccountNumber().equals(receiveAccount)){
                double balanceFirst = account.getBalance();
                account.setBalance(account.getBalance() + amount);
                AccountDao.update(account);
                //Lưu lịch sử giao dịch của người nhận tiền vào transactions.dat
                createTransaction(receiveAccount, amount, balanceFirst, TransactionType.TRANSFER, true, getTime());
            }
        }
    }
    //Cập nhật số tiền người gửi
    @Override
    public void deposite(String depositeAccount, double amount){
        for(Account account:AccountDao.list()){
            if(account.getAccountNumber().equals(depositeAccount)){
                double balanceFirst = account.getBalance();
                account.setBalance(account.getBalance()-amount);
                AccountDao.update(account);
                //Lưu lịch sử giao dịch của người gửi tiền vào transactions.dat
                createTransaction(depositeAccount, -amount, balanceFirst, TransactionType.TRANSFER,true, getTime());
            }
        }
    }

}
