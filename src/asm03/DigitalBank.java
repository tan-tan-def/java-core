package asm03;

import asm02.Account;
import asm02.Bank;
import asm02.Customer;
import asm04.dao.CustomerDao;
import asm04.exception.CustomerIdNotValidException;
import asm04.file.TextFileService;

import java.util.*;

public class DigitalBank extends Bank {
    public Customer getCustomerId(String customerId){
        for(Customer customer: getCustomers()){
            if(customer.getCustomerId().equals(customerId)){
                return customer;
            }
        }
        return null;
    }
    public void addCustomer(String customerId, String name){
        Customer customer = new Customer(name,customerId);
        addCustomer(customer);
    }
    public void withdraw(String customerId, String accountNumber, double amount){
        for(Customer customer: this.getCustomers()){
            if(customerId.equals(customer.getCustomerId())){
                DigitalCustomer digitalCustomer = new DigitalCustomer();
                digitalCustomer.withdraw(accountNumber,amount);
                return;
                }
        }
    }
    //Asm04
    //Hiển thị danh sách khách hàng
    @Override
    public void showCustomers(){
            if(CustomerDao.list().isEmpty()){
                System.out.println("Chưa có khách hàng nào trong danh sách!");
            }else {
                for(Customer customer:CustomerDao.list()){
                    customer.displayInformation2();
                }
            }
    }
    //Thêm khách hàng vào file customer.dat
    @Override
    public void addCustomers(String fileName){
        List<List<String>> datas = TextFileService.readFile(fileName);
        Set<String> cccdSet = new HashSet<>();
        for(List<String> data:datas){
            if(cccdSet.contains(data.get(0))){
                System.out.println("Khách hàng " + data.get(0) + " đã tồn tại, thêm khách hàng không thành công");
            }else{
                cccdSet.add(data.get(0));
                Customer customer = new Customer();
                //Bắt khi id customer ko hợp lệ
                try{
                    validateCustomerId(data.get(0).trim());
                    customer.setCustomerId(data.get(0).trim());
                }catch (CustomerIdNotValidException e){
                    System.out.println("Khách hàng " + data.get(0) + " không hợp lệ, thêm khách hàng không thành công");
                    continue;
                } catch (Exception e) {
                    System.out.println("Khách hàng " + data.get(0) + " không hợp lệ, thêm khách hàng không thành công");
                    continue;
                }
                    customer.setName(data.get(1).trim());
                    addCustomer(customer);
            }
        }
        CustomerDao.save(getCustomers());
    }
    //Thêm tài khoản Savings
    @Override
    public void addSavingAccount(Scanner scanner, String customerId){
        Account account = new Account();
        account.input(scanner,customerId);
    }
    //Chuyển tiền qua các tài khoản
    @Override
    public void tranfers(Scanner scanner, String customerId){
        scanner = new Scanner(System.in);
        Customer customer = getCustomerById(CustomerDao.list(),customerId);
        if(customer!=null){
            customer.displayInformation2();
            customer.tranfers(scanner,customerId);
        }
    }
    //Chức năng rút tiền
    @Override
    public void withdraw(Scanner scanner, String customerId){
        Customer customer = getCustomerById(CustomerDao.list(), customerId);
        if(customer!=null){
            customer.displayInformation2();
            customer.withdraw(scanner, customerId);
        }else{
            System.out.println("Không tìm thấy khách hàng "+customerId+", tác vụ không thành công");
        }
    }
    //Kiểm tra 3 số đầu của CCCD người dùng nhập, thực hiện chức năng nâng cao(biểu thức lamda)
    public boolean checkNumberProvince(String cccdPrefix, String[] numberProvince){
        return Arrays.stream(numberProvince)
                .anyMatch(prefix -> prefix.equals(cccdPrefix));
    }
    //Thực hiện kiểm tra ngoại lệ khi lưu số CCCD
    public void validateCustomerId(String customerId) throws CustomerIdNotValidException {
        String [] numberProvince = {"001", "002", "004", "006", "008", "010", "011", "012", "014",
                "015", "017", "019", "020", "022", "024", "025", "026", "096",
                "027", "030", "031", "033", "034", "035", "036", "037", "038",
                "040", "042", "044", "045", "046", "048", "049", "051", "052",
                "054", "056", "058", "060", "062", "064", "066", "067", "068",
                "070", "072", "074", "075", "077", "079", "080", "082", "083",
                "084", "086", "087", "089", "091", "092", "093", "094", "095"};
        if(customerId.length() != 12 || !customerId.matches("[0-9]+")
                ||!checkNumberProvince(customerId.substring(0,3),numberProvince)) {
            throw new CustomerIdNotValidException("CCCD không đúng");
        }
    }
    //Kiểm tra tài khoản bị trùng bằng accountNumber, thực hiện chức năng nâng cao(biểu thức lamda)
    public boolean isAccountExisted(List<Account> accountList, String accountNumber){
        return accountList.stream().anyMatch(account -> account.getAccountNumber().equals(accountNumber));
    }
    //Kiểm tra tài khoản bị trùng bằng đối tượng Account, thực hiện chức năng nâng cao(biểu thức lamda)
    public boolean isAccountExisted(List<Account> accountsList, Account newAccount){
        return accountsList.stream().anyMatch(account -> account.getAccountNumber().equals(newAccount.getAccountNumber()));
    }
    //lấy ra một customer có id bằng id cho trước, thực hiện chức năng nâng cao(biểu thức lamda)
    public Customer getCustomerById(List<Customer> customerList, String customerId){
        return customerList.stream()
                .filter(customer -> customer.getCustomerId().equals(customerId))
                .findFirst()
                .orElse(null);

    }
    //Kiểm tra khách hàng có tồn tại, thực hiện chức năng nâng cao(biểu thức lamda)
    public boolean isCustomerExisted(List<Customer> customers, Customer newCustomer) {
        return customers.stream().anyMatch(customer -> customer.getCustomerId().equals(newCustomer.getCustomerId()));
    }
}
