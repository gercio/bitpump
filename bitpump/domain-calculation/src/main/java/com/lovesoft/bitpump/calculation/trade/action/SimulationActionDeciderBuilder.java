package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.simulation.SimulationParametersTO;

/**
 * Created 25.02.2018 19:45.
 */
public class SimulationActionDeciderBuilder {

    private SimulationParametersTO parameters;
    private int numberOfHistoricalTransactionsToRunSimulation;

    protected SimulationActionDeciderBuilder() {
    }

    public SimulationActionDeciderBuilder withParameters(SimulationParametersTO parameters) {
        this.parameters = parameters;
        return this;
    }

    public SimulationActionDeciderBuilder withNumberOfHistoricalTransactionsToRunSimulation(int numberOfHistoricalTransactionsToRunSimulation) {
        this.numberOfHistoricalTransactionsToRunSimulation = numberOfHistoricalTransactionsToRunSimulation;
        return this;
    }

    public SimulationActionDecider build() {
        return new SimulationActionDecider(parameters, new HistoricalTransactionsBuffer(numberOfHistoricalTransactionsToRunSimulation * 2));
    }
}
