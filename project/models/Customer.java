package project.models;

import java.time.LocalDate;

public class Customer {
    private String customerID;
    private String name;
    private String phone;
    private LocalDate dateOfBirth;

    public Customer(String customerID, String name, String phone, LocalDate dateOfBirth) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "CustomerID: " + customerID
                + ", Name: " + name
                + ", Phone: " + phone
                + ", Date of Birth: " + dateOfBirth;
    }
}
