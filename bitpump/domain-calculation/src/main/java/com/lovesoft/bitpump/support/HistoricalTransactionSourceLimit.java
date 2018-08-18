package com.lovesoft.bitpump.support;

import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
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
    public List<HistoricalTransactionTO> getHistoricalTransactions() {
        return getWithLimit(source.getHistoricalTransactions());
    }

    private List<HistoricalTransactionTO> getWithLimit(List<HistoricalTransactionTO> history) {
        if(history.size() > limitToFirstElements) {
            return history.subList(0, limitToFirstElements);
        }
        return history;
    }
}
