package com.lovesoft.bitpump.calculation.trade.action.candle;

import com.lovesoft.bitpump.calculation.candle.ChartTimeInterval;

public class CTIADParameters {
    private int greenCounter;
    private int redCounter;
    private int previousCandleToCompare;
    private ChartTimeInterval chartTimeInterval;

    public void setGreenCounter(int counterForGrow) {
        this.greenCounter = counterForGrow;
    }

    public int getGreenCounter() {
        return greenCounter;
    }

    public void setPreviousCandleToCompare(int previousCandleToCompare) {
        this.previousCandleToCompare = previousCandleToCompare;
    }

    public ChartTimeInterval getChartTimeInterval() {
        return chartTimeInterval;
    }

    public void setChartTimeInterval(ChartTimeInterval chartTimeInterval) {
        this.chartTimeInterval = chartTimeInterval;
    }

    public int getPreviousCandleToCompare() {
        return previousCandleToCompare;
    }

    public int getRedCounter() {
        return redCounter;
    }

    public void setRedCounter(int redCounter) {
        this.redCounter = redCounter;
    }
}
