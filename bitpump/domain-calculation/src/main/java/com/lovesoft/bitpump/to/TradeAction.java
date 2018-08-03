package com.lovesoft.bitpump.to;

public enum TradeAction {
    SELL, BUY;

    public TradeAction runIfBuy(Runnable runnable) {
        return runIf(runnable, BUY);
    }

    private TradeAction runIf(Runnable runnable, TradeAction ta) {
        if(ta.equals(this)) {
            runnable.run();
            return this;
        }
        return this;
    }

    public TradeAction runIfSell(Runnable runnable) {
        if(SELL.equals(this)) {
            runnable.run();
            return this;
        }
        return this;
    }
}
