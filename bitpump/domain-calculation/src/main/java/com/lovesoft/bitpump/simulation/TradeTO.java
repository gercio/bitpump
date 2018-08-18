package com.lovesoft.bitpump.simulation;

import java.util.Date;

/**
 * Created by Patryk Kaluzny on 15.08.18 08:51 at Milky Way Galaxy.
 */
public class TradeTO {
    private double price;
    private Date date;

    public TradeTO(Double price) {
        this.price = price;
        date = new Date(0);
    }

    public TradeTO(double price, Date date) {
        this.price = price;
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
