package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.calculation.trade.action.TradeActionParameters;

public class ParametersTO {
    private TradeActionParameters trendParameters;
    private double maximumLoosePercentage;
    private double startDigitalCurrencyAmount;
    private double startMoneyAmount;
    private HistoricalTransactionSource historicalTransactionSource;

    public ParametersTO() {

    }

    public TradeActionParameters getTrendParameters() {
        return trendParameters;
    }

    public void setTrendParameters(TradeActionParameters trendParameters) {
        this.trendParameters = trendParameters;
    }

    public double getMaximumLoosePercentage() {
        return maximumLoosePercentage;
    }

    public void setMaximumLoosePercentage(double maximumLoosePercentage) {
        this.maximumLoosePercentage = maximumLoosePercentage;
    }

    public HistoricalTransactionSource getHistoricalTransactionSource() {
        return historicalTransactionSource;
    }

    public void setHistoricalTransactionSource(HistoricalTransactionSource historicalTransactionSource) {
        this.historicalTransactionSource = historicalTransactionSource;
    }

    public double getStartDigitalCurrencyAmount() {
        return startDigitalCurrencyAmount;
    }

    public void setStartDigitalCurrencyAmount(double startDigitalCurrencyAmount) {
        this.startDigitalCurrencyAmount = startDigitalCurrencyAmount;
    }

    public double getStartMoneyAmount() {
        return startMoneyAmount;
    }

    public void setStartMoneyAmount(double startMoneyAmount) {
        this.startMoneyAmount = startMoneyAmount;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ParametersTO that = (ParametersTO) o;
        if (!that.trendParameters.equals(trendParameters))
            return false;
        if (Double.compare(that.maximumLoosePercentage, maximumLoosePercentage) != 0)
            return false;
        return historicalTransactionSource != null ?
                historicalTransactionSource.equals(that.historicalTransactionSource) :
                that.historicalTransactionSource == null;
    }

    @Override public int hashCode() {
        int result = 13;
        long temp;
        temp = Double.doubleToLongBits(maximumLoosePercentage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (historicalTransactionSource != null ? historicalTransactionSource.hashCode() : 0);
        result = 31 * result + trendParameters.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ParametersTO{" +
                "trendParameters=" + trendParameters +
                ", maximumLoosePercentage=" + maximumLoosePercentage +
                ", startDigitalCurrencyAmount=" + startDigitalCurrencyAmount +
                ", startMoneyAmount=" + startMoneyAmount +
                '}';
    }

}
