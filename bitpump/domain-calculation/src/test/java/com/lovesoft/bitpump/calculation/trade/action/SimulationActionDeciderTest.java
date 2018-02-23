package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.ExchangeDataTOBuilder;
import com.lovesoft.bitpump.simulation.SimulationParametersTO;
import com.lovesoft.bitpump.simulation.SimulationParametersTOBuilder;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAction;

import java.util.Optional;

public class SimulationActionDeciderTest {

//    @Test
    public void testIt() {
        SimulationParametersTO parameters = SimulationParametersTOBuilder.aSimulationParametersTO()
                .withDoubleStep(0.1)
                .withMaximumLoosePercentageFrom(1)
                .withMaximumLoosePercentageTo(2)
                .withPercentageBuyFrom(1)
                .withPercentageBuyTo(2)
                .withPercentageSelFrom(1)
                .withPercentageSelTo(2)
                .withTriggerTargetCountFrom(1)
                .withTriggerTargetCountTo(2)
                .withNumberOfThreads(1)
                .build();

        ExchangeDataTO ed = new ExchangeDataTOBuilder().build(95d, 96d, 97d, 98d, 99d, 100d);
        SimulationActionDecider actionTrigger =  new SimulationActionDecider(parameters);
        actionTrigger.loadExchangeData(ed);
        Optional<TradeAction> ta = actionTrigger.calculateTradeAction();
        //TODO finish this
//        Assertions.assertTrue(ta.isPresent());
    }
}
