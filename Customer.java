package cc.ku.ict.module3.ECommerce;

public class Customer extends User {

    private Money balance; // in pennies

    public Customer(Integer id, String name, String email, String password, Integer balance) {
        super(id, name, email, password);
        this.setRole("Customer");
        this.balance = new Money(balance);
    }

    public void setBalance(Integer balance) {
        this.balance = new Money(balance);
    }

    public Money getBalance() {
        return this.balance;
    }

    public void addBalance(Integer amount) {
        Integer new_balance = this.getBalance().getValue() + amount;
        setBalance(new_balance);
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + "\nEmail: " + this.getEmail() + "\nPassword: " + this.getPassword() + "\nBalance: " + this.balance.format() + "\nRole: " + this.getRole();
    }
}
