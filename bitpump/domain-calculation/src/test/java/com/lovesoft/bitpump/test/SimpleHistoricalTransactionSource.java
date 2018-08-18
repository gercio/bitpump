package com.lovesoft.bitpump.test;

import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
import com.lovesoft.bitpump.simulation.HistoricalTransactionSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleHistoricalTransactionSource implements HistoricalTransactionSource{

    private List<HistoricalTransactionTO> historicalTransactions;

    private SimpleHistoricalTransactionSource(List<HistoricalTransactionTO> historicalTransactions) {
        this.historicalTransactions = historicalTransactions;
    }

    @Override
    public List<HistoricalTransactionTO> getHistoricalTransactions() {
        return historicalTransactions;
    }


    public static SimpleHistoricalTransactionSource build(Double ...  htTable) {
        List<HistoricalTransactionTO> list = Arrays.asList(htTable).stream().map( price -> new HistoricalTransactionTO(0, price)).collect(Collectors.toList());
        return new SimpleHistoricalTransactionSource(list);
    }
}
