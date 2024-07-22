package asm03;

import asm02.Account;
import asm02.Customer;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Asm03 {
    private static final String AUTHOR = "FX20821";
    private static final String VERSION = "2023.1.2 (Community Edition) Build #IC-231.9011.34";
    private static final Scanner sc = new Scanner(System.in);
    public static final DigitalBank activeBank = new DigitalBank();
    protected static final Account activeAccount = new Account();
    private static final String CUSTOMER_ID = "002135967485";
    private static final String CUSTOMER_NAME = "Jenifer Huynh";

    public static void main(String[] args) {
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        System.out.println("|    NGAN HANG SO | " + AUTHOR + "@v" + VERSION + "     |");
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        System.out.println("|    1. Thông tin khách hàng                                                        |");
        System.out.println("|    2. Thêm tài khoản ATM                                                          |");
        System.out.println("|    3. Thêm tài khoản tín dụng                                                     |");
        System.out.println("|    4. Rút tiền                                                                    |");
        System.out.println("|    5. Lịch sử giao dịch                                                           |");
        System.out.println("|    0. Thoát                                                                       |");
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        int choice;
        activeBank.addCustomer(CUSTOMER_ID,CUSTOMER_NAME);
        while(true){
            try{
                System.out.print("Nhập chức năng: ");
                choice = sc.nextInt();
                System.out.println("+--------------------------------------------------+");
                sc.nextLine();
                switch (choice){
                    case 1:
                        showCustomer();
                        break;
                    case 2:
                        addSavingsAccount();
                        break;
                    case 3:
                        addLoanAccount();
                        break;
                    case 4:
                        withdraw();
                        break;
                    case 5:
                        transaction();
                        break;
                    case 0:
                        return;//Ngưng chương trình
                    default:
                        System.out.println("Vui lòng nhập các số từ 0 đến 5");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("Vui lòng nhập một số nguyên");
                sc.nextLine();//Xóa bỏ đầu vào không hợp lệ
            }
        }
    }
    public static void showCustomer(){
        Customer customer = activeBank.getCustomerId(CUSTOMER_ID);
        if(customer!=null){
            customer.displayInformation1();
        }
    }
    //Chức năng 2: Thêm tài khoản ATM
    public static void addSavingsAccount(){
        System.out.print("Nhập mã số tài khoản gồm 6 chữ số: ");
        String accountNumber = sc.nextLine();
        if(accountNumber.length()==6&&accountNumber.matches("[0-9]+")){
            boolean foundBalance = false;
            while(!foundBalance){
                System.out.print("Nhập số dư: ");
                String balance = sc.nextLine();
                if(balance.matches("[0-9]+")){
                    double balanceToDouble = Double.parseDouble(balance);
                    if(balanceToDouble>=50_000){
                        SavingsAccount savingsAccount = new SavingsAccount();
                        savingsAccount.setAccountNumber(accountNumber);
                        savingsAccount.setBalance(balanceToDouble);
                        activeBank.addAccount(CUSTOMER_ID,savingsAccount);
                        foundBalance=true;
                    }else{
                        System.out.println("Số dư tối thiểu là 50,000đ");
                    }
                }else{
                    System.out.println("Vui lòng nhập các kí tự số");
                }
            }
        }else{
            System.out.println("Số tài khoản gồm 6 kí tự số, vui lòng thực hiện lại");
            addSavingsAccount();
        }
    }
    //Chức năng 3: Thêm tài khoản tín dụng
    public static void addLoanAccount(){
        System.out.print("Nhập mã số tài khoản gồm 6 chữ số: ");
        String accountNumber = sc.nextLine();
        if(accountNumber.length()==6&&accountNumber.matches("[0-9]+")){
            boolean foundBalance = false;
            while(!foundBalance){
                System.out.print("Nhập số dư: ");
                String balance = sc.nextLine();
                if(balance.matches("[0-9]+")){
                    double balanceToDouble = Double.parseDouble(balance);
                    if(balanceToDouble>=50_000){
                        LoansAccount loanAccount = new LoansAccount();
                        loanAccount.setAccountNumber(accountNumber);
                        loanAccount.setBalance(balanceToDouble);
                        activeBank.addAccount(CUSTOMER_ID,loanAccount);
                        foundBalance=true;
                    }else{
                        System.out.println("Số dư tối thiểu là 50,000đ");
                    }
                }else{
                    System.out.println("Vui lòng nhập các kí tự số");
                }
            }
        }else{
            System.out.println("Số tài khoản gồm 6 kí tự số, vui lòng thực hiện lại");
            addLoanAccount();
        }
    }
    //Chức năng 4: Rút tiền
    public static void withdraw(){
        Scanner sc = new Scanner(System.in);
        boolean checkAccount = false;
            System.out.print("Nhập số tài khoản: ");
            String accountNumber = sc.nextLine();
            for (Customer customer : activeBank.getCustomers()) {
                for (Account account : customer.getAccounts()) {
                    if (account.getAccountNumber().equals(accountNumber)) {
                        checkAccount=true;
                    }
                }
            }
            if(!checkAccount){
                System.out.println("Số tài khoản không hợp lệ, vui lòng nhập lại.");
                withdraw();
            }
            boolean checkAmount = false;
            while (!checkAmount) {
                System.out.print("Nhập số tiền cần rút: ");
                try {
                    double amount = sc.nextDouble();
                    checkAmount = true;
                    activeBank.withdraw(CUSTOMER_ID, accountNumber, amount);
                    return;
                } catch (InputMismatchException e) {
                    System.out.println("Số tiền không hợp lệ, vui lòng nhập lại");
                    sc.nextLine();
                }
            }
    }
    //Chức năng 5: Lịch sử giao dịch
    public static void transaction(){
        showCustomer();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0đ");
        DecimalFormat decimalFormat1 = new DecimalFormat("-#,##0đ");
        Transaction transaction1 = new Transaction();
        // In giao dịch
        boolean isTransactionPrinted = false;
        for (Transaction transaction : activeAccount.getTransactionlist()) {
            System.out.printf("%-4s  %-4s | %20s | %28s\n", "[GD]",
                    transaction.getAccountNumber(), decimalFormat.format(transaction.getBalance()), transaction.getTime());
            System.out.printf("%-4s  %-4s | %20s | %28s\n", "[GD]",
                    transaction.getAccountNumber(), decimalFormat1.format(transaction.getAmount()), transaction.getTime());
            isTransactionPrinted = true; // Đánh dấu đã in giao dịch
        }

        // In ra những tài khoản không thực hiện giao dịch sau khi hoàn thành in giao dịch
        if (isTransactionPrinted) {
            for (Customer customer : activeBank.getCustomers()) {
                for (Account account : customer.getAccounts()) {
                    boolean hasTransaction = false;
                    for (Transaction transaction : activeAccount.getTransactionlist()) {
                        if (transaction.getAccountNumber().equals(account.getAccountNumber())) {
                            hasTransaction = true;
                            break;
                        }
                    }
                    if (!hasTransaction) {
                        System.out.printf("%-4s  %-4s | %20s | %28s\n", "[GD]",
                                account.getAccountNumber(), decimalFormat.format(account.getBalance()), transaction1.getTime());
                    }
                }
            }
        }
    }
}
