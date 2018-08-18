package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;

import java.util.List;

public interface HistoricalTransactionSource {
    List<HistoricalTransactionTO> getHistoricalTransactions();
}
