package project.services;

import java.util.List;
import java.util.Scanner;
import model.Customer;
public class CustomerService extends Menu {

    private Company company;
    private String newPhone;
    private String newDateOfBirth;

    public CustomerService() {
        company = new Company();
        company.loadDataFromFile();
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
                case 0:
                    System.out.println("Exitting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
    }

    //Display menu
    private void displayMenu() {
        System.out.println("----- Customer Management System -----");
        System.out.println("1. Add new customer");
        System.out.println("2. Display all customers");
        System.out.println("3. Write data to file");
        System.out.println("4. Search customer");
        System.out.println("5. Delete a customer by ID");
        System.out.println("6. Update phone and date of birth");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    //Display customers
    private void displayAllCustomers() {
        System.out.println("----- All Customers -----");
        List<Customer> customers = company.getCustomers();
        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                System.out.println(customer);
                System.out.println("--------------------------");
            }
        } else {
            System.out.println("No customers found.");
        }
    }

    //Add customers
    private void addCustomer() {
        System.out.println("----- Add New Customer -----");
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Date of Birth (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        if (!isValidCustomerID(customerID)) {
            System.out.println("Invalid Customer ID. Customer ID must have a length of 4 and start with 'KH'.");
            return;
        }
        if (!isValidPhone(phone)) {
            System.out.println("Invalid Phone. Phone must have a length of 4 and start with '09'.");
            return;
        }
        if (!isValidDateOfBirth(dateOfBirth)) {
            System.out.println("Invalid Date of Birth. Date of Birth must be in the format 'dd/MM/yyyy'.");
            return;
        }

        Customer customer = new Customer(customerID, name, phone, dateOfBirth);
        company.addCustomer(customer);
        System.out.println("Customer added.");
    }

    //Search customers
    private void searchCustomer() {
        System.out.println("----- Search Customer -----");
        System.out.println("Search by:");
        System.out.println("1. Customer ID");
        System.out.println("2. Name");
        System.out.println("3. Phone");
        System.out.println("4. Date of birth");
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
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void searchCustomerByID() {
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        Customer customer = company.searchCustomerByID(customerID);
        if (customer != null) {
            System.out.println("----- Customer Found -----");
            System.out.println(customer);
            System.out.println("--------------------------");
        } else {
            System.out.println("No customer found.");
        }
    }

    private void searchCustomersByName() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        List<Customer> customers = company.searchCustomersByName(name);
        displaySearchResults(customers);
    }

    private void searchCustomersByPhone() {
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        List<Customer> customers = company.searchCustomersByPhone(phone);
        displaySearchResults(customers);
    }

    private void searchCustomersByDateOfBirth() {
        System.out.print("Enter Date of Birth (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        List<Customer> customers = company.searchCustomersByDateOfBirth(dateOfBirth);
        displaySearchResults(customers);
    }

    private void displaySearchResults(List<Customer> customers) {
        System.out.println("----- Search Results -----");
        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                System.out.println(customer);
                System.out.println("--------------------------");
            }
        } else {
            System.out.println("No customers found.");
        }
    }

    //Write data to file
    private void writeDataToFile() {
        company.saveDataToFile();
        System.out.println("Data written to customers.txt file.");
    }

    //Delete customers
    private void deleteCustomer() {
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        Customer customer = company.searchCustomerByID(customerID);
        if (customer != null) {
            company.getCustomers().remove(customer);
            System.out.println("Customer deleted.");
        } else {
            System.out.println("No customer found.");
        }
    }

    //Update information
    private void updateCustomer() {
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        Customer customer = company.searchCustomerByID(customerID);
        if (customer != null) {
            System.out.print("Enter new Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Enter new Date of Birth (dd/mm/yyyy): ");
            String dateOfBirth = scanner.nextLine();
            if (!isValidPhone(newPhone)) {
                    System.out.println("Invalid Phone. Phone must have a length of 4 and start with '09'.");
                    return;
                }
                if (!isValidDateOfBirth(newDateOfBirth)) {
                    System.out.println("Invalid Date of Birth. Date of Birth must be in the format 'dd/MM/yyyy'.");
                    return;
                }
            company.updateCustomer(customerID, phone, dateOfBirth);
            System.out.println("Customer updated.");
        } else {
            System.out.println("No customer found.");
        }
    }
    private boolean isValidCustomerID(String customerID) {
        return customerID.matches("^KH\\d{2}$");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^09\\d{8}$");
    }

    private boolean isValidDateOfBirth(String dateOfBirth) {
        return dateOfBirth.matches("^\\d{2}/\\d{2}/\\d{4}$");
    }

    @Override
    public void execute(Object data) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}

public abstract class Menu <T> {

    protected Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }
    public abstract void execute(T data);
}