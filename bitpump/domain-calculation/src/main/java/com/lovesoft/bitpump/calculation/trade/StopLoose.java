package com.lovesoft.bitpump.calculation.trade;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.support.MathSupport;
import com.lovesoft.bitpump.support.OptionalConsumerBoolean;

public class StopLoose {
    private double maximumLoosePercentage;
    private TradeData tradeData;

    public StopLoose(double maximumLoosePercentage) {
        Preconditions.checkArgument(0.1 < maximumLoosePercentage && maximumLoosePercentage <= 90, "Bad maximumLoosePercentage " + maximumLoosePercentage);
        this.maximumLoosePercentage = maximumLoosePercentage;
    }

    public void setTradeData(TradeData tradeData) {
        this.tradeData = tradeData;
    }

    public boolean stopLoose() {
        Preconditions.checkNotNull(tradeData);
        return OptionalConsumerBoolean.of(tradeData.getLastBuyExchangeRate())
                .ifPresent(lastER -> MathSupport.calculatePercentageOfXisY(lastER , tradeData.getSellExchangeRate()) < (100 - maximumLoosePercentage))
                .ifNotPresent(() -> false)
                .getBoolean();
    }
}
