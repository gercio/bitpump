package com.lovesoft.bitpump.calculation.trade.action;

public class TradeActionDeciderBuilder {

    public TrendActionDeciderBuilder buildTrendTradeActionDecider() {
        return new TrendActionDeciderBuilder();
    }

    public SimulationActionDeciderBuilder buildSimulationActionDecider() {
        return new SimulationActionDeciderBuilder();
    }
}
