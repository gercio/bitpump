package com.lovesoft.bitpump.calculation.trade.action.candle;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDecider;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAction;

import java.util.Optional;

public class CandleTimeIndicatorActionDecider implements TradeActionDecider {

    private CTIADParameters parameters;

    public CandleTimeIndicatorActionDecider(CTIADParameters parameters) {
        Preconditions.checkNotNull(parameters);
        this.parameters = parameters;
    }

    @Override
    public Optional<TradeAction> calculateTradeAction(ExchangeDataTO exchangeData) {

        return Optional.empty();
    }
}
