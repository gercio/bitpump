package com.lovesoft.bitpump.simulation;


public class ParametersTO {
    private double percentageBuy;
    private double percentageSel;
    private int triggerTargetBuyCount;
    private int triggerTargetSellCount;
    private double maximumLoosePercentage;
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
        return "ParametersTO{" + "percentageBuy=" + percentageBuy + ", percentageSel=" + percentageSel + ", triggerTargetBuyCount=" + triggerTargetBuyCount
                + ", triggerTargetSellCount=" + triggerTargetSellCount + ", maximumLoosePercentage=" + maximumLoosePercentage  + '}';
    }
}
