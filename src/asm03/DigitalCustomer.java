package asm03;

import asm02.Account;
import asm02.Customer;
public class DigitalCustomer extends Customer {
    public static String setAccount;
    public void withdraw(String accountNumber, double amount){
        for(Customer customer: Asm03.activeBank.getCustomers()){
            for(Account account:customer.getAccounts()){
                if(accountNumber.equals(account.getAccountNumber())){
                    setAccount=account.getAccountNumber();
                    if(account instanceof LoansAccount){
                        LoansAccount loansAccount = new LoansAccount();
                        loansAccount.withdraw(amount);
                        return;
                    } else if (account instanceof SavingsAccount) {
                        SavingsAccount savingsAccount = new SavingsAccount();
                        savingsAccount.withdraw(amount);
                        return;
                    }
                }
            }
        }
    }
}
