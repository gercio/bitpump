package com.lovesoft.bitpump.calculation.trade.amount;

public class TradeAmountDeciderBuilder {

    public TradeAmountDecider build() {
        return new MaximumTradeAmountDecider();
    }
}
