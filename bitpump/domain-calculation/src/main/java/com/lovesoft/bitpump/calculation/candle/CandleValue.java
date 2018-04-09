package com.lovesoft.bitpump.calculation.candle;

public class CandleValue {
    private double open = -1;
    private double close = -1;
    private double max = Double.MIN_VALUE;
    private double min = Double.MAX_VALUE;

    public CandleValue() {
    }

    public CandleValue(double open, double close, double max, double min) {
        this.open = open;
        this.close = close;
        this.max = max;
        this.min = min;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public void setMaxIfBigger(double v) {
        if(v > max) {
            max = v;
        }
    }

    public void setMinIfLower(double v) {
        if(v < min) {
            this.min = v;
        }
    }
}
