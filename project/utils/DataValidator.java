package project.utils;

public class DataValidator {
    public static boolean isValidProductID(String productID) {
        return productID.matches("^SP\\d{2}$");
    }
    
    public static boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    public static boolean isValidPrice(double price) {
        return price >= 0;
    }

}
