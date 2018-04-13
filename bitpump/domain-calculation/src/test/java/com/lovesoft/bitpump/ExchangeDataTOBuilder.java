package com.lovesoft.bitpump;

import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExchangeDataTOBuilder {
    public static final int TIME_INTERVAL_IN_MS = 1000 * 60; // One minute
    private int counter = 0;
    public ExchangeDataTO build(Double ... historicalTrades) {
        return new ExchangeDataTO(createHistoricalTransactions(historicalTrades)); // Collect to list
    }

    public List<HistoricalTransactionTO> createHistoricalTransactions(Double ... historicalTrades) {
        List<HistoricalTransactionTO> list = new ArrayList<>();
        for(double trade :  Arrays.asList(historicalTrades) ) {
            list.add( new HistoricalTransactionTO(counter++, trade, trade));
        }
        return list;
    }

    public List<HistoricalTransactionTO> build(double open, double max, double low, double close, long time) {
        List<HistoricalTransactionTO> list = new ArrayList<>();

        HistoricalTransactionTO htOpe = new HistoricalTransactionTO(time + 0 * TIME_INTERVAL_IN_MS, open, open);
        HistoricalTransactionTO htMax = new HistoricalTransactionTO(time + 1 * TIME_INTERVAL_IN_MS, max, max);
        HistoricalTransactionTO htLow = new HistoricalTransactionTO(time + 2 * TIME_INTERVAL_IN_MS, low, low);
        HistoricalTransactionTO htClo = new HistoricalTransactionTO(time + 3 * TIME_INTERVAL_IN_MS, close, close);

        list.add(htOpe);
        list.add(htMax);
        list.add(htLow);
        list.add(htClo);

        return list;
    }
}
