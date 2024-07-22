package asm04.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileService {
    private static final String COMMA_DELIMITER = ",";
    public static List<List<String>> readFile(String fileName){
        List<List<String>> data = new ArrayList<>();
        List<String> row;
        try(BufferedReader file = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line=file.readLine())!=null){
                row = new ArrayList<>();
                String[] value = line.split(COMMA_DELIMITER);
                row.add(value[0]);
                row.add(value[1]);
                data.add(row);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return data;
    }

}
