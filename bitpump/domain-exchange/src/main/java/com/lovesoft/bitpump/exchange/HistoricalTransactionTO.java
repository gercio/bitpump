package com.lovesoft.bitpump.exchange;

import com.lovesoft.bitpump.commons.ValueAtTime;

import java.util.Objects;

public class HistoricalTransactionTO implements Comparable<HistoricalTransactionTO>, ValueAtTime{
    private long transactionTimeInMs;
    private double transactionPrice;
    private double transactionPriceMVA;

    public HistoricalTransactionTO(long transactionTimeInMs, double transactionPrice) {
       this(transactionTimeInMs, transactionPrice, transactionPrice);
    }

    public HistoricalTransactionTO(long transactionTimeInMs, double transactionPrice, double transactionPriceMVA) {
        this.transactionTimeInMs = transactionTimeInMs;
        this.transactionPrice = transactionPrice;
        this.transactionPriceMVA = transactionPriceMVA;
    }

    public HistoricalTransactionTO(HistoricalTransactionTO to) {
        this(to.getTransactionTimeInMs(), to.getTransactionPrice(), to.getTransactionPriceMVA());
    }

    public double getTransactionPrice() {
        return transactionPrice;
    }

    public long getTransactionTimeInMs() {
        return transactionTimeInMs;
    }

    @Override
    public int compareTo(HistoricalTransactionTO o) {
        return Long.compare(transactionTimeInMs, o.transactionTimeInMs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalTransactionTO that = (HistoricalTransactionTO) o;
        return transactionTimeInMs == that.transactionTimeInMs;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionTimeInMs);
    }

    public double getTransactionPriceMVA() {
        return transactionPriceMVA;
    }

    public void setTransactionPriceMVA(double transactionPriceMVA) {
        this.transactionPriceMVA = transactionPriceMVA;
    }

    @Override
    public long getTimeInMS() {
        return getTransactionTimeInMs();
    }

    @Override
    public double getValue() {
        return getTransactionPrice();
    }
}
