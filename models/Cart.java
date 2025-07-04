package models;
import java.util.ArrayList;

public class Cart {
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