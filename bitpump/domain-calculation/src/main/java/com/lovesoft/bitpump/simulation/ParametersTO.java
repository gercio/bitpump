package com.lovesoft.bitpump.simulation;

import java.text.DecimalFormat;

public class ParametersTO {
    private double percentageBuy;
    private double percentageSel;
    private int triggerTargetBuyCount;
    private int triggerTargetSellCount;
    private double maximumLoosePercentage;
    private double startDigitalCurrencyAmount = 0;
    private double startMoneyAmount = 100;
    private HistoricalTransactionSource historicalTransactionSource;

    public double getPercentageBuy() {
        return percentageBuy;
    }

    public void setPercentageBuy(double percentageBuy) {
        this.percentageBuy = percentageBuy;
    }

    public double getPercentageSel() {
        return percentageSel;
    }

    public void setPercentageSel(double percentageSel) {
        this.percentageSel = percentageSel;
    }

    public int getTriggerTargetBuyCount() {
        return triggerTargetBuyCount;
    }

    public void setTriggerTargetBuyCount(int triggerTargetBuyCount) {
        this.triggerTargetBuyCount = triggerTargetBuyCount;
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

    public int getTriggerTargetSellCount() {
        return triggerTargetSellCount;
    }

    public void setTriggerTargetSellCount(int triggerTargetSellCount) {
        this.triggerTargetSellCount = triggerTargetSellCount;
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

        if (Double.compare(that.percentageBuy, percentageBuy) != 0)
            return false;
        if (Double.compare(that.percentageSel, percentageSel) != 0)
            return false;
        if (triggerTargetBuyCount != that.triggerTargetBuyCount)
            return false;
        if (triggerTargetSellCount != that.triggerTargetSellCount)
            return false;
        if (Double.compare(that.maximumLoosePercentage, maximumLoosePercentage) != 0)
            return false;
        return historicalTransactionSource != null ?
                historicalTransactionSource.equals(that.historicalTransactionSource) :
                that.historicalTransactionSource == null;
    }

    @Override public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(percentageBuy);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(percentageSel);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + triggerTargetBuyCount;
        result = 31 * result + triggerTargetSellCount;
        temp = Double.doubleToLongBits(maximumLoosePercentage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (historicalTransactionSource != null ? historicalTransactionSource.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        DecimalFormat formatterShort = new DecimalFormat("00.0");
        return String.format("ParametersTO{percentageBuy=" + format(formatterShort, percentageBuy) +", percentageSel=" + format(formatterShort, percentageSel) + ", triggerTargetBuyCount=%02d"
                + ", triggerTargetSellCount=%02d, maximumLoosePercentage=" + format(formatterShort, maximumLoosePercentage) +", startDigitalCurrencyAmount =  " + startDigitalCurrencyAmount + ", startMoneyAmount = " + startMoneyAmount +" }'", triggerTargetBuyCount
        , triggerTargetSellCount);
    }

    private String format(DecimalFormat formatter, double percentageBuy) {
        return formatter.format(percentageBuy).replace(",", ".");
    }
}
