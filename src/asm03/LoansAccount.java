package asm03;

import asm02.Account;
import asm02.Customer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoansAccount extends Account implements ReportService, Withdraw{
    private final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;// phí rút tiền tài khoản thường
    private final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;// phí rút tiền tài khoản premium

    private final double LOAN_ACCOUNT_MAX_BALANCE = 100_000_000;// Hạn mức tối đa
    private double withdrawFee;
    private double balance;
    private double remainBalance;

    @Override
    public void log(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0đ");
        DecimalFormat decimalFormat1 = new DecimalFormat("-#,##0đ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
            System.out.println("+-------+------------------------+--------+");
            System.out.printf("%30s\n","BIEN LAI GIAO DICH LOAN");
            System.out.printf("%-10s%24s\n","NGAY G/D:", formattedDate);
            System.out.printf("%-9s%25s\n","ATM ID:", "DIGITAL-BANK-ATM 2023");
            System.out.printf("%-9s%25s\n","SO TK:", DigitalCustomer.setAccount);
            System.out.printf("%-9s%25s\n","SO TIEN:", decimalFormat.format(balance));
            System.out.printf("%-13s%21s\n","SO TIEN RUT:",decimalFormat1.format(amount));
            System.out.printf("%-9s%25s\n","SO DU:", decimalFormat.format(remainBalance));
            System.out.printf("%-11s%23s\n","PHI + VAT:", decimalFormat.format(withdrawFee));
            System.out.println("+-------+------------------------+--------+");
    }

    @Override
    public boolean withdraw(double amount){
        if(isAccepted(amount)){
            //In phí giao dịch
            getFee(amount);
            boolean checkRemainBalance = false;
            //Lưu các giá trị để in hóa đơn
            for(Customer customer: Asm03.activeBank.getCustomers()){
                for(Account account: customer.getAccounts()){
                    if(account.getAccountNumber().equals(DigitalCustomer.setAccount)){
                        if(account.getBalance()-amount-withdrawFee>=50_000){
                            checkRemainBalance=true;
                            DigitalCustomer.setAccount = account.getAccountNumber();
                            balance = account.getBalance();
                            account.setBalance(account.getBalance()-amount-withdrawFee);
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
    //Hạn mức tối đa của tài khoản tín dụng
    @Override
    public boolean isAccepted(double amount) {
        if(amount>LOAN_ACCOUNT_MAX_BALANCE){
            System.out.println("Hạn mức tối đa là 100,000,000đ");
            return false;
        }
        return true;

    }
    public double getFee(double amount){
        for(Customer customer: Asm03.activeBank.getCustomers()){
            if(customer.isPremium().equals("Normal")){
                withdrawFee = amount*LOAN_ACCOUNT_WITHDRAW_FEE;
                break;
            } else if (customer.isPremium().equals("Premium")) {
                withdrawFee = amount*LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE;
                break;
            }
        }
        return withdrawFee;
    }
}
