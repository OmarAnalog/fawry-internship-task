package models;
import java.util.Date;
import Interfaces.IExpired;

public class Expiration implements IExpired {
    private Date expirationDate;

    public Expiration(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean isExpired() {
        return new Date().after(expirationDate);
    }
}