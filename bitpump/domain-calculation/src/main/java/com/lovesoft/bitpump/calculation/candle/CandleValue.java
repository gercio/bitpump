package com.lovesoft.bitpump.calculation.candle;

import com.google.common.base.Preconditions;

public class CandleValue {
    private double open;
    private double max;
    private double min;
    private double close;

    public CandleValue(double open, double max, double min, double close) {
        this.open = open;
        this.close = close;
        this.max = max;
        this.min = min;
        checkValidation();
    }

    private void checkValidation() {
        Preconditions.checkArgument(max >= min, "Max " + max + " should be higher or equal than min " + min);
        Preconditions.checkArgument(max >= open, "Max " + max + " should be higher or equal than open " + open);
        Preconditions.checkArgument(max >= close, "Max " + max + " should be higher or equal than close " + close);
        Preconditions.checkArgument(min <= open, "Min " + min + " should be lower or equal than open " + open);
        Preconditions.checkArgument(min <= close, "Min " + min + " should be lower or equal than close " + close);
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
        checkValidation();
    }

    public void setClose(double close) {
        this.close = close;
        checkValidation();
    }

    public void setMaxIfBigger(double v) {
        if(v > max) {
            max = v;
            checkValidation();
        }
    }

    public void setMinIfLower(double v) {
        if(v < min) {
            this.min = v;
            checkValidation();
        }
    }

    @Override
    public String toString() {
        return "CandleValue{" +
                "open=" + open +
                ", max=" + max +
                ", min=" + min +
                ", close=" + close +
                '}';
    }
}
