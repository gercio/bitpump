package com.lovesoft.bitpump.to;

public class TradeAmountTO {
    private TradeAction action;
    private double amount;

    public TradeAmountTO(TradeAction action, double amount) {
        this.action = action;
        this.amount = amount;
    }

    public TradeAction getAction() {
        return action;
    }

    public void setAction(TradeAction action) {
        this.action = action;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "TradeAmountTO{" +
                "action=" + action +
                ", amount=" + amount +
                '}';
    }
}
