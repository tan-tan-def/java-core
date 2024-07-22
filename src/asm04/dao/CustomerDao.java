package asm04.dao;

import asm02.Customer;
import asm04.file.BinaryFileService;

import java.util.List;

public class CustomerDao {
    private static final String FILE_PATH = "store\\customers.dat";
    public static void save(List<Customer> customers){
        BinaryFileService.writeObject(FILE_PATH,customers);
    }
    public static List<Customer> list(){
        return BinaryFileService.readFile(FILE_PATH);
    }
}
