package com.lovesoft.bitpump.calculation.trade.action.candle;

import com.google.common.base.Preconditions;

public class Counter {
    private final int targetCount;
    private int actualCount;

    public Counter(int targetCount) {
        Preconditions.checkArgument(targetCount > 0);
        this.targetCount = targetCount;
    }

    public void count() {
        ++actualCount;
        if(actualCount > targetCount) {
            actualCount = 1;
        }
    }

    public boolean isTriggered() {
        return targetCount == actualCount;
    }

    public void reset() {
        actualCount = 0;
    }
}
