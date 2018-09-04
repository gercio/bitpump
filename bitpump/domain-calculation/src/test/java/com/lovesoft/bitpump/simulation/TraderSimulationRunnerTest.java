package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.simulation.SimulationDataSupport.ChartName;
import org.junit.jupiter.api.Test;

public class TraderSimulationRunnerTest {

    @Test
    public void simulationShouldNotThrowAnyException() {
        SimulationHistoricalTransaction history = new SimulationHistoricalTransaction(1);
        history.setChartName(ChartName.CHART_01);
        SimulationParametersTO parameters = SimulationParametersTOBuilder.aSimulationParametersTO()
                .withDoubleStep(1)
                .withMaximumLoosePercentageFrom(1)
                .withMaximumLoosePercentageTo(2)
                .withPercentageBuyFrom(1)
                .withPercentageBuyTo(2)
                .withPercentageSelFrom(1)
                .withPercentageSelTo(2)
                .withTriggerTargetCountFrom(1)
                .withTriggerTargetCountTo(2)
                .withNumberOfThreads(1)
                .withMoneyAmount(100)
                .withDigitalCurrencyAmount(1)
                .build();

        TraderSimulationRunner simulationRunner = new TraderSimulationRunner(history, parameters);
        simulationRunner.execute();
    }
}
