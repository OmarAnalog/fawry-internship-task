import java.util.Date;

import Services.CheckoutService;
import Services.ShippingService;
import models.Cart;
import models.Customer;
import models.Expiration;
import models.Product;
import models.Shippable;


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