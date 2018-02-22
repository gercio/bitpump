package com.lovesoft.bitpump;

import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import com.lovesoft.bitpump.to.ExchangeDataTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExchangeDataTOBuilder {
    private int counter = 0;
    public ExchangeDataTO build(Double ... historicalTrades) {
        return new ExchangeDataTO(createHistoricalTransactions(historicalTrades)); // Collect to list
    }

    public List<HistoricalTransactionTO> createHistoricalTransactions(Double ... historicalTrades) {
        List<HistoricalTransactionTO> list = new ArrayList<>();
        for(double trade :  Arrays.asList(historicalTrades) ) {
            list.add( new HistoricalTransactionTO(counter++, trade));
        }
        return list;
    }
}
