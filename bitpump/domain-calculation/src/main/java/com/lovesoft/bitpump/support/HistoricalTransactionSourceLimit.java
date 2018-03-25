package com.lovesoft.bitpump.support;

import com.lovesoft.bitpump.simulation.HistoricalTransactionSource;

import java.util.List;

public class HistoricalTransactionSourceLimit implements HistoricalTransactionSource {
    private int limitToFirstElements;
    private HistoricalTransactionSource source;

    public HistoricalTransactionSourceLimit(HistoricalTransactionSource source, int limitToFirstElements) {
        this.limitToFirstElements = limitToFirstElements;
        this.source = source;
    }

    @Override
    public List<Double> getHistoricalTransactions() {
        List<Double> history = source.getHistoricalTransactions();
        if(history.size() > limitToFirstElements) {
            return history.subList(0, limitToFirstElements);
        }
        return history;
    }
}
