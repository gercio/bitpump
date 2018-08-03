package com.lovesoft.bitpump.calculation.trade.action.candle;

import com.google.common.base.Preconditions;

public class Counter {
    private final int TARGET_COUNT;
    private int actualCount;

    public Counter(int targetCount) {
        Preconditions.checkArgument(targetCount > 0);
        this.TARGET_COUNT = targetCount;
    }

    public void count() {
        ++actualCount;
        if(actualCount > TARGET_COUNT) {
            actualCount = 1;
        }
    }

    public boolean isTriggered() {
        return TARGET_COUNT == actualCount;
    }

    public void reset() {
        actualCount = 0;
    }
}
