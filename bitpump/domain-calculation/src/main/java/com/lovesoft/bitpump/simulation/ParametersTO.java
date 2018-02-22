package com.lovesoft.bitpump.simulation;

import java.util.Objects;

public class ParametersTO {
    private double percentageBuy;
    private double percentageSel;
    private int triggerTargetCount;
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

    public int getTriggerTargetCount() {
        return triggerTargetCount;
    }

    public void setTriggerTargetCount(int triggerTargetCount) {
        this.triggerTargetCount = triggerTargetCount;
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

    @Override
    public String toString() {
        return "ParametersTO{" +
                "percentageBuy=" + percentageBuy +
                ", percentageSel=" + percentageSel +
                ", triggerTargetCount=" + triggerTargetCount +
                ", maximumLoosePercentage=" + maximumLoosePercentage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParametersTO that = (ParametersTO) o;
        return Double.compare(that.percentageBuy, percentageBuy) == 0 &&
                Double.compare(that.percentageSel, percentageSel) == 0 &&
                triggerTargetCount == that.triggerTargetCount &&
                Double.compare(that.maximumLoosePercentage, maximumLoosePercentage) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentageBuy, percentageSel, triggerTargetCount, maximumLoosePercentage);
    }


}
