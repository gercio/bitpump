package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.calculation.HistoricalTransactions;

public class TradeActionDeciderBuilder {
    private double percentageUpBuy;
    private double percentageDownSell;
    private HistoricalTransactions historicalTransactions;
    private int withTriggerTargetCount = 1;

    public TradeActionDeciderBuilder withPercentage(double percentage) {
        this.percentageUpBuy = this.percentageDownSell = percentage;
        return this;
    }

    public TradeActionDeciderBuilder withTriggerTargetCount(int withTriggerTargetCount) {
        this.withTriggerTargetCount = withTriggerTargetCount;
        return this;
    }

    public TradeActionDeciderBuilder withPercentageDownSell(double percentageDownSell) {
        this.percentageDownSell = percentageDownSell;
        return this;
    }

    public TradeActionDeciderBuilder withPercentageUpBuy(double percentageUpBuy) {
        this.percentageUpBuy = percentageUpBuy;
        return this;
    }

    public TradeActionDeciderBuilder withMaximumHistoricalTransactions(long maximumHistoricalTransactions) {
        historicalTransactions  = new HistoricalTransactions(maximumHistoricalTransactions);
        return this;
    }

    public TradeActionDecider build() {
        TrendTradeActionDecider tad = new TrendTradeActionDecider(percentageUpBuy, percentageDownSell, historicalTransactions);
        tad.setTriggerTargetCount(withTriggerTargetCount);
        return tad;
    }
}
