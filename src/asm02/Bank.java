package asm02;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Bank {
    private List<Customer> customers;
    private String id;

    public Bank() {
        this.customers = new ArrayList<>();
        this.id = String.valueOf(UUID.randomUUID());
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public boolean isCustomerExist(String customerId){
        for(Customer customer: customers){
            if(customer.getCustomerId().equals(customerId)){
                return true;
            }
        }
        return false;
    }
    //Thêm khách hàng vào list
    public void addCustomer(Customer newCustomer){

       if(!isCustomerExist(newCustomer.getCustomerId())){
            customers.add(newCustomer);
            System.out.println("Đã thêm khách hàng " + newCustomer.getCustomerId() + " vào danh sách");
       }
    }
    //Yêu cầu chức năng nâng cao, tạo addAccount với 2 tham số(customerId, account)
    public void addAccount(String customerId, Account account){
        for(Customer customer: customers){
            if(customer.getCustomerId().equals(customerId)){
                customer.addAccount(account);
                return;
            }
        }
    }
    public Customer getCustomerById(String customerId){
        for(Customer customer: customers){
            if(customer.getCustomerId().equals(customerId)){
                return customer;
            }
        }
        return null;
    }
    //Asm04
    public void addCustomers(String fileName){

    }
    public void showCustomers(){

    }
    public void addSavingAccount(Scanner scanner, String customerId){

    }
    public void tranfers(Scanner scanner, String customerId){

    }
    public void withdraw(Scanner scanner, String customerId){

    }
}
