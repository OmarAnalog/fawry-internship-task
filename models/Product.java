package models;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private Shippable shippable;
    private Expiration expirable;

    public Product(String name, double price, int quantity, Expiration expirable, Shippable shippable) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.shippable = shippable;
        this.expirable = expirable;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isExpirable() {
        return expirable != null;
    }

    public boolean isExpired() {
        return expirable != null && expirable.isExpired();
    }

    public boolean isShippable() {
        return shippable != null;
    }

    public double getWeight() {
        if (shippable != null) {
            return shippable.getWeight();
        }
        throw new UnsupportedOperationException("This product is not shippable");
    }
};
