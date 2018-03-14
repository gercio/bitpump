package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.simulation.SimulationDataSupport.ChartName;

public class TraderSimulationRunnerMain {

    public static void main(String[] arg) {
        SimulationHistoricalTransaction simulationHistoricalTransaction = new SimulationHistoricalTransaction();
        simulationHistoricalTransaction.setChartName(ChartName.Bitmarket24_04);
        new TraderSimulationRunner(simulationHistoricalTransaction, getParameters()).execute();
    }

    public static SimulationParametersTO getParameters() {
        return SimulationParametersTOBuilder.aSimulationParametersTO()
                .withDoubleStep(0.1)
                .withPercentageBuyFrom(2.1)
                .withPercentageBuyTo(2.3)
                .withPercentageSelFrom(0.6)
                .withPercentageSelTo(0.8)
                .withTriggerTargetCountFrom(1)
                .withTriggerTargetCountTo(9)
                .withMaximumLoosePercentageFrom(4)
                .withMaximumLoosePercentageTo(7)
                .withNumberOfThreads(5)
                .withMoneyAmount(100)
                .withDigitalCurrencyAmount(0)
                .build();
    }
}
