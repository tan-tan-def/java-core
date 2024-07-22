package asm02;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String customerId;

    public User() {
    }

    public User(String name, String customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) throws Exception {
        String [] numberProvince = {"001", "002", "004", "006", "008", "010", "011", "012", "014",
                "015", "017", "019", "020", "022", "024", "025", "026", "096",
                "027", "030", "031", "033", "034", "035", "036", "037", "038",
                "040", "042", "044", "045", "046", "048", "049", "051", "052",
                "054", "056", "058", "060", "062", "064", "066", "067", "068",
                "070", "072", "074", "075", "077", "079", "080", "082", "083",
                "084", "086", "087", "089", "091", "092", "093", "094", "095"};
        if(customerId.length() == 12 && customerId.matches("[0-9]+") && checkNumberProvince(customerId.substring(0,3),numberProvince)){
            this.customerId = customerId;
        }else{
            throw new Exception("CCCD không đúng");
        }
    }
    //Kiểm tra 3 số đầu của CCCD người dùng nhập
    public boolean checkNumberProvince(String cccdPrefix, String[] numberProvince){
        for(String prefix: numberProvince){
            if(prefix.equals(cccdPrefix)){
                return true;
            }
        }
        return false;
    }

    public abstract void displayInformation();
}
