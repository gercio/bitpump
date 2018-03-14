package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.calculation.HistoricalTransactions;

import java.util.Optional;

public class TrendActionDeciderBuilder extends TradeActionBuilder<TrendTradeActionDeciderParameters> {

    @Override
    protected Optional<TradeActionDecider> buildFromParameters(TrendTradeActionDeciderParameters par) {
        TrendTradeActionDecider tad = new TrendTradeActionDecider(par.getPercentageUpBuy(), par.getPercentageDownSell(), new HistoricalTransactions(1000));
        tad.setTargetBuyCount(par.getTriggerTargetBuyCount());
        tad.setTargetSellCount(par.getTriggerTargetSellCount());
        return Optional.of(tad);
    }

    @Override
    protected Class getTradeActionParametersClass() {
        return TrendTradeActionDeciderParameters.class;
    }
}
