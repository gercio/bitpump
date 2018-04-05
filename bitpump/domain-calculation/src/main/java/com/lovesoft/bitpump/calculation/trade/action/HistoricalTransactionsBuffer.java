package com.lovesoft.bitpump.calculation.trade.action;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.simulation.HistoricalTransactionSource;
import com.lovesoft.bitpump.support.MathSupport;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if(size > countLastToKeep) {
            int from = size - countLastToKeep ;
            historicalTransactionList = historicalTransactionList.subList(from, size);
        }
    }

    public void trimToPercentOfCapacity(double percentage) {
        if(historicalTransactionList.size() == 0) {
            return;
        }
        Preconditions.checkArgument(percentage > 1 && percentage < 99, "Bad percentage " + percentage);
        int countToKeep = (int) MathSupport.calculateValueOfXPercentFromY(percentage, capacity);
        keepNumberOfLastItems(countToKeep);
    }

    @Override
    public List<Double> getHistoricalTransactions() {
        return historicalTransactionList.stream().map( to -> to.getTransactionPrice()).collect(Collectors.toList());
    }

    @Override
    public List<Double> getHistoricalTransactionsMVA() {
        return historicalTransactionList.stream().filter(to -> to.getTransactionPriceMVA().isPresent()).map( to -> to.getTransactionPriceMVA().get()).collect(Collectors.toList());
    }
}
