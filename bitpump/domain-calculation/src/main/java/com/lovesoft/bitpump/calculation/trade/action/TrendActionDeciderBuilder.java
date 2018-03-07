package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.calculation.HistoricalTransactions;
import com.lovesoft.bitpump.simulation.ParametersTO;

public class TrendActionDeciderBuilder {
    private double percentageUpBuy;
    private double percentageDownSell;
    private HistoricalTransactions historicalTransactions;
    private int withTriggerTargetBuyCount = 1;
    private int withTriggerTargetSellCount = 1;

    protected TrendActionDeciderBuilder() {
    }

    public TrendActionDeciderBuilder withPercentage(double percentage) {
        this.percentageUpBuy = this.percentageDownSell = percentage;
        return this;
    }

    public TrendActionDeciderBuilder withTriggerTargetBuyCount(int withTriggerTargetCount) {
        this.withTriggerTargetBuyCount = withTriggerTargetCount;
        return this;
    }

    public TrendActionDeciderBuilder withTriggerTargetSellCount(int withTriggerTargetSellCount) {
        this.withTriggerTargetSellCount = withTriggerTargetSellCount;
        return this;
    }

    public TrendActionDeciderBuilder withPercentageDownSell(double percentageDownSell) {
        this.percentageDownSell = percentageDownSell;
        return this;
    }

    public TrendActionDeciderBuilder withPercentageUpBuy(double percentageUpBuy) {
        this.percentageUpBuy = percentageUpBuy;
        return this;
    }

    public TrendActionDeciderBuilder withParameters(ParametersTO parameters) {
        withTriggerTargetBuyCount(parameters.getTriggerTargetBuyCount());
        withMaximumHistoricalTransactions(1000);// Uhhh
        withPercentageDownSell(parameters.getPercentageSel());
        withPercentageUpBuy(parameters.getPercentageBuy());
        return this;
    }

    public TrendActionDeciderBuilder withMaximumHistoricalTransactions(long maximumHistoricalTransactions) {
        historicalTransactions  = new HistoricalTransactions(maximumHistoricalTransactions);
        return this;
    }

    public TradeActionDecider build() {
        TrendTradeActionDecider tad = new TrendTradeActionDecider(percentageUpBuy, percentageDownSell, historicalTransactions);
        tad.setTargetBuyCount(withTriggerTargetBuyCount);
        tad.setTargetSellCount(withTriggerTargetSellCount);
        return tad;
    }
}
