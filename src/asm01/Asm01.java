package asm01;

import java.util.*;

public class Asm01 {
    public static final String AUTHOR = "FX20821";
    public static final String VERSION = "2023.1.2 (Community Edition) Build #IC-231.9011.34";
    public static void main(String[] args) {
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        System.out.println("|    NGAN HANG SO | " + AUTHOR + "@v" + VERSION + "     |");
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        System.out.println("|    1. Nhap CCCD                                                                   |");
        System.out.println("|    0. Thoat                                                                       |");
        System.out.println("+----------------+-----------------------------------------------+------------------+");
        repeatFunction();
    }
    //repeatFunction thực hiện thao tác Nhập CCCD hoặc Thoát
    public static void repeatFunction(){
        Scanner sc = new Scanner(System.in);
        try{
            System.out.print("Chuc nang: ");
            int function = sc.nextInt();
            if(function==1){
                verrificationCode();//Nhập mã xác thực khi chọn 1
            } else if (function==0) {
                System.out.println("Đã thoát");
                System.exit(0);
            } else {
                System.out.println("");
                System.out.println("Lựa chọn không hợp lệ, vui lòng nhập 1 hoặc 0 ");
                repeatFunction();//Đệ quy lặp lại thông báo chọn chức năng phù hợp
            }
        }catch (InputMismatchException e){
            System.out.println("");
            System.out.println("Lỗi: Bạn đã nhập không phải là một số nguyên. Vui lòng thử lại.");
            repeatFunction();//Đệ quy lặp lại thông báo chọn chức năng phù hợp
        }finally {
            sc.close();
        }
    }
    //verrificationCode thao tác nhập mã xác thực
    public static void verrificationCode(){
        Random random= new Random();
        Scanner sc= new Scanner(System.in);
        try{
        System.out.println("Lựa chọn chức năng nhập mã xác thực:\n1. EASY\n2. HARD ");
        System.out.print("Chọn chức năng: ");
        int function = sc.nextInt();
            switch (function){
                case 1:
                    int randomInt = random.nextInt(900)+100;//random các số 100-999
                    System.out.println("Mã xác thực: " + randomInt);
                    System.out.print("Nhập mã xác thực: ");

                    int verificationCode = sc.nextInt();
                    if(verificationCode==randomInt){
                        enterNationalIdentityCard();//Hàm nhập CCCD, xác thực CCCD, thực hiện thao tác khi CCCD hợp lệ
                    }else{
                        System.out.println("Mã xác thực không đúng. Vui lòng nhập lại");
                        verrificationCode();//Đệ quy để lặp lại quá trình nếu mã xác thực không đúng
                    }
                    break;
                case 2:
                    final String CHARACTERS =
                            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                    StringBuilder verticationCodes = new StringBuilder();
                    for(int i=0; i<6; i++){
                        int randomIndex = random.nextInt(CHARACTERS.length());
                        char randomChar = CHARACTERS.charAt(randomIndex);
                        verticationCodes.append(randomChar);
                    }
                    String theFinallyVerticationCodes = verticationCodes.toString();
                    System.out.println("Mã xác thực: " + theFinallyVerticationCodes);
                    System.out.print("Nhập mã xác thực: ");
                    String enterVerticationCode = sc.next();
                    if(enterVerticationCode.equals(theFinallyVerticationCodes)){
                        enterNationalIdentityCard();//Hàm nhập CCCD, xác thực CCCD, thực hiện thao tác khi CCCD hợp lệ
                    }else{
                        System.out.println("Mã xác thực không đúng. Vui lòng nhập lại");
                       verrificationCode();//Đệ quy để lặp lại quá trình nếu mã xác thực không đúng
                    }
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập 1 hoặc 2 ");
                    verrificationCode();//Đệ quy để lặp lại quá trình nếu mã xác thực không đúng
                    break;
            }
        }catch (InputMismatchException e){
            System.out.println("Lỗi: Bạn đã nhập không phải là một số nguyên. Vui lòng thử lại.");
            verrificationCode();//Đệ quy để lại lại quá trình nếu mã xác thực không đúng
        }finally {
            sc.close();
        }
        }

    //Hàm nhập CCCD, xác thực CCCD, thực hiện thao tác khi CCCD hợp lệ
    public static void enterNationalIdentityCard(){
        String[] notNumberProvince = {"003", "005", "007", "009", "013", "016",
                "018", "021", "023", "028", "029", "032",
                "039", "041", "043", "047", "050", "053",
                "055", "057", "059", "061", "063", "065",
                "069", "071", "073", "076", "078", "081", "085", "088", "090"};//Chưa dùng nhưng để trước để phòng hờ
        String [] numberProvince = {"001", "002", "004", "006", "008", "010", "011", "012", "014",
                "015", "017", "019", "020", "022", "024", "025", "026", "096",
                "027", "030", "031", "033", "034", "035", "036", "037", "038",
                "040", "042", "044", "045", "046", "048", "049", "051", "052",
                "054", "056", "058", "060", "062", "064", "066", "067", "068",
                "070", "072", "074", "075", "077", "079", "080", "082", "083",
                "084", "086", "087", "089", "091", "092", "093", "094", "095"};
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số CCCD: ");
        String nationalIdentityCard = sc.nextLine();//Nhập CCCD bằng String nên không có ngoại lệ số học
        //Kiểm tra độ dài chuỗi CCCD bằng 12 và phải tập hợp từ những số 0-9
        if(nationalIdentityCard.length() == 12 && nationalIdentityCard.matches("[0-9]+")){
            while(true){
                //Kiểm tra xem 3 số đầu CCCD có đúng với mã của 63 tỉnh thành được quy định trong luật không
                if(!checkNumberProvince(nationalIdentityCard.substring(0,3), numberProvince)){
                    System.out.println("CCCD không hợp lệ\nVui lòng nhập lại hoặc nhấp 'No' để thoát");
                    repeatEnterValue();//hàm xử lí khi chọn "1. Nhập lại" hoặc "2.No" để thoát khi CCCD không hợp lệ
                }
                System.out.println("| 1.Kiểm tra nơi sinh\n" +
                        "| 2.Kiểm tra năm sinh, giới tính\n" +
                        "| 3.Kiểm tra số ngẫu nhiên\n" +
                        "| 0.Thoát");
                functionOfId(nationalIdentityCard);//Thực hiện in thông tin khi nhập CCCD và xủ lí các ngoại lệ
            }

        }else{
            System.out.println("Số CCCD không hợp lệ\nVui lòng nhập lại hoặc nhấp 'No' để thoát");
            repeatEnterValue();//hàm xử lí khi chọn "1. Nhập lại" hoặc "2.No" để thoát khi CCCD không hợp lệ
        }
        sc.close();
    }
    //Thực hiện in thông tin khi nhập CCCD và xủ lí các ngoại lệ với biến số là CCCD
    public static void functionOfId(String nationalIdentityCard){
        Map<String, String> provinceMap = new HashMap<String, String>();
        provinceMap.put("001", "Hà Nội");
        provinceMap.put("002", "Hà Giang");
        provinceMap.put("004", "Cao Bằng");
        provinceMap.put("006", "Bắc Kạn");
        provinceMap.put("008", "Tuyên Quang");
        provinceMap.put("010", "Lào Cai");
        provinceMap.put("011", "Điện Biên");
        provinceMap.put("012", "Lai Châu");
        provinceMap.put("014", "Sơn La");
        provinceMap.put("015", "Yên Bái");
        provinceMap.put("017", "Hòa Bình");
        provinceMap.put("019", "Thái Nguyên");
        provinceMap.put("020", "Lạng Sơn");
        provinceMap.put("022", "Quảng Ninh");
        provinceMap.put("024", "Bắc Giang");
        provinceMap.put("025", "Phú Thọ");
        provinceMap.put("026", "Vĩnh Phúc");
        provinceMap.put("027", "Bắc Ninh");
        provinceMap.put("030", "Hải Dương");
        provinceMap.put("031", "Hải Phòng");
        provinceMap.put("033", "Hưng Yên");
        provinceMap.put("034", "Thái Bình");
        provinceMap.put("035", "Hà Nam");
        provinceMap.put("036", "Nam Đinh");
        provinceMap.put("037", "Ninh Bình");
        provinceMap.put("038", "Thanh Hóa");
        provinceMap.put("040", "Nghệ An");
        provinceMap.put("042", "Hà Tĩnh");
        provinceMap.put("044", "Quảng Bình");
        provinceMap.put("045", "Quảng Trị");
        provinceMap.put("046", "Thừa Thiên Huế");
        provinceMap.put("048", "Đà Nẵng");
        provinceMap.put("049", "Quảng Nam");
        provinceMap.put("051", "Quảng Ngãi");
        provinceMap.put("052", "Bình Định");
        provinceMap.put("054", "Phú Yên");
        provinceMap.put("056", "Khánh Hòa");
        provinceMap.put("058", "Ninh Thuận");
        provinceMap.put("060", "Bình Thuận");
        provinceMap.put("062", "Kon Tum");
        provinceMap.put("064", "Gia Lai");
        provinceMap.put("066", "Đắk Lắk");
        provinceMap.put("067", "Đắk Nông");
        provinceMap.put("068", "Lâm Đồng");
        provinceMap.put("070", "Bình Phước");
        provinceMap.put("072", "Tây Ninh");
        provinceMap.put("074", "Bình Dương");
        provinceMap.put("075", "Đồng Nai");
        provinceMap.put("077", "Bà Rịa-Vũng Tàu");
        provinceMap.put("079", "Hồ Chí Minh");
        provinceMap.put("080", "Long An");
        provinceMap.put("082", "Tiền Giang");
        provinceMap.put("083", "Bến Tre");
        provinceMap.put("084", "Trà Vinh");
        provinceMap.put("086", "Vĩnh Long");
        provinceMap.put("087", "Đồng Tháp");
        provinceMap.put("089", "An Giang");
        provinceMap.put("091", "Kiên Giang");
        provinceMap.put("092", "Cần Thơ");
        provinceMap.put("093", "Hậu Giang");
        provinceMap.put("094", "Sóc Trăng");
        provinceMap.put("095", "Bạc Liêu");
        provinceMap.put("096", "Cà Mau");
        Scanner sc = new Scanner(System.in);
        try{
            System.out.print("Chọn chức năng: ");
            int numberOfFunction = sc.nextInt();
            if(numberOfFunction==1){
                System.out.println("Nơi sinh: " + provinceMap.get(nationalIdentityCard.substring(0,3)));//lấy dữ liệu từ Map với 3 số đầu CCCD
            } else if (numberOfFunction==2) {
                char genderByNumber = nationalIdentityCard.charAt(3);//Lấy kí tự thứ 3 để xác định giới tính, năm sinh
                int gender = Character.getNumericValue(genderByNumber);//Chuyển kí tự thành số để dễ so sánh
                String yearByNumber = nationalIdentityCard.substring(4,6);//căt 2 kí tự 4,5 để in năm sinh
                if(gender==0){
                    System.out.println("Giới tính: Nam | Năm sinh: 19"+yearByNumber );
                } else if (gender==1) {
                    System.out.println("Giới tính: Nữ | Năm sinh: 19"+yearByNumber );
                } else if (gender==2) {
                    System.out.println("Giới tính: Nam | Năm sinh: 20"+yearByNumber );
                } else if (gender==3) {
                    System.out.println("Giới tính: Nữ | Năm sinh: 20"+yearByNumber );
                } else if (gender==4) {
                    System.out.println("Giới tính: Nam | Năm sinh: 21"+yearByNumber );
                } else if (gender==5) {
                    System.out.println("Giới tính: Nữ | Năm sinh: 21"+yearByNumber );
                } else if (gender==6) {
                    System.out.println("Giới tính: Nam | Năm sinh: 22"+yearByNumber );
                } else if (gender==7) {
                    System.out.println("Giới tính: Nữ | Năm sinh: 22"+yearByNumber );
                } else if (gender==8) {
                    System.out.println("Giới tính: Nam | Năm sinh: 23"+yearByNumber );
                } else if (gender==9) {
                    System.out.println("Giới tính: Nữ | Năm sinh: 23"+yearByNumber );
                }
            } else if (numberOfFunction==3) {
                String lastSixNumber = nationalIdentityCard.substring(6,12);
                System.out.println("Số ngẫu nhiên: " + lastSixNumber );
            } else if (numberOfFunction==0) {
                System.out.println("Đã thoát");
                System.exit(0);
            }else{
                System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại");
                functionOfId(nationalIdentityCard);//Đệ quy thực hiện lại quy trình nếu nhập các số khác 0, 1, 2, 3
            }
        }catch (InputMismatchException e){
            System.out.println("Lỗi: Bạn đã nhập không phải là một số nguyên. Vui lòng thử lại.");
            sc.nextLine();//Xóa dòng chứa dữ liệu không hợp lệ khỏi Scanner
            functionOfId(nationalIdentityCard);//Đệ quy thực hiện lại quy trình nếu nhập các số khác 0, 1, 2, 3
        }
    }
    //hàm thực hiện khi nhập sai CCCD
    public static void repeatEnterValue(){
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println("| 1. Nhập lại\n| 2. No");
            System.out.print("Mời quý khách đưa ra lựa chọn: ");
            int enter = sc.nextInt();
            if(enter==1){
                enterNationalIdentityCard();//Hàm nhập CCCD, xác thực CCCD, thực hiện thao tác khi nhập 1
            } else if (enter==2) {
                System.out.println("Đã thoát");
                System.exit(0);
            }else{
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại hoặc nhấp 'No' để thoát");
                repeatEnterValue();//đệ quy
            }
        }catch (InputMismatchException e){
            System.out.println("Lỗi: Bạn đã nhập không phải là một số nguyên. Vui lòng thử lại.");
            sc.nextLine();// Xóa dòng chứa dữ liệu không hợp lệ khỏi Scanner
            repeatEnterValue();//đệ quy
        }finally {
            sc.close();
        }
    }

    //hàm kiem tra 3 số đầu của CCCD có giống với 3 số đại điện 63 tỉnh thành
    public static boolean checkNumberProvince(String cccdPrefix, String[] numberProvince){
        for(String prefix: numberProvince){
            if(prefix.equals(cccdPrefix)){
                return true;
            }
        }
        return false;
    }
}
