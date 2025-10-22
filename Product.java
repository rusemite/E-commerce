package cc.ku.ict.module3.ECommerce;

public class Product {
    private Integer id;
    private String name;
    private Money price; // In pennies
    private Integer stockQuantity;

    public enum Category {
        Book,
        Electronics,
        Clothing,
        Undefined
    }

    private Category category = Category.valueOf("Undefined");

    public Product(Integer id, String name, Integer price, Integer stockQuantity, String category) {
        this.id = id;
        this.name = name;
        this.price = new Money(price);
        this.stockQuantity = stockQuantity;
        this.category = Category.valueOf(category);
    }

    public Product(Integer id, String name, Integer price, Integer stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = new Money(price);
        this.stockQuantity = stockQuantity;
    }

    public String displayInfo() {
        return "ID: " + this.id + " | " + this.name + " | Price: " + this.price.format() + " | In stock: " + this.stockQuantity + " | Category: " + this.category;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nPrice: " + this.price.format() + "\nStock Quantity: " + this.stockQuantity + "\nCategory: " + this.category + "\n";
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = new Money(price);
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCategory() {
        return this.category.name();
    }

    public void setCategory(String category) {
        this.category = Category.valueOf(category);
    }
}
