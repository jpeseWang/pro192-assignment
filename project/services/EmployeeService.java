package project.services;

public class EmployeeManagement {
    static ArrayList<Employee> EmpArrList = new ArrayList<Employee>();
    static Validation valid = new Validation();

 public static ArrayList<Employee> getListEmployee()
    {
        return EmpArrList;
    }
    
 public static void displayEmployee(ArrayList<Employee> ListEmployee)
    {    
        if (ListEmployee.isEmpty()){
            System.out.println("No Employee found!");
        } else {  
            for (Employee i : ListEmployee){
                System.out.println(i);
            }
        }

    }  
 
//////////////////////////////////////////////////////////////////////////////// 
public static void addEmployee(ArrayList<Employee> ListEmployee, Employee c)
    {
        ListEmployee.add(c);
    } 

////////////////////////////////////////////////////////////////////////////////
public static ArrayList<Employee> search(ArrayList<Employee> ListEmployee, Predicate<Employee> p)
    {
        ArrayList<Employee> searchArrList = new ArrayList<>();
        
        for (Employee i : ListEmployee){
            if(p.test(i)){
                searchArrList.add(i);
            }
        }     
        return searchArrList;
    }
    
    public static ArrayList<Employee> searchById(ArrayList<Employee> ListEmployee, String searchKey)
    {
        ListEmployee = search(ListEmployee, p -> p.getID().toLowerCase().startsWith(searchKey));
        return ListEmployee;
    }
    public static ArrayList<Employee> searchByName(ArrayList<Employee> ListEmployee, String searchKey)
    {
        ListEmployee = search(ListEmployee, p -> p.getName().toLowerCase().startsWith(searchKey));
        
        return ListEmployee;
    }
    public static ArrayList<Employee> searchByPhone(ArrayList<Employee> ListEmployee, String searchKey)
    {
        ListEmployee= search(ListEmployee, p -> p.getPhone().startsWith(searchKey));
        
        return ListEmployee;
    }
    public static ArrayList<Employee> searchByPosition(ArrayList<Employee> ListEmployee, String searchKey)
    {
        ListEmployee = search(ListEmployee, p -> p.getPosition().toLowerCase().startsWith(searchKey));
        
        return ListEmployee;
}

////////////////////////////////////////////////////////////////////////////////
 public static boolean deleteEmployee(ArrayList<Employee> list, String deleteKey) {
    Iterator<Employee> iterator = list.iterator();
    while (iterator.hasNext()) {
        Employee info = iterator.next();
        if (info.getID().equalsIgnoreCase(deleteKey)) {
            iterator.remove();
            return true;
        } else{ 
            if(info.getName().equalsIgnoreCase(deleteKey)) {
            iterator.remove();
            return true;
        } else{
            if(info.getPhone().equalsIgnoreCase(deleteKey)) {
            iterator.remove();
            return true;  
        } else{
            if(info.getPosition().equalsIgnoreCase(deleteKey)) {
            iterator.remove();
            return true;      
            }  
        }
            }
        }
            }
    return false;
    }
  //////////////////////////////////////////////////////////////////////////////
 public static boolean checkId(ArrayList<Employee> list, String id)
    {
        for (Employee i : list){
            if (i.getID().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }
    
    public static void update(ArrayList<Employee> List, String id, String newName,String newPhone, String newPosition)
    {
        for (Employee i: List){
            if (checkId(List, id)){
                i.setName(newName);
                i.setPhone(newPhone);
                i.setPosition(newPosition);
                break;
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    public static void SortByName(ArrayList<Employee> List)
    {
         Collections.sort(List, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                // Sử dụng Collator để so sánh tên đầy đủ theo ngôn ngữ hiện tại (tiếng Việt)
                Collator collator = Collator.getInstance(new Locale("vi", "VN"));
                return collator.compare(e1.getName(), e2.getName());
            }
        });
    }

///////////////////////////////////////////////////////////////////////////////    
 public void execute() {
       int choice;
        do {
            displayMenu();
            choice = getChoice();
          switch (choice){
                case 1:
                    displayAllEmployee();
                    break;
                case 2:
                    addNewEmployee();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    deleteEmployeeByID();
                    break;
                case 5:
                    updateInfomation();
                    break;
                case 6:
                    SortEmployee();
                    break;
                case 7:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid option! (1-7)");
                    break;

            }
        } while (choice != 7);
        
    }
    private int getChoice()  {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        return choice;
    }
 
 /////////////////////////////////////////////////////////////////////////////
 private void displayMenu() {
        System.out.println("----- Employee Management System -----");
        System.out.println("1. Display all Employee");
        System.out.println("2. Add new Employee");
        System.out.println("3. Search Employee");
        System.out.println("4. Delete a Employee ");
        System.out.println("5. Update Employee");
        System.out.println("6. Sort By Name");
        System.out.println("7. Exit");
    }
/////////////////////////////////////////////////////////////////////////////
public String getString(String text){
        Scanner sc = new Scanner(System.in);
        
        System.out.print(text);
        
        String input = sc.nextLine();
        return input;
    }
private void displayAllEmployee()
    {
        displayEmployee(EmpArrList);
    }

///////////////////////////////////////////////////////////////////////////////
private void addNewEmployee()
    {
        String id, name, phone, position;
        
        id = valid.checkValidId(getString("Enter ID: "));
            
        name = getString("Enter Name: ");
        
        phone = valid.checkValidPhone(getString("Enter Phone number: "));
        
        position = getString("Enter Position: ");
                
        addEmployee(EmpArrList, new Employee(id, name, phone, position));
        
    
        System.out.println("Added successfully");
     
    }

///////////////////////////////////////////////////////////////////////////////
private void searchEmployee()
    {
        int choice;
        
        ArrayList<Employee> searchArrList = new ArrayList<>();
        System.out.println("----- Search Employee -----");
        System.out.println("Search by:");
        System.out.println("1. Employee ID");
        System.out.println("2. Name");
        System.out.println("3. Phone");
        System.out.println("4. Position");
        System.out.print("Enter your choice: ");
        do {
            choice = getChoice();
            String searchKey;
            System.out.println("");
           
            switch (choice){
                case 1:
                    searchKey = getString("Enter ID to find: ").toLowerCase();
                    searchArrList = searchById(EmpArrList, searchKey); 
                    
                    break;
                case 2:
                   searchKey = getString("Enter Name to find: ").toLowerCase();
                   searchArrList = searchByName(EmpArrList, searchKey);
                   
                   break;
                case 3:
                   searchKey = getString("Enter Phone number to find: ").toLowerCase();
                   searchArrList = searchByPhone(EmpArrList, searchKey);
                   
                   break;
                case 4:
                   searchKey = getString("Enter Position to find: ").toLowerCase();
                   searchArrList = searchByPosition(EmpArrList, searchKey);
                   
                   break;
                default:
                    System.out.println("Invalid option! (1-4)");
                    break;
                
           }
           
           displayEmployee(searchArrList);
           
        } while (choice < 1 && choice > 4);
    }

////////////////////////////////////////////////////////////////////////////////
 public void deleteEmployeeByID()
    {
        
        String deleteKey = getString("Enter ID to delete: ");
        if (deleteEmployee((ArrayList<Employee>) EmpArrList, deleteKey)){
            System.out.println( "[" + deleteKey + "] deleted");
        }
        else {
            System.out.println("No " + "[" + deleteKey + "] found!");
        }
    }
 
////////////////////////////////////////////////////////////////////////////////
 public void updateInfomation()
    {
        String id;
        boolean check;
        
        do{
            id = getString("Enter ID to update: ");
            check = checkId((ArrayList<Employee>) EmpArrList, id);
            
            if(!check) System.out.println("No ID [" + id + "] found!\n");
 
        } while (check == false);
            String newName = getString("Enter Name: ");
            String newPhone = valid.checkValidPhone(getString("Enter Phone number: "));
            String newPosition = getString("Enter Postion: ");
            
            update((ArrayList<Employee>) EmpArrList, id, newName, newPhone, newPosition);
            System.out.println("Updated successfully");
  
    }
 
 ///////////////////////////////////////////////////////////////////////////////
 public void SortEmployee (){
    
}
}
