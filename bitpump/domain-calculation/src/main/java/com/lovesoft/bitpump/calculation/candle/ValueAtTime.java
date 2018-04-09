package com.lovesoft.bitpump.calculation.candle;

public interface ValueAtTime {
    long getTimeInMS();
    double getValue();
}
