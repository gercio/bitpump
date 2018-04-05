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
        return getWithLimit(source.getHistoricalTransactions());
    }

    private List<Double> getWithLimit(List<Double> history) {
        if(history.size() > limitToFirstElements) {
            return history.subList(0, limitToFirstElements);
        }
        return history;
    }

    @Override
    public List<Double> getHistoricalTransactionsMVA() {
        return getWithLimit(source.getHistoricalTransactionsMVA());
    }
}
