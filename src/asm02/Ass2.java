package asm02;

import java.text.Normalizer;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Ass2 {
    private static final Bank bank = new Bank();
    private static final String AUTHOR = "FX20821";
    private static final String VERSION = "2023.1.2 (Community Edition) Build #IC-231.9011.34";
    public static void main(String[] args) {
            System.out.println("+----------------+-----------------------------------------------+------------------+");
            System.out.println("|    NGAN HANG SO | " + AUTHOR + "@v" + VERSION + "     |");
            System.out.println("+----------------+-----------------------------------------------+------------------+");
            System.out.println("+----------------+-----------------------------------------------+------------------+");
            System.out.println("|    1. Thêm khách hàng                                                             |");
            System.out.println("|    2. Thêm tài khoản cho khách hàng                                               |");
            System.out.println("|    3. Hiển thị danh sách khách hàng                                               |");
            System.out.println("|    4. Tìm theo CCCD                                                               |");
            System.out.println("|    5. Tìm theo tên khách hàng                                                     |");
            System.out.println("|    0. Thoát                                                                       |");
            System.out.println("+----------------+-----------------------------------------------+------------------+");
        Scanner sc = new Scanner(System.in);
        int choice;
            while(true){
                try{
                    System.out.print("Nhập chức năng: ");
                    choice = sc.nextInt();
                    System.out.println("+--------------------------------------------------+");
                    sc.nextLine();
                    switch (choice){
                        case 1:
                            inputInformationCustomer();
                            break;
                        case 2:
                            inputAccountCustomer();
                            break;
                        case 3:
                            displayInformationCustomer();
                            break;
                        case 4:
                            searchCustomerByCCCD();
                            break;
                        case 5:
                            searchCustomerByName();
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
    //Thực hiện chức năng 1: Thêm khách hàng
    public static void inputInformationCustomer() {
        Scanner sc = new Scanner(System.in);
        Customer customer = new Customer();

        System.out.print("Nhập tên khách hàng: ");
        String customerName = sc.nextLine().trim();
        customer.setName(customerName);

        boolean validCccd = false;
        while(!validCccd){
            System.out.print("Nhập số CCCD: ");
            String nationalIdentityCard = sc.nextLine().trim();
            try{
                customer.setCustomerId(nationalIdentityCard);
                validCccd=true;// Khi số CCCD hợp lệ, thoát khỏi vòng lặp
            }catch (Exception e){
                System.out.println("Số CCCD không hợp lệ, vui lòng nhập lại");
            }
        }

        bank.addCustomer(customer);
    }
    //Thực hiện chức năng 2: Thêm tài khoản
    public static void inputAccountCustomer(){
        Scanner sc = new Scanner(System.in);
        Account account = new Account();
        if(bank.getCustomers().isEmpty()){
            System.out.println("Vui lòng thực hiện chức năng 1 trước khi thực hiện các chức năng khác");
        }else{
            System.out.print("Nhập CCCD khách hàng: ");
            String enterNationalIdentityCard = sc.nextLine().trim();
            boolean foundCustomer = false;
            for(Customer customer1: bank.getCustomers()){
                if(customer1.getCustomerId().equals(enterNationalIdentityCard)){
                    boolean validAccountNumber = false;
                    while (!validAccountNumber){
                        System.out.print("Nhập mã STK gồm 6 chữ số: ");
                        String accountNumber = sc.nextLine().trim();
                        //báo lỗi khi nhập cùng 1 số tài khoản
                        for(Customer customer: bank.getCustomers()){
                            for(Account account1: customer.getAccounts()){
                                if(accountNumber.equals(account1.getAccountNumber())){
                                    System.out.println("Số tài khoản đã tồn tại, vui lòng thực hiện lại.");
                                    return;
                                }
                            }
                        }
                        //
                        if(accountNumber.length()==6 && accountNumber.matches("[0-9]+")){
                            validAccountNumber=true;
                            boolean validBalance = false;
                            while (!validBalance){
                                System.out.print("Nhập số dư: ");
                                String inputBalance = sc.nextLine().trim();
                                if(inputBalance.matches("[0-9]+")){
                                    double balanceToNumber = Double.parseDouble(inputBalance);
                                    if(balanceToNumber>=50_000){
                                        account.setAccountNumber(accountNumber);
                                        account.setBalance(balanceToNumber);
                                        customer1.addAccount(account);
                                        validBalance = true;
                                    }else{
                                        System.out.println("Số dư tối thiểu là 50.000, vui lòng nhập lại");
                                    }
                                }else{
                                    System.out.println("Số dư chỉ chứa kí tự số, vui lòng thử lại");
                                }
                            }
                        }else{
                            System.out.println("STK chỉ chứa 6 kí tự số, vui lòng nhập lại");
                        }
                    }
                    foundCustomer=true;
                    break;
                }
            }
            if(!foundCustomer){
                System.out.println("Không tìm thấy CCCD, vui lòng nhập lại");
                inputAccountCustomer();
            }
        }
    }
    //Thực hiện chức năng 3: Hiển thị danh sách
    public static void displayInformationCustomer(){
        if(bank.getCustomers().isEmpty()){
            System.out.println("Vui lòng thực hiện chức năng 1 trước khi thực hiện các chức năng khác");
        }else{
            for(Customer customer1: bank.getCustomers()){
                customer1.displayInformation();
            }
        }
    }
    //Thực hiện chức năng 4: Tìm theo CCCD(tìm chính xác)
    public static void searchCustomerByCCCD(){
        Scanner sc = new Scanner(System.in);
        if(bank.getCustomers().isEmpty()){
            System.out.println("Vui lòng thực hiện chức năng 1 trước khi thực hiện các chức năng khác");
        }else{
            System.out.print("Nhập chính xác CCCD: ");
            String checkCCCD = sc.nextLine().trim();
            boolean foundCustomerId = false;
            for(Customer customer: bank.getCustomers()){
                if(checkCCCD.equals(customer.getCustomerId())){
                    customer.displayInformation();
                    foundCustomerId=true;
                    break;
                }
            }
            if(!foundCustomerId){
                System.out.println("Không tìm thấy CCCD, vui lòng thực hiện lại");
                searchCustomerByCCCD();
            }
        }
    }
    //Thực hiện chức năng 5: Tìm theo tên(tìm gần đúng)
    public static void searchCustomerByName(){
        Scanner sc = new Scanner(System.in);
        if(bank.getCustomers().isEmpty()){
            System.out.println("Vui lòng thực hiện chức năng 1 trước khi thực hiện các chức năng khác");
        }else{
            System.out.print("Vui lòng nhập họ tên đầy đủ: ");
            String checkName = sc.nextLine().replaceAll("\\s+", "").trim();
            String newCheckName = removeDiacriticalMarks(checkName);//bỏ hết dấu, khoảng trắng chuỗi mớí nhập
            boolean foundCustomerName = false;
            for(Customer customer: bank.getCustomers()){
                String newGetName = removeDiacriticalMarks(customer.getName().replaceAll("\\s+", ""));//bỏ hết dấu, khoảng trắng tên khách hàng
                if(newCheckName.equalsIgnoreCase(newGetName)){
                  customer.displayInformation();
                  foundCustomerName=true;

                }
            }
           if(!foundCustomerName){
               System.out.println("Không tìm thấy tên người dùng, vui lòng thực hiện lại");
               searchCustomerByName();
           }
        }
    }

    //Hàm bỏ dấu tiếng việt
    public static String removeDiacriticalMarks(String str) {
        String normalizedStr = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedStr).replaceAll("");
    }
}