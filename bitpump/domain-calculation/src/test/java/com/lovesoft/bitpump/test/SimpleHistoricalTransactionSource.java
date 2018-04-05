package com.lovesoft.bitpump.test;

import com.lovesoft.bitpump.simulation.HistoricalTransactionSource;

import java.util.Arrays;
import java.util.List;

public class SimpleHistoricalTransactionSource implements HistoricalTransactionSource{

    private List<Double> historicalTransactions;

    private SimpleHistoricalTransactionSource(List<Double> historicalTransactions) {
        this.historicalTransactions = historicalTransactions;
    }

    @Override
    public List<Double> getHistoricalTransactions() {
        return historicalTransactions;
    }

    @Override
    public List<Double> getHistoricalTransactionsMVA() {
        return historicalTransactions;
    }


    public static SimpleHistoricalTransactionSource build(Double ...  htTable) {
        return new SimpleHistoricalTransactionSource(Arrays.asList(htTable));
    }
}
