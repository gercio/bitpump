package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAction;

import java.util.Optional;

public interface TradeActionDecider {

    /**
     * Calculate TradeAction with given actual exchange data.
     * @param exchangeData
     * @return
     */
    Optional<TradeAction> calculateTradeAction(ExchangeDataTO exchangeData);
}
