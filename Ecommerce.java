import java.util.ArrayList;
import java.util.Date;
import java.util.List;

interface IShippable {
    public double getWeight();
}

class Shippable implements IShippable {
    private double weight;

    public Shippable(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}

interface IExpired {
    public boolean isExpired();
}

class Expiration implements IExpired {
    private Date expirationDate;

    public Expiration(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean isExpired() {
        return new Date().after(expirationDate);
    }
}
class Product{
    private String name;
    private double price;
    private int quantity;
    private Shippable shippable;
    private Expiration expirable;
    public Product(String name, double price, int quantity,Expiration expirable,Shippable shippable) {
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

class Customer {
    private double balance;

    public Customer(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
};

class CartItem {
    private Product product;
    private int quantity;
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
};

class Cart {
    private ArrayList<CartItem> cartItems = new ArrayList();

    public void add(Product product, int quantity) {
        if (quantity > product.getQuantity()) {
            System.out.println("Error : There is no enough quantity for product : " + product.getName());
            return;
        }
        if (quantity <= 0) {
            System.out.println("Error invalid asked quantity for product : " + product.getName());
            return;
        }
        for (CartItem item : cartItems) {
            if (item.getProduct().getName() == product.getName()) {
                item.addQuantity(quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }
};

class ShipmentItem {
    private String name;
    private double weight;
    public ShipmentItem(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }  
};

class ShippingService {
    public void ship(List<ShipmentItem> items) {
        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        for (ShipmentItem item : items) {
            if (item.getWeight() < 1000)
                System.out.println(item.getName() + "     " + item.getWeight() + "g");
            else
                System.out.println(item.getName() + "     " + item.getWeight() / 1000 + "kg");
            totalWeight += item.getWeight();
        }
        System.out.println("Total package weight     " + (totalWeight / 1000) + "kg");
    }
}

class CheckoutService {
    private ShippingService shippingService;
    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void checkout(Customer customer, Cart cart) {
        if (cart.getCartItems().isEmpty()) {
            System.out.println("Error : You have no items in your cart");
            return;
        }
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().isExpired() || item.getProduct().getQuantity() < item.getQuantity()) {
                System.out.println("Error : Item " + item.getProduct().getName() + "is expired or out of stock");
                return;
            }
        }
        var subtotal = getsSubtotal(cart);
        var shippingFees = 30;
        var totalAmount = getFees(subtotal,shippingFees);
        if (customer.getBalance() < totalAmount) {
            System.out.println("Error: Your balance isn't enough");
            return;
        }

        List<ShipmentItem> shippedItems = new ArrayList<ShipmentItem>();

        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().isShippable()) {
                var weight = item.getProduct().getWeight() * item.getQuantity();
                var name = item.getQuantity() + "x  " + item.getProduct().getName();
                shippedItems.add(new ShipmentItem(name, weight));
            }
        }
        shippingService.ship(shippedItems);
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getCartItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + "     "
                    + (item.getProduct().getPrice() * item.getQuantity()));
        }
        System.out.println("--------------");
        System.out.println("Subtotal      " + subtotal);
        System.out.println("Shipping      " + shippingFees);
        System.out.println("Amount        " + totalAmount);
    }

    private double getFees(double subtotal,double shippingFees) {
        double totalAmount = subtotal + shippingFees;
        return totalAmount;
    }

    private double getsSubtotal(Cart cart){
        double subtotal = 0;
        for (CartItem item : cart.getCartItems()) {
            subtotal += item.getProduct().getPrice() * item.getQuantity();
        }
        return subtotal;
    }
};
public class Ecommerce {
    public static void main(String[] args) {
        Date futureDate = new Date(System.currentTimeMillis() + 500000);
        Product cheese = new Product("Cheese", 40, 5, new Expiration(futureDate), new Shippable(200));
        Product milk = new Product("Milk", 100, 5, new Expiration(futureDate), new Shippable(50));
        Product scratchCard = new Product("Scratch Card", 10, 100, null, null);
        Cart cart = new Cart();
        cart.add(cheese, 5);
        cart.add(milk, 1);
        cart.add(scratchCard, 1);
        Customer customer = new Customer(500);
        CheckoutService checkoutService = new CheckoutService(new ShippingService());
        checkoutService.checkout(customer, cart);
    }
}