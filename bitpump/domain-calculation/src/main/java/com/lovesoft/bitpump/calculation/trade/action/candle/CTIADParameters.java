package com.lovesoft.bitpump.calculation.trade.action.candle;

public class CTIADParameters {
    private int greenCounter;
    private int previousCandleToCompare;

    public void setGreenCounter(int counterForGrow) {
        this.greenCounter = counterForGrow;
    }

    public int getGreenCounter() {
        return greenCounter;
    }

    public void setPreviousCandleToCompare(int previousCandleToCompare) {
        this.previousCandleToCompare = previousCandleToCompare;
    }
}
