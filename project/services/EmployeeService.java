import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Predicate;
import project.models.Employee;
import project.utils.DataValidator;

public class EmployeeService {
    static ArrayList<Employee> EmpArrList = new ArrayList<Employee>();
    Scanner scanner = new Scanner(System.in);

    public static ArrayList<Employee> getListEmployee() {
        return EmpArrList;
    }

    public static void displayEmployee(ArrayList<Employee> ListEmployee) {
        if (ListEmployee.isEmpty()) {
            System.out.println("No Employee found!");
        } else {
            for (Employee i : ListEmployee) {
                System.out.println(i);
            }
        }
    }

    public static void addEmployee(ArrayList<Employee> ListEmployee, Employee c) {
        ListEmployee.add(c);
    }

    public static ArrayList<Employee> search(ArrayList<Employee> ListEmployee, Predicate<Employee> p) {
        ArrayList<Employee> searchArrList = new ArrayList<>();

        for (Employee i : ListEmployee) {
            if (p.test(i)) {
                searchArrList.add(i);
            }
        }
        return searchArrList;
    }

    public static ArrayList<Employee> searchById(ArrayList<Employee> ListEmployee, String searchKey) {
        ListEmployee = search(ListEmployee, p -> p.getID().toLowerCase().startsWith(searchKey));
        return ListEmployee;
    }

    public static ArrayList<Employee> searchByName(ArrayList<Employee> ListEmployee, String searchKey) {
        ListEmployee = search(ListEmployee, p -> p.getName().toLowerCase().startsWith(searchKey));

        return ListEmployee;
    }

    public static ArrayList<Employee> searchByPhone(ArrayList<Employee> ListEmployee, String searchKey) {
        ListEmployee = search(ListEmployee, p -> p.getPhone().startsWith(searchKey));

        return ListEmployee;
    }

    public static ArrayList<Employee> searchByPosition(ArrayList<Employee> ListEmployee, String searchKey) {
        ListEmployee = search(ListEmployee, p -> p.getPosition().toLowerCase().startsWith(searchKey));

        return ListEmployee;
    }

    public static boolean deleteEmployee(ArrayList<Employee> list, String deleteKey) {
        Iterator<Employee> iterator = list.iterator();
        while (iterator.hasNext()) {
            Employee info = iterator.next();
            if (info.getID().equalsIgnoreCase(deleteKey)) {
                iterator.remove();
                return true;
            } else {
                if (info.getName().equalsIgnoreCase(deleteKey)) {
                    iterator.remove();
                    return true;
                } else {
                    if (info.getPhone().equalsIgnoreCase(deleteKey)) {
                        iterator.remove();
                        return true;
                    } else {
                        if (info.getPosition().equalsIgnoreCase(deleteKey)) {
                            iterator.remove();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkId(ArrayList<Employee> list, String id) {
        for (Employee i : list) {
            if (i.getID().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public static void update(ArrayList<Employee> List, String id, String newName, String newPhone,
            String newPosition) {
        for (Employee i : List) {
            if (checkId(List, id)) {
                i.setName(newName);
                i.setPhone(newPhone);
                i.setPosition(newPosition);
                break;
            }
        }
    }

    public static void SortByName(ArrayList<Employee> List) {
        Collections.sort(List, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                Collator collator = Collator.getInstance(new Locale("vi", "VN"));
                return collator.compare(e1.getName(), e2.getName());
            }
        });
    }

    public void execute() {
        int choice;
        do {
            displayMenu();
            choice = getChoice();
            switch (choice) {
                case 1:
                    displayAllEmployee();
                    break;
                case 2:
                    addNewEmployee();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    deleteEmployeeByID();
                    break;
                case 5:
                    updateInfomation();
                    break;
                case 6:
                    SortEmployee();
                    break;
                case 7:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid option! (1-7)");
                    break;

            }
        } while (choice != 7);

    }

    private int getChoice() {
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        return choice;
    }

    private void displayMenu() {
        System.out.println("----- Employee Management System -----");
        System.out.println("1. Display all Employee");
        System.out.println("2. Add new Employee");
        System.out.println("3. Search Employee");
        System.out.println("4. Delete a Employee ");
        System.out.println("5. Update Employee");
        System.out.println("6. Sort By Name");
        System.out.println("7. Exit");
    }

    public String getString(String text) {
        System.out.print(text);
        String input = scanner.nextLine();
        return input;
    }

    private void displayAllEmployee() {
        displayEmployee(EmpArrList);
    }

    private void addNewEmployee() {
        String id, name, phone, position;
        System.out.println("\t\t\t----- Add New Employee -----");
        System.out.println("\t\t\t1. Employee ID must have a length of 8 and start with 'NVGH'.");
        System.out.print("\t\t\tEnter Employee ID: "); 
        id = getString("");
        if (!DataValidator.isValidEmployeeID(id)) {
            System.out.println("\t\t\tInvalid ID, please try again (NVGHxxxx)");
            return;
        }
        System.out.print("\t\t\t2. Enter Employee name: "); 
        name = getString("");
        if (!DataValidator.isValidName(name)) {
            System.out.println("\t\t\tInvalid Name. Name must not contain numbers.");
            return;
        }

        System.out.println("\t\t\t3. Phone must have a length of 10 and start with '09'");
        System.out.print("\t\t\tEnter Phone: "); 
        try {
            phone = getString("");
            if (!DataValidator.isValidPhone(phone)) {
                System.out.println("\t\t\tInvalid Phone. Phone must be a valid number.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("\t\t\tInvalid Phone. Phone must be a valid number.");
            
            return;
        }
        System.out.print("\t\t\t4. Enter Employee Position: "); 
        position = getString("");
        if (!DataValidator.isValidName(position)) {
            System.out.println("\t\t\tInvalid Employee Position. Position must not contain numbers.");
            return;
        }
        addEmployee(EmpArrList, new Employee(id, name, phone, position));
        System.out.println("Added successfully");
    }

    private void searchEmployee() {
        int choice;

        ArrayList<Employee> searchArrList = new ArrayList<>();
        System.out.println("----- Search Employee -----");
        System.out.println("Search by:");
        System.out.println("1. Employee ID");
        System.out.println("2. Name");
        System.out.println("3. Phone");
        System.out.println("4. Position");
        System.out.print("Enter your choice: ");
        do {
            choice = getChoice();
            String searchKey;
            System.out.println("");

            switch (choice) {
                case 1:
                    searchKey = getString("Enter ID to find: ").toLowerCase();
                    searchArrList = searchById(EmpArrList, searchKey);

                    break;
                case 2:
                    searchKey = getString("Enter Name to find: ").toLowerCase();
                    searchArrList = searchByName(EmpArrList, searchKey);

                    break;
                case 3:
                    searchKey = getString("Enter Phone number to find: ").toLowerCase();
                    searchArrList = searchByPhone(EmpArrList, searchKey);

                    break;
                case 4:
                    searchKey = getString("Enter Position to find: ").toLowerCase();
                    searchArrList = searchByPosition(EmpArrList, searchKey);

                    break;
                default:
                    System.out.println("Invalid option! (1-4)");
                    break;

            }

            displayEmployee(searchArrList);

        } while (choice < 1 && choice > 4);
    }

    public void deleteEmployeeByID(){
        String deleteKey = getString("Enter ID to delete: ");
        if (deleteEmployee(EmpArrList, deleteKey)) {
            System.out.println("[" + deleteKey + "] deleted");
        } else {
            System.out.println("No " + "[" + deleteKey + "] found!");
        }
    }

    public void updateInfomation() {
        String id;
        boolean check;

        do {
            id = getString("Enter ID to update: ");
            check = checkId(EmpArrList, id);

            if (!check)
                System.out.println("No ID [" + id + "] found!\n");

        } while (check == false);
        String newName = getString("Enter Name: ");
        String newPhone = getString("Enter Phone number: ");
        String newPosition = getString("Enter Position: ");
        update(EmpArrList, id, newName, newPhone, newPosition);
        System.out.println("Updated successfully");

    }

    public void SortEmployee() {
        SortByName(EmpArrList);
    }
}
