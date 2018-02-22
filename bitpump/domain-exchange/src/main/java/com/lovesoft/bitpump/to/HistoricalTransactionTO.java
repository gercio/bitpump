package com.lovesoft.bitpump.to;

import java.util.Objects;

public class HistoricalTransactionTO implements Comparable<HistoricalTransactionTO> {
    private long transactionTimeInMs;
    private double transactionPrice;

    public HistoricalTransactionTO(long transactionTimeInMs, double transactionPrice) {
        this.transactionTimeInMs = transactionTimeInMs;
        this.transactionPrice = transactionPrice;
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
}
