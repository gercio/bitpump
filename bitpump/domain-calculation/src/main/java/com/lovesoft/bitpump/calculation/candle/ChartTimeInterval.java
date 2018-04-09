package com.lovesoft.bitpump.calculation.candle;

public enum ChartTimeInterval {
    HOUR(1),
    HOUR_4 (4),
    HOUR_12 (12),
    DAY(24),
    WEEK (7 * 24);

    private long intervalInMS;

    ChartTimeInterval(long intervalInHours) {
        this.intervalInMS = intervalInHours * 1000 * 60 * 60;
    }

    public long getIntervalInMS() {
        return intervalInMS;
    }
}
