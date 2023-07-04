package project.models;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class Customer {

    private String customerID;
    private String name;
    private String phone;
    private String dateOfBirth;

    public Customer(String customerID, String name, String phone, String dateOfBirth) {
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
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

public class Company {

    private List<Customer> customers;

    public Company() {
        customers = new ArrayList<>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer searchCustomerByID(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }
        return null;
    }

    public List<Customer> searchCustomersByName(String name) {
        List<Customer> result = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                result.add(customer);
            }
        }
        return result;
    }

    public List<Customer> searchCustomersByPhone(String phone) {
        List<Customer> result = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getPhone().equals(phone)) {
                result.add(customer);
            }
        }
        return result;
    }

    public List<Customer> searchCustomersByDateOfBirth(String dateOfBirth) {
        List<Customer> result = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getDateOfBirth().equals(dateOfBirth)) {
                result.add(customer);
            }
        }
        return result;
    }

    public void saveDataToFile() {
        try {
            FileWriter fw = new FileWriter("customers.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Customer customer : customers) {
                bw.write(customer.getCustomerID() + ", "
                        + customer.getName() + ", "
                        + customer.getPhone() + ", "
                        + customer.getDateOfBirth());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error writing data to file.");
            e.printStackTrace();
        }
    }

    public void updateCustomer(String customerID, String phone, String dateOfBirth) {
        Customer customer = searchCustomerByID(customerID);
        if (customer != null) {
            customer.setPhone(phone);
            customer.setDateOfBirth(dateOfBirth);
        }
    }
    public void loadDataFromFile() {
        try {
            FileReader fr = new FileReader("customers.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length == 4) {
                    Customer customer = new Customer(data[0], data[1], data[2], data[3]);
                    customers.add(customer);
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error loading data from file.");
            e.printStackTrace();
        }
    }
}
