package com.lovesoft.bitpump.candle;

import com.lovesoft.bitpump.calculation.candle.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CandleChartBuilderTest {


    private final int ONE_HOUR_IN_MS = 1000 * 60 * 60;

    @Test
    public void testBuild() {
        CandleChartBuilder builder = new CandleChartBuilder();
        List<ValueAtTime> values = new ArrayList<>();

        addValue(values, 1, 10);
        addValue(values, 2, 11);
        addValue(values, 3, 15);
        addValue(values, 4, 14);
        addValue(values, 5, 13);
        addValue(values, ONE_HOUR_IN_MS - 5, 3);

        addValue(values, ONE_HOUR_IN_MS + 1, 7);
        addValue(values, ONE_HOUR_IN_MS + 2, 10);
        addValue(values, ONE_HOUR_IN_MS + 4, 13);
        addValue(values, 2 * ONE_HOUR_IN_MS - 3, 20);


        CandleChart chart = builder.build(values, ChartTimeInterval.HOUR);
        List<CandleValue> candles = chart.getValues();

        Assertions.assertNotNull(values);
        Assertions.assertEquals(2, candles.size(), "There should be exactly two candles.");

        Assertions.assertEquals(candles.get(0).getOpen(), 10);
        Assertions.assertEquals(candles.get(0).getClose(), 3);
        Assertions.assertEquals(candles.get(0).getMax(), 15);
        Assertions.assertEquals(candles.get(0).getMin(), 3);

        Assertions.assertEquals(candles.get(1).getOpen(), 7);
        Assertions.assertEquals(candles.get(1).getClose(), 20);
        Assertions.assertEquals(candles.get(1).getMax(), 20);
        Assertions.assertEquals(candles.get(1).getMin(), 7);
    }

    private void addValue(List<ValueAtTime> values, int i, int i2) {
        values.add(new Value(i, i2));
    }


    private class Value implements ValueAtTime {
        private long time;
        private double vale;
        public Value(long time, double vale) {
            this.time = time;
            this.vale = vale;
        }

        @Override
        public long getTimeInMS() {
            return time;
        }

        @Override
        public double getValue() {
            return vale;
        }
    }
}
