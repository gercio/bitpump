package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAction;

import java.util.Optional;

public interface TradeActionDecider {
    Optional<TradeAction> calculateTradeAction();
    void loadExchangeData(ExchangeDataTO exchangeData);
}
