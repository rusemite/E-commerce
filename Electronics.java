package cc.ku.ict.module3.ECommerce;

public class Electronics extends Product {

    public Electronics(Integer id, String name, Integer price, Integer stockQuantity) {
        super(id, name, price, stockQuantity);
        this.setCategory("Electronics");
    }
}
