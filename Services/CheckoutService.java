package Services;

import java.util.ArrayList;
import java.util.List;

import models.Customer;
import models.Cart;
import models.CartItem;
import models.ShipmentItem;


public class CheckoutService {
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
}
