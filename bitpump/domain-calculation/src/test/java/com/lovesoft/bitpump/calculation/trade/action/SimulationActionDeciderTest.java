package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.ExchangeDataTOBuilder;
import com.lovesoft.bitpump.simulation.SimulationParametersTO;
import com.lovesoft.bitpump.simulation.SimulationParametersTOBuilder;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import com.lovesoft.bitpump.to.TradeAction;
import com.lovesoft.bitpump.to.TradeWalletTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class SimulationActionDeciderTest {

    @Test
    public void testIt() {
        SimulationParametersTO parameters = SimulationParametersTOBuilder.aSimulationParametersTO()
                .withDoubleStep(0.5)
                .withMaximumLoosePercentageFrom(1)
                .withMaximumLoosePercentageTo(2)
                .withPercentageBuyFrom(0.1)
                .withPercentageBuyTo(2)
                .withPercentageSelFrom(0.1)
                .withPercentageSelTo(2)
                .withTriggerTargetCountFrom(1)
                .withTriggerTargetCountTo(2)
                .withNumberOfThreads(1)
                .withHistoricalBufferTrimSizePercentage(50)
                .build();

        ExchangeDataTOBuilder builder = new ExchangeDataTOBuilder();
        List<HistoricalTransactionTO> historicalTransactions = builder
                .createHistoricalTransactions(95d, 96d, 97d, 98d, 99d, 100d, 101d, 102d, 103d, 104d, 115d, 116d, 117d, 110d, 110d, 90d);
        ExchangeDataTO ed = builder.build(118d, 119d, 120d, 121d, 130d, 132d, 134d, 140d, 145d, 130d, 120d);
        SimulationActionDeciderParameters par = new SimulationActionDeciderParameters();
        par.setNumberOfHistoricalTransactionsToRunSimulation(10);
        par.setWalletToSupplier(() -> new TradeWalletTO(10, 10));
        par.setParameters(parameters);
        TradeActionDecider actionTrigger =  new SimulationActionDeciderBuilder().build(par).get();

        actionTrigger.loadHistoricalData(historicalTransactions);
        Optional<TradeAction> ta = actionTrigger.calculateTradeAction(ed);

        Assertions.assertTrue(ta.isPresent());
        Assertions.assertTrue(ta.get().equals(TradeAction.BUY));
    }
}
