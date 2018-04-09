package com.lovesoft.bitpump.calculation.candle;

import java.util.List;

public class CandleChart {
    private List<CandleValue> values;

    protected CandleChart(List<CandleValue> values) {
        this.values = values;
    }

    public List<CandleValue> getValues() {
        return values;
    }
}
