package com.lovesoft.bitpump.calculation.trade.action;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 25.02.2018 12:44.
 */
public class HistoricalTransactionsBuffer {
    private int capacity;
    private List<HistoricalTransactionTO> historicalTransactionList = new ArrayList<>();

    public HistoricalTransactionsBuffer(int capacity) {
        Preconditions.checkArgument(capacity > 1, "Bad capacity " + capacity);
        this.capacity = capacity;
    }

    public void keep(List<HistoricalTransactionTO> historicalTransactionList) {
        this.historicalTransactionList.addAll(historicalTransactionList);
    }

    public boolean isOverLoaded() {
        return historicalTransactionList.size() > capacity;
    }

    public List<HistoricalTransactionTO> getHistoricalTransactions() {
        return historicalTransactionList;
    }

    public void trimToHalfCapacity() {
        int half = capacity / 2;
        int size = historicalTransactionList.size();
        if(size > half) {
            int from = size - half ;
            historicalTransactionList = historicalTransactionList.subList(from, size);
        }
    }
}
