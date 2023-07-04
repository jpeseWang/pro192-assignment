package project.utils;
import java.util.function.Predicate;

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
    public static boolean isValidCustomerID(String customerID) {
        return customerID.matches("^KH\\d{2}$");
    }
    public static boolean isValidPhone(String phone) {
        return phone.matches("^09\\d{8}$");
    }

    public static boolean isValidDateOfBirth(String dateOfBirth) {
        return dateOfBirth.matches("^\\d{2}/\\d{2}/\\d{4}$");
    }

    public static boolean isValidEmployeeID(String customerID) {
        return customerID.matches("^NVGH\\d{4}$");
    }

}
