package com.lovesoft.bitpump.calculation.candle;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.commons.ValueAtTime;

import java.util.ArrayList;
import java.util.List;

public class CandleChartBuilder {
    public CandleChart build(List<? extends ValueAtTime> values, ChartTimeInterval interval) {
        Preconditions.checkArgument(!values.isEmpty(), "Values can't be empty.");

        List<CandleValue> list = new ArrayList<>();
        CandleChart candleChart = new CandleChart(list);

        long periodEnd = Long.MIN_VALUE;
        CandleValue candleValue = null;
        for(ValueAtTime vat : values) {
            long eventTime = vat.getTimeInMS();
            double value = vat.getValue();
            if(periodEnd <= eventTime) {
                periodEnd = eventTime + interval.getIntervalInMS();
                candleValue = new CandleValue(value, value, value, value);
                list.add(candleValue);
            }
            candleValue.setMaxIfBigger(value);
            candleValue.setMinIfLower(value);
            candleValue.setClose(value);
        }
        return candleChart;
    }
}
