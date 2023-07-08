import controll.Menu;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import project.models.Employee;
import project.utils.DataValidator;

public class EmployeeService extends Menu {

    private Scanner scanner = new Scanner(System.in);
        private List<Employee> EmpArrList;

    public EmployeeService() {
        EmpArrList = new ArrayList<>();
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
                    addEmployee();
                    break;
                case 2:
                    displayAllEmployees();
                    break;
                case 3:
                    writeDataToFile();
                    break;
                case 4:
                    searchEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    updateEmployee();
                    break;
                case 7:
                    sortEmployees();
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
        System.out.println("\t\t\t|     Employee Management System        |");
        System.out.println("\t\t\t+---------------------------------------+");
        System.out.println("\t\t\t| 1. Add new Employee                   |");
        System.out.println("\t\t\t| 2. Display all Employees              |");
        System.out.println("\t\t\t| 3. Write data to file                 |");
        System.out.println("\t\t\t| 4. Search Employee                    |");
        System.out.println("\t\t\t| 5. Delete a Employee                  |");
        System.out.println("\t\t\t| 6. Update phone and date of birth     |");
        System.out.println("\t\t\t| 7. Sort Employee                      |");
        System.out.println("\t\t\t| 0. Exit                               |");
        System.out.println("\t\t\t+---------------------------------------+");
        System.out.print("\t\t\tEnter your choice: ");
    }

    // Display Employees
    private void displayAllEmployees() {
            System.out.println("\t\t\t+------+---------------+------------------+---------------+");
            System.out.println("\t\t\t|----------           All Employees           ------------|");
        if (!EmpArrList.isEmpty()) {
            System.out.println("\t\t\t+------+---------------+------------------+---------------+");
            System.out.println("\t\t\t|  ID  |      Name     |   Phone Number   | Date of Birth |");
            System.out.println("\t\t\t+------+---------------+------------------+---------------+");
            for (Employee Employee : EmpArrList) {
                System.out.printf("\t\t\t| %-4s |  %-12s |   %-14s |  %-12s |\n",
                    Employee.getEmployeeID(), Employee.getName(), Employee.getPhone(), Employee.getDateOfBirth());
            }
            System.out.println("\t\t\t+------+---------------+------------------+---------------+");
             System.out.println("");
        } else {
            System.out.println("\t\t\tNo Employees found.");
        }
    }

    // Add Employees
    private void addEmployee() {
        System.out.println("\t\t\t----- Add New Employee -----");
        System.out.println("\t\t\t1. Employee ID must have a length of 4 and start with 'KH'.");
        System.out.print("\t\t\tEnter Employee ID: "); 
        String EmployeeID = scanner.nextLine();
        if (!DataValidator.isValidEmployeeID(EmployeeID)) {
            System.out.println("\t\t\tInvalid Employee ID. Employee ID must have a length of 4 and start with 'KH'.");
            return;
        }
        System.out.print("\t\t\t2. Enter Name: ");
        String name = scanner.nextLine();
        if (!DataValidator.isValidName(name)) {
            System.out.println("\t\t\tInvalid Name. Name must not contain numbers.");
            return;
        }
        System.out.println("\t\t\t3. Phone must have a length of 10 and start with '09'");
        System.out.print("\t\t\tEnter Phone: "); 
        String phone;
        try {
            phone = scanner.nextLine();
            if (!DataValidator.isValidPhone(phone)) {
                System.out.println("\t\t\tInvalid Phone. Phone must be a valid number.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("\t\t\tInvalid Phone. Phone must be a valid number.");
            
            return;
        }
        System.out.print("\t\t\tEnter Date of Birth (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();    
        if (!DataValidator.isValidDateOfBirth(dateOfBirth)) {
            System.out.println("\t\t\tInvalid Date of Birth. Date of Birth must be in the format 'dd/MM/yyyy'.");
            return;
        }

        Employee Employee = new Employee(EmployeeID, name, phone, dateOfBirth);
        EmpArrList.add(Employee);
        System.out.println("\t\t\tEmployee added.");
    }

    // Search Employees
    private void searchEmployee() {
        System.out.println("\t\t\t----- Search Employee -----");
        System.out.println("\t\t\tSearch by:");
        System.out.println("\t\t\t1. Employee ID");
        System.out.println("\t\t\t2. Name");
        System.out.println("\t\t\t3. Phone");
        System.out.println("\t\t\t4. Date of birth");
        System.out.print("\t\t\tEnter your choice: ");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();

        switch (searchChoice) {
            case 1:
                searchEmployeeByID();
                break;
            case 2:
                searchEmployeesByName();
                break;
            case 3:
                searchEmployeesByPhone();
                break;
            case 4:
                searchEmployeesByDateOfBirth();
                break;
            default:
                System.out.println("\t\t\tInvalid choice. Please try again.");
                break;
        }
    }

    private void searchEmployeeByID() {
        System.out.print("\t\t\tEnter Employee ID: ");
        String EmployeeID = scanner.nextLine();
        if (!DataValidator.isValidEmployeeID(EmployeeID)) {
            System.out.println("\t\t\tInvalid Employee ID. Employee ID must have a length of 4 and start with 'KH'.");
            return;
        }
        Employee Employee = getEmployeeByID(EmployeeID);
        if (Employee != null) {
            System.out.println("\t\t\t----- Employee Found -----");
            System.out.println(Employee);
            System.out.println("\t\t\t--------------------------");
        } else {
            System.out.println("\t\t\tNo Employee found.");
        }
    }

    private Employee getEmployeeByID(String EmployeeID) {
        for (Employee Employee : EmpArrList) {
            if (Employee.getEmployeeID().equals(EmployeeID)) {
                return Employee;
            }
        }
        return null; 
    }

    private void searchEmployeesByName() {
        System.out.print("\t\t\tEnter Name: ");
        String name = scanner.nextLine();
        if (!DataValidator.isValidName(name)) {
            System.out.println("\t\t\tInvalid Name. Name must not contain numbers.");
            return;
        }
        List<Employee> Employees = getEmployeesByName(name);
        displaySearchResults(Employees);
    }

    private List<Employee> getEmployeesByName(String name) {
        List<Employee> Employees = new ArrayList<>();
        for (Employee Employee : EmpArrList) {
            if (Employee.getName().equalsIgnoreCase(name)) {
                Employees.add(Employee);
            }
        }
        return Employees;
    }

    private void searchEmployeesByPhone() {
        System.out.print("\t\t\tEnter Phone: ");
        String phone;
        try {
            phone = scanner.nextLine();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("\t\t\tInvalid Phone. Phone must be a numeric value.");
            scanner.nextLine();
            return;
        }
        if (!DataValidator.isValidPhone(phone)) {
            System.out.println("\t\t\tInvalid Phone. Phone must be a positive value.");
            return;
        }
        List<Employee> Employees = getEmployeesByPhone(phone);
        displaySearchResults(Employees);
    }

    private List<Employee> getEmployeesByPhone(String phone) {
        List<Employee> Employees = new ArrayList<>();
        for (Employee Employee : EmpArrList) {
            if (Employee.getPhone() == phone) {
                Employees.add(Employee);
            }
        }
        return Employees;
    }

    private void searchEmployeesByDateOfBirth() {
        System.out.print("\t\t\tEnter Date of Birth (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        if (!DataValidator.isValidDateOfBirth(dateOfBirth)) {
            System.out.println("\t\t\tInvalid Date of Birth. Date of Birth must be in the format 'dd/MM/yyyy'.");
            return;
        }
        List<Employee> Employees = getEmployeesByDateOfBirth(dateOfBirth);
        displaySearchResults(Employees);
    }

    private List<Employee> getEmployeesByDateOfBirth(String dateOfBirth) {
        List<Employee> Employees = new ArrayList<>();
        for (Employee Employee : EmpArrList) {
            if (Employee.getDateOfBirth() == dateOfBirth) {
                Employees.add(Employee);
            }
        }
        return Employees;
    }

    private void displaySearchResults(List<Employee> Employees) {
        System.out.println("\t\t\t----- Search Results -----");
        if (!Employees.isEmpty()) {
            for (Employee Employee : Employees) {
                System.out.println(Employee);
                System.out.println("\t\t\t--------------------------");
            }
        } else {
            System.out.println("\t\t\tNo Employees found.");
        }
    }

    private void sortEmployees() {
        System.out.println("\t\t\t----- Sort Employees -----");
        System.out.println("\t\t\tSort by:");
        System.out.println("\t\t\t1. Employee ID");
        System.out.println("\t\t\t2. Name");
        System.out.print("\t\t\tEnter your choice: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();

        switch (sortChoice) {
            case 1:
                sortEmployeesByID();
                break;
            case 2:
                sortEmployeesByName();
                break;
            default:
                System.out.println("\t\t\tInvalid choice. Please try again.");
                break;
        }
    }

    private void sortEmployeesByID() {
        Collections.sort(EmpArrList, new Comparator<Employee>() {
            @Override
            public int compare(Employee c1, Employee c2) {
                return c1.getEmployeeID().compareTo(c2.getEmployeeID());
            }
        });
        System.out.println("\t\t\tEmployees sorted by ID:");
        displayAllEmployees();
    }

    private void sortEmployeesByName() {
        Collections.sort(EmpArrList, new Comparator<Employee>() {
    @Override
            public int compare(Employee c1, Employee c2) {
                return c1.getName().compareToIgnoreCase(c2.getName());
            }
        });
        System.out.println("\t\t\tEmployees sorted by Name:");
        displayAllEmployees();
    }

    // Delete Employees
    private void deleteEmployee() {
        System.out.print("\t\t\tEnter Employee ID: ");
            String EmployeeID = scanner.nextLine();
            if (!DataValidator.isValidEmployeeID(EmployeeID)) {
            System.out.println("\t\t\tInvalid Employee ID. Employee ID must have a length of 4 and start with 'KH'.");
            return;
        }
        Employee Employee = getEmployeeByID(EmployeeID);
        if (Employee != null) {
            EmpArrList.remove(Employee);
            System.out.println("\t\t\tEmployee deleted.");
        } else {
            System.out.println("\t\t\tNo Employee found.");
        }
    }

    // Write data to file
    public void writeDataToFile() {
        try {
            FileWriter fw = new FileWriter("resources/data/Employees.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Employee Employee : EmpArrList) {
                bw.write(Employee.getEmployeeID() + ", "
                        + Employee.getName() + ", "
                        + Employee.getPhone() + ", "
                        + Employee.getDateOfBirth());
                bw.newLine();
            }

            bw.close();
            System.out.println("\t\t\tEmployee data saved to file.");
        } catch (IOException e) {
            System.out.println("\t\t\tError writing data to file.");
            e.printStackTrace();
        }
    }

    public void readDataFromFile() {
        try {
            FileInputStream fis = new FileInputStream("resources/data/Employees.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                String arr[] = line.split(",");
                if (arr.length == 4) {
                    Employee Employee = new Employee(arr[0], arr[1], arr[2], arr[3]);
                    EmpArrList.add(Employee);
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
    private void updateEmployee() {
        System.out.print("\t\t\tEnter Employee ID: ");
        String EmployeeID = scanner.nextLine();
        Employee Employee = getEmployeeByID(EmployeeID);
        if (Employee != null) {
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
            System.out.print("\t\t\tEnter new Date of Birth (dd/mm/yyyy): ");
                String newDateOfBirth = scanner.nextLine();
                if (!DataValidator.isValidDateOfBirth(newDateOfBirth)) {
                    System.out.println("\t\t\tInvalid Date of Birth. Date of Birth must be in the format 'dd/MM/yyyy'.");
                    return;
                }
            
            Employee.setPhone(newPhone);
            Employee.setDateOfBirth(newDateOfBirth);
            System.out.println("\t\t\tEmployee updated.");
        } else {
            System.out.println("\t\t\tNo Employee found.");
        }
    }
    
    //Check valid
    

}
