
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import project.models.Customer;
import project.models.Product;

public class CustomerService extends Menu {

    private Scanner scanner = new Scanner(System.in);
    private List<Customer> customerList;

    public CustomerService() {
        customerList = new ArrayList<>();
        //customer.loadDataFromFile();
    }

    public void execute() {
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    displayAllCustomers();
                    break;
                case 3:
                    writeDataToFile();
                    break;
                case 4:
                    searchCustomer();
                    break;
                case 5:
                    deleteCustomer();
                    break;
                case 6:
                    updateCustomer();
                    break;
                case 7:
                    sortCustomers();
                    break;
                case 0:
                    System.out.println("\t\t\tExiting...");
                    break;
                default:
                    System.out.println("\t\t\tInvalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
    }

    // Display menu
    private void displayMenu() {
        System.out.println("\t\t\t----- Customer Management System -----");
        System.out.println("\t\t\t1. Add new customer");
        System.out.println("\t\t\t2. Display all customers");
        System.out.println("\t\t\t3. Write data to file");
        System.out.println("\t\t\t4. Search customer");
        System.out.println("\t\t\t5. Delete a customer");
        System.out.println("\t\t\t6. Update phone and date of birth");
        System.out.println("\t\t\t7. Sort customer list by different criteria");
        System.out.println("\t\t\t0. Exit");
        System.out.print("Enter your choice: ");
    }

    // Display customers
    private void displayAllCustomers() {
        System.out.println("\t\t\t----- All Customers -----");
        if (!customerList.isEmpty()) {
            for (Customer customer : customerList) {
                System.out.println(customer);
                System.out.println("\t\t\t--------------------------");
            }
        } else {
            System.out.println("\t\t\tNo customers found.");
        }
    }

    // Add customers
    private void addCustomer() {
        System.out.println("\t\t\t----- Add New Customer -----");
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        if (!isValidCustomerID(customerID)) {
            System.out.println("\t\t\tInvalid Customer ID. Customer ID must have a length of 4 and start with 'KH'.");
            return;
        }
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        if (!isValidName(name)) {
            System.out.println("\t\t\tInvalid Name. Name must not contain numbers or spaces.");
            return;
        }
        System.out.print("Enter Phone: ");
        String phone;
        try {
            phone = scanner.nextLine();
            if (!isValidPhone(phone)) {
                System.out.println("\t\t\tInvalid Phone. Phone must be a valid number.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("\t\t\tInvalid Phone. Phone must be a valid number.");
            
            return;
        }
        System.out.print("Enter Date of Birth (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();    
        if (!isValidDateOfBirth(dateOfBirth)) {
            System.out.println("\t\t\tInvalid Date of Birth. Date of Birth must be in the format 'dd/MM/yyyy'.");
            return;
        }

        Customer customer = new Customer(customerID, name, phone, dateOfBirth);
        customerList.add(customer);
        System.out.println("\t\t\tCustomer added.");
    }

    // Search customers
    private void searchCustomer() {
        System.out.println("\t\t\t----- Search Customer -----");
        System.out.println("\t\t\tSearch by:");
        System.out.println("\t\t\t1. Customer ID");
        System.out.println("\t\t\t2. Name");
        System.out.println("\t\t\t3. Phone");
        System.out.println("\t\t\t4. Date of birth");
        System.out.print("Enter your choice: ");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();

        switch (searchChoice) {
            case 1:
                searchCustomerByID();
                break;
            case 2:
                searchCustomersByName();
                break;
            case 3:
                searchCustomersByPhone();
                break;
            case 4:
                searchCustomersByDateOfBirth();
                break;
            default:
                System.out.println("\t\t\tInvalid choice. Please try again.");
                break;
        }
    }

    private void searchCustomerByID() {
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        if (!isValidCustomerID(customerID)) {
            System.out.println("\t\t\tInvalid Customer ID. Customer ID must have a length of 4 and start with 'KH'.");
            return;
        }
        Customer customer = getCustomerByID(customerID);
        if (customer != null) {
            System.out.println("\t\t\t----- Customer Found -----");
            System.out.println(customer);
            System.out.println("\t\t\t--------------------------");
        } else {
            System.out.println("\t\t\tNo customer found.");
        }
    }

    private Customer getCustomerByID(String customerID) {
        for (Customer customer : customerList) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }
        return null; 
    }

    private void searchCustomersByName() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        if (!isValidName(name)) {
            System.out.println("\t\t\tInvalid Name. Name must not contain numbers or spaces.");
            return;
        }
        List<Customer> customers = getCustomersByName(name);
        displaySearchResults(customers);
    }

    private List<Customer> getCustomersByName(String name) {
        List<Customer> customers = new ArrayList<>();
        for (Customer customer : customerList) {
            if (customer.getName().equalsIgnoreCase(name)) {
                customers.add(customer);
            }
        }
        return customers;
    }

    private void searchCustomersByPhone() {
        System.out.print("Enter Phone: ");
        String phone;
        try {
            phone = scanner.nextLine();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("\t\t\tInvalid Phone. Phone must be a numeric value.");
            scanner.nextLine();
            return;
        }
        if (!isValidPhone(phone)) {
            System.out.println("\t\t\tInvalid Phone. Phone must be a positive value.");
            return;
        }
        List<Customer> customers = getCustomersByPhone(phone);
        displaySearchResults(customers);
    }

    private List<Customer> getCustomersByPhone(String phone) {
        List<Customer> Customers = new ArrayList<>();
        for (Customer customer : customerList) {
            if (customer.getPhone() == phone) {
                customers.add(customer);
            }
        }
        return customers;
    }

    private void searchCustomersByDateOfBirth() {
        System.out.print("Enter Date of Birth (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        if (!isValidDateOfBirth(dateOfBirth)) {
            System.out.println("\t\t\tInvalid Date of Birth. Date of Birth must be in the format 'dd/MM/yyyy'.");
            return;
        }
        List<Customer> customers = getCustomersByDateOfBirth(dateOfBirth);
        displaySearchResults(customers);
    }

    private List<Customer> getCustomersByDateOfBirth(String dateOfBirth) {
        List<Customer> Customers = new ArrayList<>();
        for (Customer customer : customerList) {
            if (customer.getDateOfBirth() == dateOfBirth) {
                customers.add(customer);
            }
        }
        return customers;
    }

    private void displaySearchResults(List<Customer> customers) {
        System.out.println("\t\t\t----- Search Results -----");
        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                System.out.println(customer);
                System.out.println("\t\t\t--------------------------");
            }
        } else {
            System.out.println("\t\t\tNo customers found.");
        }
    }

    private void sortCustomers() {
        System.out.println("\t\t\t----- Sort Customers -----");
        System.out.println("\t\t\tSort by:");
        System.out.println("\t\t\t1. Customer ID");
        System.out.println("\t\t\t2. Name");
        System.out.print("Enter your choice: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();

        switch (sortChoice) {
            case 1:
                sortCustomersByID();
                break;
            case 2:
                sortCustomersByName();
                break;
            default:
                System.out.println("\t\t\tInvalid choice. Please try again.");
                break;
        }
    }

    private void sortCustomersByID() {
        Collections.sort(customerList, new Comparator<Customer>() {
            @Override
            public int compare(Customer c1, Customer c2) {
                return c1.getCustomerID().compareTo(c2.getCustomerID());
            }
        });
        System.out.println("\t\t\tCustomers sorted by ID:");
        displayAllCustomers();
    }

    private void sortCustomersByName() {
        Collections.sort(customerList, new Comparator<Customer>() {
    @Override
            public int compare(Customer c1, Customer c2) {
                return c1.getName().compareToIgnoreCase(c2.getName());
            }
        });
        System.out.println("\t\t\tCustomers sorted by Name:");
        displayAllCustomers();
    }

    // Delete customers
    private void deleteCustomer() {
        System.out.print("Enter Customer ID: ");
            String customerID = scanner.nextLine();
            if (!isValidCustomerID(customerID)) {
            System.out.println("\t\t\tInvalid Customer ID. Customer ID must have a length of 4 and start with 'KH'.");
            return;
        }
        Customer customer = getCustomerByID(customerID);
        if (customer != null) {
            customerList.remove(customer);
            System.out.println("\t\t\tCustomer deleted.");
        } else {
            System.out.println("\t\t\tNo customer found.");
        }
    }

    // Write data to file
    public void writeDataToFile() {
        try {
            FileWriter fw = new FileWriter("customers.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Customer customer : customerList) {
                bw.write(customer.getCustomerID() + ", "
                        + customer.getName() + ", "
                        + customer.getPhone() + ", "
                        + customer.getDateOfBirth());
                bw.newLine();
            }

            bw.close();
            System.out.println("\t\t\tCustomer data saved to file.");
        } catch (IOException e) {
            System.out.println("\t\t\tError writing data to file.");
            e.printStackTrace();
        }
    }
    // //Load data from file
    // public void loadDataFromFile() {
    //     try {
    //         FileReader fr = new FileReader("customers.txt");
    //         BufferedReader br = new BufferedReader(fr);

    //         String line;
    //         while ((line = br.readLine()) != null) {
    //             String[] data = line.split(", ");
    //             if (data.length == 4) {
    //                 Customer customer = new Customer(data[0], data[1], data[2], data[3]);
    //                 customers.add(customer);
    //             }
    //         }

    //         br.close();
    //     } catch (IOException e) {
    //         System.out.println("\t\t\tError loading data from file.");
    //         e.printStackTrace();
    //     }
    // }

    // Update information
    private void updateCustomer() {
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        Customer customer = getCustomerByID(customerID);
        if (customer != null) {
            System.out.print("Enter new Name: ");
                String newName = scanner.nextLine();
                if (!isValidName(newName)) {
                    System.out.println("\t\t\tInvalid Name. Name must not contain numbers or spaces.");
                    return;
                }
            System.out.print("Enter new Phone number: ");
            try {
                String newPhone = scanner.nextLine();
                if (!isValidPhone(newPhone)) {
                    System.out.println("\t\t\tInvalid Phone. Phone must have a length of 4 and start with '09'.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("\t\t\tInvalid Phone. Phone must be a valid number.");
                return;
            }
            System.out.print("Enter new Date of Birth (dd/mm/yyyy): ");
                String newDateOfBirth = scanner.nextLine();
                if (!isValidDateOfBirth(newDateOfBirth)) {
                    System.out.println("\t\t\tInvalid Date of Birth. Date of Birth must be in the format 'dd/MM/yyyy'.");
                    return;
            }
            customer.setPhone(newPhone);
            customer.setDateOfBirth(newDateOfBirth);
            System.out.println("\t\t\tCustomer updated.");
        } else {
            System.out.println("\t\t\tNo customer found.");
        }
    }
    
    //Check valid
    private boolean isValidCustomerID(String customerID) {
        return customerID.matches("^KH\\d{2}$");
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^09\\d{8}$");
    }

    private boolean isValidDateOfBirth(String dateOfBirth) {
        return dateOfBirth.matches("^\\d{2}/\\d{2}/\\d{4}$");
    }

}
