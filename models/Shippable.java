package models;
import Interfaces.IShippable;

public class Shippable implements IShippable {
    private double weight;

    public Shippable(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
