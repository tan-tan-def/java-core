package asm04.service;

import asm04.model.IReport;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptSavings implements IReport {
    @Override
    public void logTransfer(String depositeAccount, String receiveAccount, double amount, double balance) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0đ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
        System.out.println("+----------------------------------------------+");
        System.out.printf("| %34s%12s\n","BIEN LAI GIAO DICH SAVINGS"," |");
        System.out.printf("| %9s%35s |\n","NGÀY G/D:", formattedDate);
        System.out.printf("| %7s%37s |\n","ATM ID:", "DIGITAL-BANK-ATM 2023");
        System.out.printf("| %6s%38s |\n","SỐ TK:",depositeAccount);
        System.out.printf("| %11s%33s |\n","SỐ TK NHẬN:",receiveAccount);
        System.out.printf("| %15s%29s |\n","SỐ TIỀN CHUYỂN:", decimalFormat.format(amount));
        System.out.printf("| %9s%35s |\n","SỐ DƯ TK:",decimalFormat.format(balance));
        System.out.printf("| %10s%34s |\n","PHI + VAT:","0đ");
        System.out.println("+----------------------------------------------+");
    }
    @Override
    public void logWithdraw( String accountNumber, double amount, double balance) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0đ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
        System.out.println("+----------------------------------------------+");
        System.out.printf("| %34s%12s\n","BIEN LAI GIAO DICH SAVINGS"," |");
        System.out.printf("| %9s%35s |\n","NGÀY G/D:", formattedDate);
        System.out.printf("| %7s%37s |\n","ATM ID:", "DIGITAL-BANK-ATM 2023");
        System.out.printf("| %6s%38s |\n","SỐ TK:",accountNumber);
        System.out.printf("| %12s%32s |\n","SỐ TIỀN RÚT:", decimalFormat.format(amount));
        System.out.printf("| %9s%35s |\n","SỐ DƯ TK:",decimalFormat.format(balance));
        System.out.printf("| %10s%34s |\n","PHI + VAT:","0đ");
        System.out.println("+----------------------------------------------+");
    }
}
