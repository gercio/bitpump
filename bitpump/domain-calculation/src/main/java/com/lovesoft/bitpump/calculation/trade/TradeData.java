package com.lovesoft.bitpump.calculation.trade;

import com.lovesoft.bitpump.to.TradeAction;

import java.util.Optional;

public class TradeData {
    private Optional<TradeAction> lastAction = Optional.empty();
    private Optional<Double> lastBuyExchangeRate = Optional.empty();
    private double sellExchangeRate;

    public Optional<TradeAction> getLastAction() {
        return lastAction;
    }

    public void setLastAction(TradeAction lastAction) {
        this.lastAction = Optional.ofNullable(lastAction);
    }

    public Optional<Double> getLastBuyExchangeRate() {
        return lastBuyExchangeRate;
    }

    public void setLastBuyExchangeRate(Double lastBuyExchangeRate) {
        this.lastBuyExchangeRate = Optional.ofNullable(lastBuyExchangeRate);
    }


    public double getSellExchangeRate() {
        return sellExchangeRate;
    }

    public void setSellExchangeRate(double sellExchangeRate) {
        this.sellExchangeRate = sellExchangeRate;
    }
}
