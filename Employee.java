package cc.ku.ict.module3.ECommerce;

public class Employee extends User {

    public Employee(Integer id, String name, String email, String password) {
        super(id, name, email, password);
        this.setRole("Employee");
    }
}
