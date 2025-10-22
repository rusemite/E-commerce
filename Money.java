package cc.ku.ict.module3.ECommerce;

public class Money { // pennies by default
    private Integer value;

    public Money(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public String format() {
        return String.format("%d.%02d$", this.value / 100, this.value % 100);
    }

    @Override
    public String toString() {
        return this.format();
    }
}
