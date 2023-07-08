import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import project.models.Product;
import project.utils.DataValidator;

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
                    System.out.println("\t\t\tExiting...");
                    break;
                default:
                    System.out.println("\t\t\tInvalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
    }

    public void displayMenu() {
        System.out.println("\t\t\t----- Product Management System -----");
        System.out.println("\t\t\t1. Display all product list");
        System.out.println("\t\t\t2. Add new product");
        System.out.println("\t\t\t3. Update info");
        System.out.println("\t\t\t4. Delete product");
        System.out.println("\t\t\t5. Search product by different criteria");
        System.out.println("\t\t\t6. Sort product list by different criteria");
        System.out.println("\t\t\t7. Write Data to file");
        System.out.println("\t\t\t0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void displayAllProducts() {
        System.out.println("\t\t\t----- All Products -----");
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                System.out.println(product);
                System.out.println("\t\t\t-----------------------");
            }
        } else {
            System.out.println("\t\t\tNo products found.");
        }
    }

    private void addProduct() {
        System.out.println("\t\t\t----- Add New Product -----");
        System.out.print("Enter Product ID: ");
        String productID = scanner.nextLine();
        if (!DataValidator.isValidProductID(productID)) {
            System.out.println("\t\t\tInvalid Product ID. Product ID must have a length of 4, start with 'SP', and contain only numbers.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        if (!DataValidator.isValidName(name)) {
            System.out.println("\t\t\tInvalid Name. Name must not contain numbers or spaces.");
            return;
        }

        System.out.print("Enter Price: ");
        double price;
        try {
            price = scanner.nextDouble();
            scanner.nextLine();
            if (!DataValidator.isValidPrice(price)) {
                System.out.println("\t\t\tInvalid Price. Price must be a valid number.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("\t\t\tInvalid Price. Price must be a valid number.");
            scanner.nextLine();
            return;
        }

        Product product = new Product(productID, name, price);
        productList.add(product);
        System.out.println("\t\t\tProduct added.");
    }

    private void updateProduct() {
        System.out.print("Enter Product ID: ");
        String productID = scanner.nextLine();
        Product product = getProductByID(productID);
        if (product != null) {
            System.out.print("Enter new Name: ");
            String newName = scanner.nextLine();
            if (!DataValidator.isValidName(newName)) {
                System.out.println("\t\t\tInvalid Name. Name must not contain numbers or spaces.");
                return;
            }

            System.out.print("Enter new Price: ");
            double newPrice;
            try {
                newPrice = Double.parseDouble(scanner.nextLine());
                if (!DataValidator.isValidPrice(newPrice)) {
                    System.out.println("\t\t\tInvalid Price. Price must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("\t\t\tInvalid Price. Price must be a valid number.");
                return;
            }

            // Update the product information
            product.setName(newName);
            product.setPrice(newPrice);
            System.out.println("\t\t\tProduct updated.");
        } else {
            System.out.println("\t\t\tNo product found.");
        }
    }

    private void deleteProduct() {
        System.out.print("Enter Product ID: ");
        String productID = scanner.nextLine();

        if (!DataValidator.isValidProductID(productID)) {
            System.out.println("\t\t\tInvalid Product ID. Product ID must have a length of 4, start with 'SP', and contain only numbers.");
            return;
        }
        

        Product product = getProductByID(productID);
        if (product != null) {
            productList.remove(product);
            System.out.println("\t\t\tProduct deleted.");
        } else {
            System.out.println("\t\t\tNo product found.");
        }
    }

    private void searchProducts() {
        System.out.println("\t\t\t----- Search Product -----");
        System.out.println("\t\t\tSearch by:");
        System.out.println("\t\t\t1. Product ID");
        System.out.println("\t\t\t2. Name");
        System.out.println("\t\t\t3. Price");
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
                System.out.println("\t\t\tInvalid choice. Please try again.");
                break;
        }
    }

    private void searchProductByID() {
        System.out.print("Enter Product ID: ");
        String productID = scanner.nextLine();
        if (!DataValidator.isValidProductID(productID)) {
            System.out.println("\t\t\tInvalid Product ID. Product ID must have a length of 4, start with 'SP', and contain only numbers.");
            return;
        }
        
        Product product = getProductByID(productID);
        if (product != null) {
            System.out.println("\t\t\t----- Product Found -----");
            System.out.println(product);
            System.out.println("\t\t\t--------------------------");
        } else {
            System.out.println("\t\t\tNo product found.");
        }
    }

    private Product getProductByID(String productID) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null; // Product not found
    }

    private void searchProductsByName() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        if (!DataValidator.isValidName(name)) {
            System.out.println("\t\t\tInvalid Name. Name cannot contain numbers and must not be blank.");
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
            System.out.println("\t\t\tInvalid Price. Price must be a numeric value.");
            scanner.nextLine();
            return;
        }
        if (!DataValidator.isValidPrice(price)) {
            System.out.println("\t\t\tInvalid Price. Price must be a positive value.");
            return;
        }
        List<Product> products = getProductsByPrice(price); 
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
        System.out.println("\t\t\t----- Search Results -----");
        if (!products.isEmpty()) {
            for (Product product : products) {
                System.out.println(product);
                System.out.println("\t\t\t--------------------------");
            }
        } else {
            System.out.println("\t\t\tNo products found.");
        }
    }

    private void sortProducts() {
        System.out.println("\t\t\t----- Sort Products -----");
        System.out.println("\t\t\tSort by:");
        System.out.println("\t\t\t1. Product ID");
        System.out.println("\t\t\t2. Name");
        System.out.println("\t\t\t3. Price");
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
            default:
                System.out.println("\t\t\tInvalid choice. Please try again.");
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
        System.out.println("\t\t\tProducts sorted by ID:");
        displayAllProducts();
    }

    private void sortProductsByName() {
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getName().compareToIgnoreCase(p2.getName());
            }
        });
        System.out.println("\t\t\tProducts sorted by Name:");
        displayAllProducts();
    }

    private void sortProductsByPrice() {
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });
        System.out.println("\t\t\tProducts sorted by Price:");
        displayAllProducts();
    }

    public void writeDataToFile() {
        try {
            FileWriter fw = new FileWriter("resources/data/products.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Product product : productList) {
                bw.write(product.getProductID() + ", "
                        + product.getName() + ", "
                        + product.getPrice());
                bw.newLine();
            }

            bw.close();
            System.out.println("\t\t\t**Product data saved to file successfully!.**");
        } catch (IOException e) {
            System.out.println("\t\t\tError writing data to file.");
            e.printStackTrace();
        }
    }

    

    

    
}
