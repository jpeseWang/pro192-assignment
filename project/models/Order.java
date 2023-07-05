package project.models;
class Order{
    private String orderId;
    private String customerName;
    private String status;

    public Order(String orderId, String customerName, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.status = status;
}
@Override
    public String toString() {
        return "Order ID: " + orderId + ", Customer Name: " + customerName + ", Status: " + status;
    }
}