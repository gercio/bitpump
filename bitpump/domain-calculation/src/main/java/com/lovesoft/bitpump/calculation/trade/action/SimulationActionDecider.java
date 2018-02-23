package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.simulation.SimulationParametersTO;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAction;

import java.util.Optional;

public class SimulationActionDecider implements TradeActionDecider {

    protected SimulationActionDecider(SimulationParametersTO parameters) {

    }

    @Override
    public Optional<TradeAction> calculateTradeAction() {
        return Optional.empty();
    }

    @Override
    public void loadExchangeData(ExchangeDataTO exchangeData) {

    }
}
