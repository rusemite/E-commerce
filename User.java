package cc.ku.ict.module3.ECommerce;

public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;

    public enum Role {
        Customer,
        Employee,
        Unassigned
    }

    private Role role = Role.Unassigned;

    public String getRole() {
        return this.role.name();
    }

    public void setRole(String role) {
        this.role = Role.valueOf(role);
    }

    public User(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {
        this.id = -1;
        this.name = "None";
        this.email = "None";
        this.password = "None";
    }

    @Override
    public String toString() {
        return "Name:" + this.name + "\nEmail: " + this.email + "\nPassword: " + this.password + "Role: " + this.role;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
