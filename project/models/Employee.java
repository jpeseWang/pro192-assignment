
package project.models;

public class Employee {
    private String ID;
    private String Name;
    private String Phone;
    private String Position;

    public Employee(String ID, String Name, String Phone, String Position) {
        this.ID = ID;
        this.Name = Name;
        this.Phone = Phone;
        this.Position = Position;
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

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    @Override
    public String toString() {
        return "Employee{" + "ID=" + ID + ", Name=" + Name + ", Phone=" + Phone + ", Position=" + Position + '}';
    }

}