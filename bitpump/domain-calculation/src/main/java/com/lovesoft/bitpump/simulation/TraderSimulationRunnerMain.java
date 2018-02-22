package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.simulation.SimulationDataSupport.ChartName;

public class TraderSimulationRunnerMain {

    public static void main(String[] arg) {
        SimulationHistoricalTransaction simulationHistoricalTransaction = new SimulationHistoricalTransaction();
        simulationHistoricalTransaction.setChartName(ChartName.Bitmarket24_02);
        new TraderSimulationRunner(simulationHistoricalTransaction, getParameters()).execute();
    }

    public static SimulationParametersTO getParameters() {
        return SimulationParametersTOBuilder.aSimulationParametersTO()
                .withDoubleStep(0.3)
                .withPercentageBuyFrom(0.1)
                .withPercentageBuyTo(5.0)
                .withPercentageSelFrom(0.1)
                .withPercentageSelTo(5.0)
                .withTriggerTargetCountFrom(1)
                .withTriggerTargetCountTo(10)
                .withMaximumLoosePercentageFrom(5)
                .withMaximumLoosePercentageTo(15)
                .build();
    }
}
