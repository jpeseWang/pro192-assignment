package project.models;

public class Product {

    private String productID;
    private String name;
    private double price;
    private int quantities;

    public Product(String productID, String name, double price, int quantities) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantities = quantities;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantities() {
        return quantities;
    }

    public void setQuantities(int quantities) {
        this.quantities = quantities;
    }

    @Override
    public String toString() {
        return "Product{"
                + "ID='" + productID + '\''
                + ", name='" + name + '\''
                + ", price=" + price
                + ", quantities=" + quantities
                + '}';
    }
}
