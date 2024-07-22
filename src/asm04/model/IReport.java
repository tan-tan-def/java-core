package asm04.model;

public interface IReport {
    void logTransfer(String depositeAccount, String receiveAccount, double amount, double balance);
    void logWithdraw( String accountNumber, double amount, double balance);
}
