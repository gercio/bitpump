package com.lovesoft.bitpump.calculation.trade.action;

import java.util.Optional;

public abstract class TradeActionBuilder<T extends TradeActionParameters> {

    public Optional<TradeActionDecider> build(T parameters) {
        Class<T> c = getTradeActionParametersClass();
        if(parameters != null && c.isInstance(parameters)) {
            return buildFromParameters(parameters);
        }
        return Optional.empty();
    }

    protected abstract Optional<TradeActionDecider> buildFromParameters(T parameters);

    protected abstract Class<T> getTradeActionParametersClass();
}
