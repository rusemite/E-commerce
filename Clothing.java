package cc.ku.ict.module3.ECommerce;

public class Clothing extends Product {

    public Clothing(Integer id, String name, Integer price, Integer stockQuantity) {
        super(id, name, price, stockQuantity);
        this.setCategory("Clothing");
    }
}
