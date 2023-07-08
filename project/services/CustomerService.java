
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

public class CustomerService extends Menu {

    private Scanner scanner = new Scanner(System.in);
    private List<Customer> customerList;

    public CustomerService() {
        customerList = new ArrayList<>();
        customer.loadDataFromFile();
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
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
    }

    // Display menu
    private void displayMenu() {
        System.out.println("----- Customer Management System -----");
        System.out.println("1. Add new customer");
        System.out.println("2. Display all customers");
        System.out.println("3. Write data to file");
        System.out.println("4. Search customer");
        System.out.println("5. Delete a customer");
        System.out.println("6. Update phone and date of birth");
        System.out.println("7. Sort customer list by different criteria");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // Display customers
    private void displayAllCustomers() {
        System.out.println("----- All Customers -----");
        if (!customerList.isEmpty()) {
            for (Customer customer : customers) {
                System.out.println(customer);
                System.out.println("--------------------------");
            }
        } else {
            System.out.println("No customers found.");
        }
    }

    // Add customers
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
        customerList.add(customer);
        System.out.println("Customer added.");
    }

    // Search customers
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
        if (!isValidCustomerID(customerID)) {
            System.out.println("Invalid Customer ID. Customer ID must have a length of 4 and start with 'KH'.");
            return;
        }
        Customer customer = getCustomerByID(customerID);
        if (customer != null) {
            System.out.println("----- Customer Found -----");
            System.out.println(customer);
            System.out.println("--------------------------");
        } else {
            System.out.println("No customer found.");
        }
    }

    private Customer getCustomerByID(String customerID) {
        for (Customer customer : customerList) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }
        return null; // Customer not found
    }

    private void searchCustomersByName() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        if (!isValidName(name)) {
            System.out.println("Invalid Name. Name must not contain numbers or spaces.");
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
        String phone = scanner.nextLine();
        if (!isValidPhone(phone)) {
            System.out.println("Invalid Phone. Phone must have a length of 4 and start with '09'.");
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
        if (!isValidDateOfBirth(newDateOfBirth)) {
            System.out.println("Invalid Date of Birth. Date of Birth must be in the format 'dd/MM/yyyy'.");
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

    private void sortCustomers() {
        System.out.println("----- Sort Customers -----");
        System.out.println("Sort by:");
        System.out.println("1. Customer ID");
        System.out.println("2. Name");
        System.out.print("Enter your choice: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();

        switch (sortChoice) {
            case 1:
                sortProductsByID();
                break;
            case 2:
                sortProductsByName();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
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
        System.out.println("Customers sorted by ID:");
        displayAllCustomers();
    }

    private void sortCustomersByName() {
        Collections.sort(customerList, new Comparator<Customer>() {
            @Override
            public int compare(Customer c1, Customer c2) {
                return c1.getName().compareToIgnoreCase(c2.getName());
            }
        });
        System.out.println("Customers sorted by Name:");
        displayAllCustomers();
    }

    // Delete customers
    private void deleteCustomer() {
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        if (!isValidCustomerID(customerID)) {
            System.out.println("Invalid Customer ID. Customer ID must have a length of 4 and start with 'KH'.");
            return;
        }
        Customer customer = getCustomerByID(customerID);
        if (customer != null) {
            customerList.remove(customer);
            System.out.println("Customer deleted.");
        } else {
            System.out.println("No customer found.");
        }
    }

    // Write data to file
    private void writeDataToFile() {
        customerList.saveDataToFile();
        System.out.println("Data written to customers.txt file.");
    }

    public void saveDataToFile() {
        try {
            FileWriter fw = new FileWriter("customers.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Customer customer : customers) {
                bw.write(customer.getCustomerID() + ", "
                        + customer.getName() + ", "
                        + customer.getPhone() + ", "
                        + customer.getDateOfBirth());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error writing data to file.");
            e.printStackTrace();
        }
    }

    public void loadDataFromFile() {
        try {
            FileReader fr = new FileReader("customers.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length == 4) {
                    Customer customer = new Customer(data[0], data[1], data[2], data[3]);
                    customers.add(customer);
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error loading data from file.");
            e.printStackTrace();
        }
    }

    // Update information
    private void updateCustomer() {
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        Customer customer = getCustomerByID(customerID);
        if (customer != null) {
            String newName = scanner.nextLine();
            if (!isValidName(newName)) {
                System.out.println("Invalid Name. Name must not contain numbers or spaces.");
                return;
                System.out.print("Enter new Phone: ");
                String newPhone = scanner.nextLine();
                if (!isValidPhone(newPhone)) {
                    System.out.println("Invalid Phone. Phone must have a length of 4 and start with '09'.");
                    return;
                }
                System.out.print("Enter new Date of Birth (dd/mm/yyyy): ");
                String newDateOfBirth = scanner.nextLine();

            }
            if (!isValidDateOfBirth(newDateOfBirth)) {
                System.out.println("Invalid Date of Birth. Date of Birth must be in the format 'dd/MM/yyyy'.");
                return;
            }
            customer.setPhone(newPhone);
            customer.setDateOfBirth(newDateOfBirth);
            System.out.println("Customer updated.");
        } else {
            System.out.println("No customer found.");
        }
    }

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
