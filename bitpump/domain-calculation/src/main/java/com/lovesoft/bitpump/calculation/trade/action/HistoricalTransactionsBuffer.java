package com.lovesoft.bitpump.calculation.trade.action;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.commons.MathSupport;
import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
import com.lovesoft.bitpump.simulation.HistoricalTransactionSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 25.02.2018 12:44.
 */
public class HistoricalTransactionsBuffer implements HistoricalTransactionSource {
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
        return historicalTransactionList.size() >= capacity;
    }

    public List<HistoricalTransactionTO> getHistoricalTransactionsTO() {
        return historicalTransactionList;
    }

    public void trimToHalfCapacity() {
        keepNumberOfLastItems(capacity / 2);
    }

    private void keepNumberOfLastItems(int countLastToKeep) {
        int size = historicalTransactionList.size();
        if (size > countLastToKeep) {
            int from = size - countLastToKeep;
            historicalTransactionList = historicalTransactionList.subList(from, size);
        }
    }

    public void trimToPercentOfCapacity(double percentage) {
        if (historicalTransactionList.isEmpty()) {
            return;
        }
        Preconditions.checkArgument(percentage > 1 && percentage < 99, "Bad percentage " + percentage);
        int countToKeep = (int) MathSupport.calculateValueOfXPercentFromY(percentage, capacity);
        keepNumberOfLastItems(countToKeep);
    }

    @Override
    public List<HistoricalTransactionTO> getHistoricalTransactions() {
        return getHistoricalTransactionsTO();
    }
}

