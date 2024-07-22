package asm04;

import asm02.Bank;
import asm02.Customer;
import asm03.DigitalBank;
import asm04.dao.CustomerDao;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Asm04 {
    private static final String AUTHOR = "FX20821";
    private static final String VERSION = "2023.1.2 (Community Edition) Build #IC-231.9011.34";
    private static final Scanner sc = new Scanner(System.in);
    public static final Bank bank = new DigitalBank();
    public static void main(String[] args) {
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        System.out.println("|    NGAN HANG SO | " + AUTHOR + "@v" + VERSION + "     |");
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        System.out.println("|    1. Xem danh sách khách hàng                                                    |");
        System.out.println("|    2. Nhập danh sách khách hàng                                                   |");
        System.out.println("|    3. Thêm tài khoản ATM                                                          |");
        System.out.println("|    4. Chuyển tiền                                                                 |");
        System.out.println("|    5. Rút tiền                                                                    |");
        System.out.println("|    6. Tra cứu lịch sử giao dịch                                                   |");
        System.out.println("|    0. Thoát                                                                       |");
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        int choice;
        while(true){
            System.out.print("Chọn chức năng: ");
            try{
                choice = sc.nextInt();
                System.out.println("+--------------------------------------------------+");
                sc.nextLine();
                switch (choice){
                    case 1:
                        bank.showCustomers();
                        break;
                    case 2:
                        boolean checkPath = false;
                        while(!checkPath){
                            System.out.print("Nhập đường dẫn đến tệp: ");
                            String path = sc.nextLine();
                            if(path.trim().equals("store/customers.txt")){
                                bank.addCustomers("store\\customers.txt");
                                checkPath=true;
                            }else{
                                System.out.println("Tệp không tồn tại");
                            }
                        }
                        break;
                    case 3:
                        Scanner scanner = new Scanner(System.in);
                        boolean checkCustomerId = false;
                        while (!checkCustomerId){
                            System.out.print("Nhập mã số của khách hàng: ");
                            String customerId = scanner.nextLine().trim();
                            for(Customer customer: CustomerDao.list()){
                                if(customer.getCustomerId().equals(customerId)){
                                    checkCustomerId=true;
                                    bank.addSavingAccount(scanner,customerId);
                                    System.out.println("Tạo tài khoản thành công");
                                    break;
                                }
                            }
                            if (!checkCustomerId) {
                                System.out.println("Không tìm thấy khách hàng "+customerId+", tác vụ không thành công");
                            }
                        }
                        break;
                    case 4:
                        boolean isValueCustomerIdForTranfer = false;
                        while (!isValueCustomerIdForTranfer){
                            System.out.print("Nhập mã số của khách hàng: ");
                            String customerId = sc.nextLine().trim();
                            for(Customer customer:CustomerDao.list()){
                                if(customer.getCustomerId().equals(customerId)){
                                    isValueCustomerIdForTranfer=true;
                                    bank.tranfers(sc,customerId);
                                }
                            }
                            if(!isValueCustomerIdForTranfer){
                                System.out.println("Không tìm thấy khách hàng "+customerId+", tác vụ không thành công");
                            }
                        }
                        break;
                    case 5:
                            System.out.print("Nhập mã số khách hàng: ");
                            String customerId = sc.nextLine().trim();
                            bank.withdraw(sc,customerId);
                        break;
                    case 6:
                        boolean checkCustomerIdForTransfer = false;
                        String customerIdForTransfer;
                        while (!checkCustomerIdForTransfer){
                            System.out.print("Nhập mã số khách hàng: ");
                            customerIdForTransfer = sc.nextLine().trim();
                            for(Customer customer:CustomerDao.list()){
                                if(customer.getCustomerId().equals(customerIdForTransfer)){
                                    checkCustomerIdForTransfer=true;
                                    customer.displayTransactionInformation();
                                }
                            }
                            if(!checkCustomerIdForTransfer){
                                System.out.println("Không tìm thấy khách hàng " + customerIdForTransfer + ", tác vụ không thành công");
                            }
                        }
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Vui lòng chọn kí tự từ 0 đến 6");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("Vui lòng chọn kí tự từ 0 đến 6");
                sc.nextLine();
            }
        }

    }
}
