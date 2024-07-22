package asm04.dao;

import asm03.Transaction;
import asm04.file.BinaryFileService;

import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    public static List<Transaction> transactions=new ArrayList<>();

    private static final String FILE_PATH = "store\\transactions.dat";
    public static void save(List<Transaction> transactions){
        BinaryFileService.writeObject(FILE_PATH,transactions);
    }
    public static List<Transaction> list(){
        return BinaryFileService.readFile(FILE_PATH);
    }
    public static void update(Transaction editTransaction){
        List<Transaction>transactions1 = list();
        List<Transaction> updateTransactions;
//        boolean hasExist = transactions1.stream().anyMatch(transaction -> transaction.getAccountNumber().equals(editTransaction.getAccountNumber()));
//        if(!hasExist){
            updateTransactions=new ArrayList<>(transactions1);
            updateTransactions.add(editTransaction);
            save(updateTransactions);
       // }
    }
}
