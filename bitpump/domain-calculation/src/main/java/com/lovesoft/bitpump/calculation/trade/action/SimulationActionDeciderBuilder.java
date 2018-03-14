package com.lovesoft.bitpump.calculation.trade.action;

import java.util.Optional;

/**
 * Created 25.02.2018 19:45.
 */
public class SimulationActionDeciderBuilder extends TradeActionBuilder<SimulationActionDeciderParameters> {
    @Override
    protected Optional<TradeActionDecider> buildFromParameters(SimulationActionDeciderParameters parameters) {
        return Optional.of(new SimulationActionDecider(parameters.getParameters(),
                new HistoricalTransactionsBuffer(parameters.getNumberOfHistoricalTransactionsToRunSimulation()), parameters.getWalletToSupplier()));
    }

    @Override
    protected Class<SimulationActionDeciderParameters> getTradeActionParametersClass() {
        return SimulationActionDeciderParameters.class;
    }
}
