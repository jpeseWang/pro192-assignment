import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import project.models.Order;
import project.utils.DataValidator;

public class OrderService {
    private List<Order> orders;
    
    public OrderService() {
        this.orders = new ArrayList<>();
        readDataFromFile();
    }

    public void execute() {
        int choice;
        do {
            displayMenu();
            choice = getChoice();
            switch (choice) {
                case 1:
                    displayAllOrders();
                    break;
                case 2:
                    addNewOrder();
                    break;
                case 3:
                    searchOrder();
                    break;
                case 4:
                    deleteOrderByID();
                    break;
                case 5:
                    updateOrderInformation();
                    break;
                case 6:
                    sortOrders();
                    break;
                case 7:
                    writeDataToFile();
                    break;
                case 0:
                    System.out.println("\t\t\tExiting...");
                    break;
                default:
                    System.out.println("\t\t\tInvalid option! (1-7)");
                    break;
            }
        } while (choice != 0);
    }

    private int getChoice() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\t\t\tEnter your choice: ");
        int choice = sc.nextInt();
        return choice;
    }

    private void displayMenu() {

        System.out.println("\t\t\t+---------------------------------------+");
        System.out.println("\t\t\t|     Order Management System           |");
        System.out.println("\t\t\t+---------------------------------------+");
        System.out.println("\t\t\t| 1. Display all Orders                 |");
        System.out.println("\t\t\t| 2. Add new Order                      |");
        System.out.println("\t\t\t| 3. Search Order                       |");
        System.out.println("\t\t\t| 4. Delete an Order                    |");
        System.out.println("\t\t\t| 5. Update Order Information           |");
        System.out.println("\t\t\t| 6. Sort Orders                        |");
        System.out.println("\t\t\t| 7. Write Data to file                 |");
        System.out.println("\t\t\t| 0. Exit                               |");
        System.out.println("\t\t\t+---------------------------------------+");
        System.out.println("");
    }

    private void displayAllOrders() {
        System.out.println("\t\t\t+--------+------------------------+-----------+");
        System.out.println("\t\t\t|----            All Orders              -----|");
        System.out.println("\t\t\t+--------+------------------------+-----------+");
        System.out.println("\t\t\t|   ID   |      Customer name     |   Status  |");
        System.out.println("\t\t\t+--------+------------------------+-----------+");
        for (Order order : orders) {
            System.out.printf("\t\t\t| %-7s| %-23s| %-10s|\n",
            order.getOrderId(), order.getCustomerName(), order.getStatus());
        }
        System.out.println("\t\t\t+--------+------------------------+-----------+");
        System.out.println("");
    }

    private void addNewOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\t\t\tEnter order ID: ");
        String orderId = sc.nextLine();
        if (!DataValidator.isValidProductID(orderId)) {
        System.out.println("\t\t\t*Invalid Product ID. Product ID must have a length of 4, start with 'SP', and contain only numbers!*");
        return;
        }
        System.out.print("\t\t\tEnter customer name: ");
        String customerName = sc.nextLine();
        if (!DataValidator.isValidName(customerName)) {
            System.out.println("\t\t\tInvalid Name. Name must not contain numbers.");
            return;
        }
        System.out.print("\t\t\tEnter status: ");
        String status = sc.nextLine();
        Order order = new Order(orderId, customerName, status);
        orders.add(order);
         System.out.println("\t\t\t|----    Status     -----|");
        System.out.println("\t\t\t*Order added successfully!*");
    }

    private void searchOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\t\t\tEnter order ID to find: ");
        String orderIdToFind = sc.next();
        for (Order order : orders) {
            if (order.getOrderId().equals(orderIdToFind)) {
                System.out.println("\t\t\tFound Order: " + order);
                return;
            }
        }
        System.out.println("\t\t\tOrder not found.");
    }

    private void deleteOrderByID() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\t\t\tEnter order ID to delete: ");
        String orderIdToDelete = sc.next();
        orders.removeIf(order -> order.getOrderId().equals(orderIdToDelete));
        System.out.println("\t\t\tOrder deleted successfully.");
    }

    private void updateOrderInformation() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\t\t\tEnter order ID to update: ");
        String orderIdToUpdate = sc.next();
        for (Order order : orders) {
            if (order.getOrderId().equals(orderIdToUpdate)) {
                sc.nextLine(); 
                System.out.print("\t\t\tEnter new customer name: ");
                String newCustomerName = sc.nextLine();
                System.out.print("\t\t\tEnter new status: ");
                String newStatus = sc.nextLine();
                order.setCustomerName(newCustomerName);
                order.setStatus(newStatus);
                System.out.println("\t\t\tOrder information updated successfully.");
                return;
            }
        }
        System.out.println("\t\t\tOrder not found.");
    }

    private void sortOrders() {
        List<Order> sortedOrders = new ArrayList<>(orders);
        Collections.sort(sortedOrders, Comparator.comparing(Order::getStatus));
        System.out.println("\t\t\t----- Orders Sorted by Status -----");
        for (Order order : sortedOrders) {
            System.out.println(order);
        }
    }

    private void writeDataToFile() {
        try {
            FileWriter fw = new FileWriter("resources/data/orders.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Order order : orders) {
                bw.write(order.getOrderId() + ", "
                        + order.getCustomerName() + ", "
                        + order.getStatus());
                bw.newLine();
            }

            bw.close();
            System.out.println("\t\t\t**Product data saved to file successfully!.**");
        } catch (IOException e) {
            System.out.println("\t\t\tError writing data to file.");
            e.printStackTrace();
        }
    }
    private void readDataFromFile() {
        try {
            FileInputStream fis = new FileInputStream("resources/data/orders.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                String arr[] = line.split(",");
                if (arr.length == 3) {
                    Order order = new Order(arr[0], arr[1], arr[2]);
                    orders.add(order);
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
}
