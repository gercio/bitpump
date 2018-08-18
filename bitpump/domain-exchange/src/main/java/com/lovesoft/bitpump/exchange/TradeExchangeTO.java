package com.lovesoft.bitpump.exchange;

import java.util.Date;

/**
 * Created by Patryk Kaluzny on 11.08.18 05:26 at Milky Way Galaxy.
 */
public class TradeExchangeTO {
    private Double amount;
    private Double price;
    private Date date;
    private Long transactionId;
    private String type;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TradeTO{" +
                "amount=" + amount +
                ", price=" + price +
                ", date=" + Downloader.toDateString(date) +
                ", transactionId=" + transactionId +
                ", type='" + type + '\'' +
                '}';
    }
}
