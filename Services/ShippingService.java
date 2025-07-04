package Services;

import models.ShipmentItem;
import java.util.List;
public class ShippingService {
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
