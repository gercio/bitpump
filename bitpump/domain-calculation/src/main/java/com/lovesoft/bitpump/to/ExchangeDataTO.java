package com.lovesoft.bitpump.to;

import java.util.Collections;
import java.util.List;

public class ExchangeDataTO {
    private List<HistoricalTransactionTO> historicalTransactions;
    private double buyExchangeRate;
    private double sellExchangeRate;

    public ExchangeDataTO() {
        this(Collections.EMPTY_LIST);
    }

    public ExchangeDataTO(List<HistoricalTransactionTO> historicalTransactions) {
        this(historicalTransactions, 0, 0);
    }

    public ExchangeDataTO(List<HistoricalTransactionTO> historicalTransactions, double buyExchangeRate, double sellExchangeRate) {
        this.historicalTransactions = Collections.unmodifiableList(historicalTransactions);
        this.buyExchangeRate = buyExchangeRate;
        this.sellExchangeRate = sellExchangeRate;
    }

    public List<HistoricalTransactionTO> getHistoricalTransactions() {
        return historicalTransactions;
    }

    public double getBuyExchangeRate() {
        return buyExchangeRate;
    }

    public double getSellExchangeRate() {
        return sellExchangeRate;
    }
}
