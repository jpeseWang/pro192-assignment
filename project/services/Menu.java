import java.util.Scanner;

public class Menu { 
     
    public void MenuExecute(){
        System.out.println("\t\t\t\t     ________");
        System.out.println("\t\t\t\t ___//__][__\\___");
        System.out.println("\t\t\t\t|                |\\");
        System.out.println("\t\t\t\t`-O------------O-'`");
        
    Scanner scanner = new Scanner(System.in);
    int choice;
        do {
            System.out.println("\t\t\t+---------------------------------------+");
            System.out.println("\t\t\t| ORDER AND DELIVERY MANAGEMENT SYSTEM  |");
            System.out.println("\t\t\t+---------------------------------------+");
            System.out.println("\t\t\t| 1. Manage Orders                      |");
            System.out.println("\t\t\t| 2. Manage Products                    |");
            System.out.println("\t\t\t| 3. Manage Customers                   |");
            System.out.println("\t\t\t| 4. Manage Delivery Staff              |");
            System.out.println("\t\t\t| 5. Exit                               |");
            System.out.println("\t\t\t+---------------------------------------+");
            System.out.print("\t\t\t| Enter selection: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            System.out.println("\t\t\t+---------------------------------------+");
            System.out.println("");
            

            switch (choice) {
                case 1:
                    OrderService orderService = new OrderService();
                    orderService.execute();
                    break;
                case 2:
                    ProductService productService = new ProductService();
                    productService.execute();
                    break;
                case 3:
                    CustomerService customerService = new CustomerService();
                    customerService.execute();
                    break;
                case 4:
                    EmployeeService employeeService = new EmployeeService();
                    employeeService.execute();
                    break;
                    case 5:
                    System.out.println("Program terminated.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        } while (choice != 5);
        scanner.close();
     }
    }

