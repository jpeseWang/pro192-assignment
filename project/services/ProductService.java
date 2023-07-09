import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.Menu;

import project.models.Product;

public class ProductService extends Menu {

    private Scanner scanner = new Scanner(System.in);

    private List<Product> productList;

    public ProductService() {
        productList = new ArrayList<>();
    }

    public void execute() {
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    searchProducts();
                    break;
                case 6:
                    sortProducts();
                    break;
                case 7:
                    writeDataToFile();
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

    private void displayMenu() {
        System.out.println("\t\t\t+---------------------------------------------------+");
        System.out.println("\t\t\t|             Product Management System             |");
        System.out.println("\t\t\t+---------------------------------------------------+");
        System.out.println("\t\t\t| 1. Display all product                            |");
        System.out.println("\t\t\t| 2. Add new product                                |");
        System.out.println("\t\t\t| 3. Update info                                    |");
        System.out.println("\t\t\t| 4. Delete product                                 |");
        System.out.println("\t\t\t| 5. Search product by different criteria           |");
        System.out.println("\t\t\t| 6. Sort product list by different criteria        |");
        System.out.println("\t\t\t| 7. Write Data to file                             |");
        System.out.println("\t\t\t| 0. Exit                                           |");
        System.out.println("\t\t\t+---------------------------------------------------+");
        System.out.print("Enter your choice: ");
    }

    private void displayAllProducts() {
        double totalPrices = 0;
        int totalQuantities = 0;

        System.out.println("\t\t\t+-------------+-------------------+------------------+--------------+");
        System.out.println("\t\t\t|-------------------        All Products        --------------      |");
        System.out.println("\t\t\t+-------------+-------------------+------------------+--------------+");
        System.out.println("\t\t\t|  ID         |        Nam        |  Price           |  Quantities  |");
        System.out.println("\t\t\t+-------------+-------------------+------------------+--------------+");
        for (Product product : productList) {
            System.out.printf("\t\t\t| %-11s | %-17s | $%10.2f      | %-12d |\n",
                    product.getProductID(), product.getName(), product.getPrice(), product.getQuantities());

            totalPrices += product.getPrice() * product.getQuantities();
            totalQuantities += product.getQuantities();
        }
        System.out.println("\t\t\t+-------------+-------------------+------------------+--------------+");
        System.out.printf("\t\t\t|   Total     |                   | $%10.2f     | %-12d |\n",
                totalPrices, totalQuantities);
        System.out.println("\t\t\t+-------------+-------------------+------------------+--------------+");
        System.out.println("");
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    private void addProduct() {
        System.out.println("");
        System.out.println("----- Add New Product -----");
        String productID;
        String name;
        double price;
        int quantities;

        while (true) {
            System.out.print("Enter Product ID: ");
            productID = scanner.nextLine();
            if (isValidProductID(productID)) {
                break;
            }
            System.out.println(
                    "Invalid Product ID. Product ID must have a length of 4, start with 'SP', and contain only numbers.");
        }

        while (true) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine();
            if (isValidName(name)) {
                break;
            }
            System.out.println("Invalid Name. Name must not contain numbers or spaces.");
        }

        while (true) {
            System.out.print("Enter Price: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (isValidPrice(price)) {
                    break;
                }
                System.out.println("Invalid Price. Price must be a valid number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid Price. Price must be a valid number.");
            }
        }

        while (true) {
            System.out.print("Enter Quantities: ");
            try {
                quantities = Integer.parseInt(scanner.nextLine());
                if (isValidQuantities(quantities)) {
                    break;
                }
                System.out.println("Invalid Quantities. Quantities must be a positive integer.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid Quantities. Quantities must be a valid number.");
            }
        }

        Product product = new Product(productID, name, price, quantities);
        productList.add(product);
        System.out.println("Product added.");
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    private void updateProduct() {
        String productID;
        Product product;

        do {
            System.out.print("Enter Product ID: ");
            productID = scanner.nextLine();
            product = getProductByID(productID);
            if (product == null) {
                System.out.println("No product found with the entered ID. Please try again.");
            }
        } while (product == null);

        System.out.print("Enter new Name: ");
        String newName = scanner.nextLine();
        if (!isValidName(newName)) {
            System.out.println("Invalid Name. Name must not contain numbers or spaces.");
            return;
        }

        System.out.print("Enter new Price: ");
        double newPrice;
        try {
            newPrice = Double.parseDouble(scanner.nextLine());
            if (!isValidPrice(newPrice)) {
                System.out.println("Invalid Price. Price must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Price. Price must be a valid number.");
            return;
        }

        System.out.print("Enter new Quantities: ");
        int newQuantities;
        try {
            newQuantities = Integer.parseInt(scanner.nextLine());
            if (!isValidQuantities(newQuantities)) {
                System.out.println("Invalid Quantities. Quantities must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Quantities. Quantities must be a valid number.");
            return;
        }

        product.setName(newName);
        product.setPrice(newPrice);
        product.setQuantities(newQuantities);
        System.out.println("Product updated.");
    }

    private void deleteProduct() {
        while (true) {
            System.out.print("Enter Product ID: ");
            String productID = scanner.nextLine();

            if (!isValidProductID(productID)) {
                System.out.println("Invalid Product ID. Product ID must have a length of 4 and start with 'SP'.");
                continue;
            }

            Product product = getProductByID(productID);
            if (product != null) {
                productList.remove(product);
                System.out.println("Product deleted.");
                break;
            } else {
                System.out.println("No product found. Please enter a valid Product ID.");
            }
        }
    }

    private void searchProducts() {
        System.out.println("----- Search Product -----");
        System.out.println("Search by:");
        System.out.println("1. Product ID");
        System.out.println("2. Name");
        System.out.println("3. Price");
        System.out.print("Enter your choice: ");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();

        switch (searchChoice) {
            case 1:
                searchProductByID();
                break;
            case 2:
                searchProductsByName();
                break;
            case 3:
                searchProductsByPrice();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void searchProductByID() {
        System.out.print("Enter Product ID: ");
        String productID = scanner.nextLine();
        if (!isValidProductID(productID)) {
            System.out.println("Invalid Product ID. Product ID must have a length of 4 and start with 'SP'.");
            return;
        }
        Product product = getProductByID(productID);
        if (product != null) {
            System.out.println("----- Product Found -----");
            System.out.println(product);
            System.out.println("--------------------------");
        } else {
            System.out.println("No product found.");
        }
    }

    private Product getProductByID(String productID) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    private void searchProductsByName() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        if (!isValidName(name)) {
            System.out.println("Invalid Name. Name cannot contain numbers and must not be blank.");
            return;
        }
        List<Product> products = getProductsByName(name); // Rename the method to getProductsByName
        displaySearchResults(products);
    }

    private List<Product> getProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().equalsIgnoreCase(name)) {
                products.add(product);
            }
        }
        return products;
    }

    private void searchProductsByPrice() {
        System.out.print("Enter Price: ");
        double price;
        try {
            price = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Price. Price must be a numeric value.");
            scanner.nextLine();
            return;
        }
        if (!isValidPrice(price)) {
            System.out.println("Invalid Price. Price must be a positive value.");
            return;
        }
        List<Product> products = getProductsByPrice(price); // Rename the method to getProductsByPrice
        displaySearchResults(products);
    }

    private List<Product> getProductsByPrice(double price) {
        List<Product> products = new ArrayList<>();
        for (Product product : productList) {
            if (product.getPrice() == price) {
                products.add(product);
            }
        }
        return products;
    }

    private void displaySearchResults(List<Product> products) {
        System.out.println("----- Search Results -----");
        if (!products.isEmpty()) {
            for (Product product : products) {
                System.out.println(product);
                System.out.println("--------------------------");
            }
        } else {
            System.out.println("No products found.");
        }
    }

    private void sortProducts() {
        System.out.println("----- Sort Products -----");
        System.out.println("Sort by:");
        System.out.println("1. Product ID");
        System.out.println("2. Name");
        System.out.println("3. Price");
        System.out.println("4. Quantities");
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
            case 3:
                sortProductsByPrice();
                break;
            case 4:
                sortProductsByQuantities();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void sortProductsByID() {
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductID().compareTo(p2.getProductID());
            }
        });
        System.out.println("Products sorted by ID:");
        displayAllProducts();
    }

    private void sortProductsByName() {
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getName().compareToIgnoreCase(p2.getName());
            }
        });
        System.out.println("Products sorted by Name:");
        displayAllProducts();
    }

    private void sortProductsByPrice() {
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });
        System.out.println("Products sorted by Price:");
        displayAllProducts();
    }

    private void sortProductsByQuantities() {
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.compare(p1.getQuantities(), p2.getQuantities());
            }
        });
        System.out.println("Products sorted by Quantities:");
        displayAllProducts();
    }

    public void writeDataToFile() {
        try {
            FileWriter fw = new FileWriter("pro192-assignment/resources/data/products.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Product product : productList) {
                bw.write(product.getProductID() + ","
                        + product.getName() + ","
                        + product.getPrice() + ","
                        + product.getQuantities());
                bw.newLine();
            }

            bw.close();
            System.out.println("Product data saved to file.");
        } catch (IOException e) {
            System.out.println("Error writing data to file.");
            e.printStackTrace();
        }
    }

    public void readDataFromFile() {
        try {
            FileInputStream fis = new FileInputStream("pro192-assignment/resources/data/products.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                String arr[] = line.split(",");
                if (arr.length == 4) {
                    Product product = new Product(arr[0], arr[1], Double.parseDouble(arr[2]), Integer.parseInt(arr[3]));
                    productList.add(product);
                }
                line = br.readLine();
            }
            br.close();
            isr.close();
            fis.close();

            System.out.println("Data loaded from file successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidProductID(String productID) {
        return productID.matches("^SP\\d{2}$");
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    private boolean isValidPrice(double price) {
        return price >= 0;
    }

    private boolean isValidQuantities(int quantities) {
        return quantities >= 0;
    }

}
