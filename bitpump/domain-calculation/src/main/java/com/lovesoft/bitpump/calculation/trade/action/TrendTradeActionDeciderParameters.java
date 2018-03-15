package com.lovesoft.bitpump.calculation.trade.action;

import java.util.Objects;

public class TrendTradeActionDeciderParameters implements  TradeActionParameters {
    private double percentageUpBuy;
    private double percentageDownSell;
    private int triggerTargetBuyCount = 1;
    private int triggerTargetSellCount = 1;

    public double getPercentageUpBuy() {
        return percentageUpBuy;
    }

    public void setPercentageUpBuy(double percentageUpBuy) {
        this.percentageUpBuy = percentageUpBuy;
    }

    public double getPercentageDownSell() {
        return percentageDownSell;
    }

    public void setPercentageDownSell(double percentageDownSell) {
        this.percentageDownSell = percentageDownSell;
    }

    public int getTriggerTargetBuyCount() {
        return triggerTargetBuyCount;
    }

    public void setTriggerTargetBuyCount(int triggerTargetBuyCount) {
        this.triggerTargetBuyCount = triggerTargetBuyCount;
    }

    public int getTriggerTargetSellCount() {
        return triggerTargetSellCount;
    }

    public void setTriggerTargetSellCount(int triggerTargetSellCount) {
        this.triggerTargetSellCount = triggerTargetSellCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrendTradeActionDeciderParameters that = (TrendTradeActionDeciderParameters) o;
        return Double.compare(that.percentageUpBuy, percentageUpBuy) == 0 &&
                Double.compare(that.percentageDownSell, percentageDownSell) == 0 &&
                triggerTargetBuyCount == that.triggerTargetBuyCount &&
                triggerTargetSellCount == that.triggerTargetSellCount;
    }

    @Override
    public int hashCode() {

        return Objects.hash(percentageUpBuy, percentageDownSell, triggerTargetBuyCount, triggerTargetSellCount);
    }

    @Override
    public String toString() {
        return "TrendTradeActionDeciderParameters{" +
                "percentageUpBuy=" + percentageUpBuy +
                ", percentageDownSell=" + percentageDownSell +
                ", triggerTargetBuyCount=" + triggerTargetBuyCount +
                ", triggerTargetSellCount=" + triggerTargetSellCount +
                '}';
    }
}
