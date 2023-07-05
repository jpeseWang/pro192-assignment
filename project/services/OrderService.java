import java.util.Scanner;

public class OrderService {
    private List<Order> orders;
    
    public OrderService() {
        this.orders = new ArrayList<>();
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
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option! (1-7)");
                    break;
            }
        } while (choice != 7);
    }

    private int getChoice() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        return choice;
    }

    private void displayMenu() {
        System.out.println("----- Order Management System -----");
        System.out.println("1. Display all Orders");
        System.out.println("2. Add new Order");
        System.out.println("3. Search Order");
        System.out.println("4. Delete an Order");
        System.out.println("5. Update Order Information");
        System.out.println("6. Sort Orders");
        System.out.println("7. Exit");
    }

    private void displayAllOrders() {
        System.out.println("----- All Orders -----");
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private void addNewOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter order ID: ");
        String orderId = sc.next();
        sc.nextLine(); // Consume the newline character left by next()
        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();
        System.out.print("Enter status: ");
        String status = sc.nextLine();
        Order order = new Order(orderId, customerName, status);
        orders.add(order);
        System.out.println("Order added successfully.");
    }

    private void searchOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter order ID to find: ");
        String orderIdToFind = sc.next();
        for (Order order : orders) {
            if (order.getOrderId().equals(orderIdToFind)) {
                System.out.println("Found Order: " + order);
                return;
            }
        }
        System.out.println("Order not found.");
    }

    private void deleteOrderByID() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter order ID to delete: ");
        String orderIdToDelete = sc.next();
        orders.removeIf(order -> order.getOrderId().equals(orderIdToDelete));
        System.out.println("Order deleted successfully.");
    }

    private void updateOrderInformation() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter order ID to update: ");
        String orderIdToUpdate = sc.next();
        for (Order order : orders) {
            if (order.getOrderId().equals(orderIdToUpdate)) {
                sc.nextLine(); // Consume the newline character left by next()
                System.out.print("Enter new customer name: ");
                String newCustomerName = sc.nextLine();
                System.out.print("Enter new status: ");
                String newStatus = sc.nextLine();
                order.setCustomerName(newCustomerName);
                order.setStatus(newStatus);
                System.out.println("Order information updated successfully.");
                return;
            }
        }
        System.out.println("Order not found.");
    }

    private void sortOrders() {
        List<Order> sortedOrders = new ArrayList<>(orders);
        Collections.sort(sortedOrders, Comparator.comparing(Order::getStatus));
        System.out.println("----- Orders Sorted by Status -----");
        for (Order order : sortedOrders) {
            System.out.println(order);
        }
    }
}
