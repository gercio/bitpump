package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import com.lovesoft.bitpump.to.TradeAction;

import java.util.List;
import java.util.Optional;

public interface TradeActionDecider {

    /**
     * Called to give TradeActionDecider historical transactions before trading starts.
     * @param historicalTransactions
     */
    void loadHistoricalData(List<HistoricalTransactionTO> historicalTransactions);
    /**
     * Calculate TradeAction with given actual exchange data.
     * @param exchangeData
     * @return
     */
    Optional<TradeAction> calculateTradeAction(ExchangeDataTO exchangeData);
}
