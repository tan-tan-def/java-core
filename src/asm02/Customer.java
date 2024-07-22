package asm02;

import asm03.LoansAccount;
import asm03.SavingsAccount;
import asm04.dao.AccountDao;
import asm04.dao.CustomerDao;
import asm04.service.ReceiptSavings;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Account> accounts;

    public Customer() {
        this.accounts = new ArrayList<>();
    }

    public Customer(String name, String customerId) {
        super(name, customerId);
        this.accounts = new ArrayList<>();
    }
    public Customer(List<String> values){
        this(values.get(1), values.get(0));
    }
    public List<Account> getAccounts() {
        return accounts;
    }
    public String isPremium(){
        for(Account account:accounts){
            if(account.isPremiumAccount()){
                return "Premium";
            }
        }
        return "Normal";
    }
    public boolean isCustomerPremium(){
        for(Account account:accounts){
            if(account.isPremiumAccount()){
                return true;
            }
        }
        return false;
    }
    public boolean isAccountExist(String accountNumber){
        for (Account account:accounts){
            if(account.getAccountNumber().equals(accountNumber)){
                return true;
            }
        }
        return  false;
    }
    public void addAccount(Account newAccount){
        if(!isAccountExist(newAccount.getAccountNumber())){
            accounts.add(newAccount);
        }
        else{
            System.out.println("Số tài khoản " + newAccount.getAccountNumber() + " đã tồn tại, tác vụ không thành công");
        }
    }
    public Account getAccountByAccountNumber(String accountNumber){
        for(Account account: accounts){
            if(account.getAccountNumber().equals(accountNumber)){
                return account;
            }
        }
        return null;
    }
    public boolean validateCustomerId(String canCuocCongDan){
        String [] numberProvince = {"001", "002", "004", "006", "008", "010", "011", "012", "014",
                "015", "017", "019", "020", "022", "024", "025", "026", "096",
                "027", "030", "031", "033", "034", "035", "036", "037", "038",
                "040", "042", "044", "045", "046", "048", "049", "051", "052",
                "054", "056", "058", "060", "062", "064", "066", "067", "068",
                "070", "072", "074", "075", "077", "079", "080", "082", "083",
                "084", "086", "087", "089", "091", "092", "093", "094", "095"};
        if(canCuocCongDan.length()==12&&canCuocCongDan.matches("[0-9]+")
                &&checkNumberProvince(canCuocCongDan.substring(0,3),numberProvince)){
            return true;
        }
        return false;
    }
    //Tính tổng số tiền của 1 CCCD
    public double getBalance(){
        double totalBalance =0;
        for(Account account:accounts){
            totalBalance+=account.getBalance();
        }
        return totalBalance;
    }
    //Hiển thị thông tin một khách hàng có định dạng số
    public void displayInformation(){
        int i=0;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##đ");
        String balanceFormatted = decimalFormat.format(getBalance());
        System.out.print(getCustomerId() + "         |    " + getName() + "  |  " + isPremium() + "  |  " + balanceFormatted+ "\n");
        for(Account account:accounts){
            System.out.println((i+=1) + "     " + account.toString());
        }
    }
    public void displayInformation1(){
        int i=0;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0đ");
        String balanceFormatted = decimalFormat.format(getBalance());
        System.out.printf("%-10s | %-20s | %-10s | %15s\n", getCustomerId(), getName(), isPremium(), balanceFormatted);
        for(Account account : accounts){
            String balanceToFormatted = decimalFormat.format(account.getBalance());
            if(account instanceof LoansAccount){
                System.out.printf("%-2s    %-5s | %-20s | %28s\n", i+=1, account.getAccountNumber(), "LOAN", balanceToFormatted);
            }else if(account instanceof SavingsAccount){
                System.out.printf("%-2s    %-5s | %-20s | %28s\n", i+=1, account.getAccountNumber(), "SAVINGS", balanceToFormatted);
            }
        }
    }





    //Asm04
    //Kiểm tra Premium bằng, thực hiện chức năng nâng cao(biểu thức lamda)
    public String premiumOrNormal(String customerId){
        return getAccounts(customerId).stream()
                .filter(account -> account.getBalance()>=10_000_000)
                .findFirst()
                .map(account -> "Premium")
                .orElse("Normal");
    }
    //Tính tổng số tiền trong tài khoản của một người, thực hiện chức năng nâng cao(biểu thức lamda)
    public double getBalances(String customerId){
        return getAccounts(customerId).stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }
    //Thể hiện thông tin danh sách cho Asm04
    public void displayInformation2(){
        int i=0;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0đ");
            System.out.printf("%-10s | %-35s | %-10s | %15s\n", getCustomerId(), getName(),
                    premiumOrNormal(getCustomerId()), decimalFormat.format(getBalances(getCustomerId())));
            for(Account account: getAccounts(getCustomerId())){
                String balanceToFormatted = decimalFormat.format(account.getBalance());
                System.out.printf("%-2s    %-5s | %-35s | %28s\n", i+=1, account.getAccountNumber(), "SAVINGS", balanceToFormatted);
            }
    }
    public void  withdraw(Scanner scanner, String customerId){
        List<Account> accounts = getAccounts(customerId);
        String accountNumber;
        Account account;
        String amount;
            if(!accounts.isEmpty()){
                do{
                    System.out.print("Nhập số tài khoản: ");
                    accountNumber = scanner.nextLine();
                    account= getAccountByAccountNumber(accounts,accountNumber);
                }while (!isAccountInSameCustomer(customerId,accountNumber));
                do{
                    System.out.print("Nhập số tiền rút >= 50,000đ: ");
                    amount = scanner.nextLine();
                }while (!isValueDepositeAmount(amount)
                        ||!isValueRemainAmount(accountNumber,Double.parseDouble(amount))
                        ||!isAcceptNormal(Double.parseDouble(amount)));
                if(account instanceof SavingsAccount){
                    ((SavingsAccount) account).withdrawSecond(Double.parseDouble(amount), accountNumber);
                }
            }else{
                System.out.println("Khách hàng không có tài khoản nào, thao tác không thành công!");
            }
    }
    public void tranfers(Scanner scanner, String customerId){
        List<Account> accounts = getAccounts(customerId);
            if(!accounts.isEmpty()){
                DecimalFormat decimalFormat = new DecimalFormat("#,##0đ");
                String depositeAccount;
                String receiveAccount;
                String depositeAmount;
                String yesOrNo;
                //Nhập số tài khoản của khách hàng đã chọn
                do{
                    System.out.print("Nhập số tài khoản: ");
                    depositeAccount = scanner.nextLine().trim();
                }while (!isAccountInSameCustomer(customerId,depositeAccount));
                //Nhập STK nhận có tồn tại trong danh sách và không được trùng với STK ban đầu
                do{
                    System.out.print("Nhập số tài khoản nhận(exit để thoát): ");
                    receiveAccount = scanner.nextLine().trim();
                    if(!isAccountExist(AccountDao.list(),receiveAccount)){
                        System.out.println("Số tài khoản không tồn tại, vui lòng thực hiện lại");
                    }
                }while (!isAccountExist(AccountDao.list(),receiveAccount)
                        ||isSameAccount(depositeAccount, receiveAccount));
                System.out.println("Gửi tiền đến tài khoản: " + receiveAccount + " | " + getNameByCustomerId(receiveAccount));
                //Nhập số tiền phù hợp và số dư tối thiểu trong tài khoản >=50,000đ
                do{
                    System.out.print("Nhập số tiền cần chuyển: ");
                    depositeAmount = scanner.nextLine().trim();
                }while (!isValueDepositeAmount(depositeAmount)
                        ||!isValueRemainAmount(depositeAccount,Double.parseDouble(depositeAmount)));
                //In xác nhận
                System.out.print("Xác nhận thực hiện chuyển "
                        +decimalFormat.format(Double.parseDouble(depositeAmount))
                        +" từ tài khoản " +"["+depositeAccount+"]"+" đến tài khoản "+"["+receiveAccount+"]"+"(Y/N): ");
                do{
                    yesOrNo = scanner.nextLine().trim();
                    switch (yesOrNo.toLowerCase()){
                        case "y":
                            //Thực hiện cộng tiền và trừ tiền
                            SavingsAccount savingsAccount = new SavingsAccount();
                            savingsAccount.deposite(depositeAccount,Double.parseDouble(depositeAmount));
                            savingsAccount.transfer(receiveAccount,Double.parseDouble(depositeAmount));
                            System.out.println("Chuyển tiền thành công, nhận biên lai giao dịch");
                            //In biên lai giao dịch
                            Account account = getAccountByAccountNumber(AccountDao.list(), depositeAccount);
                            new ReceiptSavings().logTransfer(depositeAccount,receiveAccount,Double.parseDouble(depositeAmount),account.getBalance());
                            break;
                        case "n":
                            System.out.println("+--------See you next time-------+");
                            return;
                        default:
                            System.out.print("Vui lòng nhâp 'Y' để đồng ý, hoặc 'N' để từ chối ");
                    }
                }while (!yesOrNo.toLowerCase().equals("y")
                        &&!yesOrNo.toLowerCase().equals("n"));

            }else{
                System.out.println("Khách hàng không có tài khoản nào, thao tác không thành công!");
            }
    }
    //Tài khoản Normal không được chuyển quá 5,000,000đ
    public boolean isAcceptNormal(double amount){
            if((premiumOrNormal(getCustomerId()).equals("Normal")) && (amount>5_000_000)){
                System.out.println("Không được rút quá 5 triệu");
                return false;
            }
        return true;
    }
    //Kiểm tra tài khoản có tồn tại, thực hiện chức năng nâng cao(biểu thức lamda)
    public boolean isAccountExist(List<Account> accounts,String accountNumber){
        return accounts.stream().anyMatch(account -> account.getAccountNumber().equals(accountNumber));
    }
    //Kiểm tra 2 tài khoản có giống nhau
    public boolean isSameAccount(String depositeAccount, String receiveAccount){
        if(depositeAccount.equals(receiveAccount)){
            System.out.println("Không thể chuyển tiền trên cùng một số tài khoản");
            return true;
        }
        return false;
    }
    //Kiểm tra tài khoản có thuộc của cùng 1 customer
    public boolean isAccountInSameCustomer(String customerId, String accountNumber){
        for(Account account:getAccounts(customerId)){
            if(account.getAccountNumber().equals(accountNumber)){
                return true;
            }
        }
        System.out.println("Vui lòng nhập đúng tài khoản có trong danh sách trên");
        return false;
    }
    //Kiểm tra số tiền cần chuyển của depositeAccount
    public boolean isValueDepositeAmount(String depositeAmount){
        if(depositeAmount.matches("[0-9]+")
                &&(Double.parseDouble(depositeAmount)>=50_000)
                &&(Double.parseDouble(depositeAmount)%10_000==0)){
            return true;
        }
        System.out.println("Vui lòng thực hiện giao dịch với số tiền tối thiểu là 50,000đ và là bội số của 10,000đ");
        return false;
    }

    //Kiểm tra số dư tối thiểu của depositeAccount
    public boolean isValueRemainAmount(String depositeAccount, double amount){
        for(Account account:AccountDao.list()){
            if(account.getAccountNumber().equals(depositeAccount)){
                if(account.getBalance()-amount>=50_000){
                    return true;
                }
            }
        }
        System.out.println("Sồ dư của tài khoản " + depositeAccount +" sau chuyển tối thiểu phải là 50,000đ, tác vụ không thành công");
        return false;
    }
    //Lấy CustomerId từ accountNumber
    public String getCustomerIdByAccountNumber(String accountNumber){
        return AccountDao.list().stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .map(Account::getCustomerId)
                .orElse(null);
    }
    //Lấy Customer từ CustomerId, thực hiện chức năng nâng cao(biểu thức lamda)
    public Customer getCustomerByCustomerId(String accountNumber){
        return CustomerDao.list()
                .stream()
                .filter(customer -> customer.getCustomerId().equals(getCustomerIdByAccountNumber(accountNumber)))
                .findFirst()
                .orElse(null);
    }
    //Lấy ra tất cả account của cùng một customerId, thực hiện chức năng nâng cao(biểu thức lamda)
    public List<Account> getAccounts(String customerId){
        return AccountDao.list()
                .stream()
                .filter(account -> account.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
    //lấy ra account từ trong danh sách, thực hiện chức năng nâng cao(biểu thức lamda)
    public Account getAccountByAccountNumber(List<Account> accounts, String accountNumber){
        return accounts
                .stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
    //Lấy tên từ Customer
    public String getNameByCustomerId(String accountNumber){
        return getCustomerByCustomerId(accountNumber).getName();
    }
    //Hiển thị thông tin các giao dịch của khách hàng hiện tại.
    public void displayTransactionInformation(){
        displayInformation2();
        for(Account account :getAccounts(getCustomerId())){
            account.displayTransactionList();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Customer){
            Customer anotherCustomer = ((Customer) obj);
            if(anotherCustomer.getName().equals(this.getName())
                    &&anotherCustomer.getCustomerId().equals(this.getCustomerId())){
                return true;
            }
            return false;
        }else{
            return false;
        }
    }
}
