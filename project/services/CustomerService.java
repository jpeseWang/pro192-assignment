import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import project.models.Customer;
import project.models.Product;
import project.utils.DataValidator;
import java.util.function.Predicate;

public class CustomerService extends Menu {

    private Scanner scanner = new Scanner(System.in);
    private List<Customer> customerList;

    public CustomerService() {
        customerList = new ArrayList<>();
        readDataFromFile();
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

    private void displayMenu() {
        System.out.println("\t\t\t+---------------------------------------+");
        System.out.println("\t\t\t|     Customer Management System        |");
        System.out.println("\t\t\t+---------------------------------------+");
        System.out.println("\t\t\t| 1. Add new customer                   |");
        System.out.println("\t\t\t| 2. Display all customers              |");
        System.out.println("\t\t\t| 3. Write data to file                 |");
        System.out.println("\t\t\t| 4. Search customer                    |");
        System.out.println("\t\t\t| 5. Delete  customer                   |");
        System.out.println("\t\t\t| 6. Update information                 |");
        System.out.println("\t\t\t| 7. Sort customer                      |");
        System.out.println("\t\t\t| 0. Exit                               |");
        System.out.println("\t\t\t+---------------------------------------+");
        System.out.print("\t\t\tEnter your choice: ");
    }

    // Display customers
    private void displayAllCustomers() {
        System.out.println("\t\t\t+------+---------------+------------------+---------------+");
        System.out.println("\t\t\t|----------                All Customers      ------------|");
        if (!customerList.isEmpty()) {
            System.out.println("\t\t\t+------+---------------+------------------+---------------+");
            System.out.println("\t\t\t|  ID  |   Name        |   Phone Number   | Date of Birth |");
            System.out.println("\t\t\t+------+---------------+------------------+---------------+");
            for (Customer customer : customerList) {
                System.out.printf("\t\t\t| %-4s |   %-12s|   %-14s |  %s   |\n",
                        customer.getCustomerID(), customer.getName(), customer.getPhone(),
                        customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            }
            System.out.println("\t\t\t+------+---------------+------------------+---------------+");
            System.out.println("");
        } else {
            System.out.println("\t\t\tNo customers found.");
        }
    }

    // Add customers
    private void addCustomer() {
        System.out.println("\t\t\t----- Add New Customer -----");

        String customerID;
        do {
            System.out.println("\t\t\t1. Customer ID must have a length of 4 and start with 'KH'.");
            System.out.print("\t\t\tEnter Customer ID: ");
            customerID = scanner.nextLine();
            if (!DataValidator.isValidCustomerID(customerID)) {
                System.out
                        .println("\t\t\tInvalid Customer ID. Customer ID must have a length of 4 and start with 'KH'.");
            }
        } while (!DataValidator.isValidCustomerID(customerID));

        String name;
        do {
            System.out.print("\t\t\t2. Enter Name: ");
            name = scanner.nextLine();
            if (!DataValidator.isValidName(name)) {
                System.out.println("\t\t\tInvalid Name. Name must not contain numbers.");
            }
        } while (!DataValidator.isValidName(name));

        String phone;
        do {
            System.out.println("\t\t\t3. Phone must have a length of 10 and start with '09'");
            System.out.print("\t\t\tEnter Phone: ");
            phone = scanner.nextLine();
            if (!DataValidator.isValidPhone(phone)) {
                System.out.println("\t\t\tInvalid Phone. Phone must be a valid number.");
            }
        } while (!DataValidator.isValidPhone(phone));

        LocalDate dateOfBirth;
        do {
            System.out.print("\t\t\tEnter Date of Birth (dd-MM-yyyy): ");
            String dateOfBirthString = scanner.nextLine();
            if (!DataValidator.isValidDateOfBirth(dateOfBirthString)) {
                System.out.println("\t\t\tInvalid Date of Birth. Date of Birth must be in the format 'dd-MM-yyyy'.");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);
                break;
            }
        } while (true);

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
        System.out.print("\t\t\tEnter your choice: ");
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
        System.out.print("\t\t\tEnter Customer ID: ");
        String customerID = scanner.nextLine();
        if (!DataValidator.isValidCustomerID(customerID)) {
            System.out.println("\t\t\tInvalid Customer ID. Customer ID must have a length of 4 and start with 'KH'.");
            return;
        }
        Customer customer = getCustomerByID(customerID);
        if (customer != null) {
            System.out.println("\t\t\t----- Customer Found -----");
            System.out.println(customer.getCustomerID() + " | " + customer.getName() + " | " + customer.getPhone()
                    + " | " + customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
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
        System.out.print("\t\t\tEnter Name: ");
        String name = scanner.nextLine();
        if (!DataValidator.isValidName(name)) {
            System.out.println("\t\t\tInvalid name.");
            return;
        }
        Customer customer = getCustomersByName(name);
        if (customer != null) {
            System.out.println("\t\t\t----- Customer Found -----");
            System.out.println(customer.getCustomerID() + " | " + customer.getName() + " | " + customer.getPhone()
                    + " | " + customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            System.out.println("\t\t\t--------------------------");
        } else {
            System.out.println("\t\t\tNo customer found.");
        }
    }
    

    private Customer getCustomersByName(String name) {
        for (Customer customer : customerList) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }
    

    private void searchCustomersByPhone() {
        System.out.print("\t\t\tEnter phone: ");
        String phone = scanner.nextLine();
        if (!DataValidator.isValidPhone(phone)) {
            System.out.println("\t\t\tInvalid phone number. Phone number must have a length of 10 and start with '09'.");
            return;
        }
        Customer customer = getCustomersByPhone(phone);
        if (customer != null) {
            System.out.println("\t\t\t----- Customer Found -----");
            System.out.println(customer.getCustomerID() + " | " + customer.getName() + " | " + customer.getPhone()
                    + " | " + customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            System.out.println("\t\t\t--------------------------");
        } else {
            System.out.println("\t\t\tNo customer found.");
        }
    }
    

    private Customer getCustomersByPhone(String phone) {
        for (Customer customer : customerList) {
            if (customer.getPhone().equals(phone)) {
                return customer;
            }
        }
        return null;
    }

    private void searchCustomersByDateOfBirth() {
        System.out.print("\t\t\tEnter Date of birth: ");
        String dateOfBirthString = scanner.nextLine();
        if (!DataValidator.isValidDateOfBirth(dateOfBirthString)) {
            System.out.println("\t\t\tInvalid date of birth. Date of birth must have a format dd-MM-yyyy.");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);
        Customer customer = getCustomersByDateOfBirth(dateOfBirth);
        if (customer != null) {
            System.out.println("\t\t\t----- Customer Found -----");
            System.out.println(customer.getCustomerID() + " | " + customer.getName() + " | " + customer.getPhone()
                    + " | " + customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            System.out.println("\t\t\t--------------------------");
        } else {
            System.out.println("\t\t\tNo customer found.");
        }
    }
    

    private Customer getCustomersByDateOfBirth(LocalDate dateOfBirth) {
        for (Customer customer : customerList) {
            if (customer.getDateOfBirth().equals(dateOfBirth)) {
                return customer;
            }
        }
        return null;
    }
    

    private void displaySearchResults(List<Customer> customers) {
        System.out.println("\t\t\t----- Search Results -----");
        if (!customers.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Change the date format
            for (Customer customer : customers) {
                String formattedDateOfBirth = customer.getDateOfBirth().format(formatter); // Format the date of birth

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
        System.out.print("\t\t\tEnter your choice: ");
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
        System.out.print("\t\t\tEnter Customer ID: ");
        String customerID = scanner.nextLine();
        if (!DataValidator.isValidCustomerID(customerID)) {
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
            FileWriter fw = new FileWriter("pro192-assignment/resources/data/customers.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Customer customer : customerList) {
                bw.write(customer.getCustomerID() + ","
                        + customer.getName() + ","
                        + customer.getPhone() + ","
                        + customer.getDateOfBirth());
                bw.newLine();
            }

            bw.close();
            System.out.println("Customer data saved to file.");
        } catch (IOException e) {
            System.out.println("Error writing data to file.");
            e.printStackTrace();
        }
    }

    public void readDataFromFile() {
        try {
            FileInputStream fis = new FileInputStream("pro192-assignment/resources/data/customers.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                String arr[] = line.split(",");
                if (arr.length == 4) {
                    String customerID = arr[0];
                    String name = arr[1];
                    String phone = arr[2];
                    String dateOfBirthString = arr[3];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);
                    Customer customer = new Customer(customerID, name, phone, dateOfBirth);
                    customerList.add(customer);
                }
                line = br.readLine();
            }
            br.close();
            isr.close();
            fis.close();

            System.out.println("\t\t\tData loaded from file successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update information
    private void updateCustomer() {
        System.out.print("\t\t\tEnter Customer ID: ");
        String customerID = scanner.nextLine();
        Customer customer = getCustomerByID(customerID);
        if (customer != null) {
            System.out.print("\t\t\tEnter new Name: ");
            String newName = scanner.nextLine();
            if (!DataValidator.isValidName(newName)) {
                System.out.println("\t\t\tInvalid Name. Name must not contain numbers.");
                return;
            }
            System.out.print("\t\t\tEnter new Phone number: ");
            String newPhone = scanner.nextLine();
            if (!DataValidator.isValidPhone(newPhone)) {
                System.out.println("\t\t\tInvalid Phone. Phone must have a length of 4 and start with '09'.");
                return;
            }
            System.out.print("\t\t\tEnter new Date of Birth (dd-mm-yyyy): ");
            String newDateOfBirthString = scanner.nextLine();
            if (!DataValidator.isValidDateOfBirth(newDateOfBirthString)) {
                System.out.println("\t\t\tInvalid Date of Birth. Date of Birth must be in the format 'dd-MM-yyyy'.");
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate newDateOfBirth = LocalDate.parse(newDateOfBirthString, formatter);

            customer.setPhone(newPhone);
            customer.setDateOfBirth(newDateOfBirth);
            System.out.println("\t\t\tCustomer updated.");
        } else {
            System.out.println("\t\t\tNo customer found.");
        }
    }
}
