
package project.models;

public class Employee {
    private String ID;
    private String Name;
    private String Phone;
    private String dateOfBirth;

    public Employee(String ID, String Name, String Phone, String dateOfBirth) {
        this.ID = ID;
        this.Name = Name;
        this.Phone = Phone;
        this.dateOfBirth = dateOfBirth;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Employee{" + "ID=" + ID + ", Name=" + Name + ", Phone=" + Phone + ", DateOfBirth=" + dateOfBirth + '}';
    }

}