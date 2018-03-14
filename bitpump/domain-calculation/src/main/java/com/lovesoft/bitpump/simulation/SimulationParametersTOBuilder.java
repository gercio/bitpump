package com.lovesoft.bitpump.simulation;

public final class SimulationParametersTOBuilder {
    private double doubleStep = 0.25;
    private double percentageBuyFrom;
    private double percentageBuyTo;
    private double percentageSelFrom;
    private double percentageSelTo;
    private int triggerTargetCountFrom;
    private int triggerTargetCountTo;
    private double maximumLoosePercentageFrom ;
    private double maximumLoosePercentageTo;
    private int numberOfThreads;
    private double historicalBufferTrimSizePercentage;
    private double moneyAmount;
    private double digitalCurrencyAmount;

    private SimulationParametersTOBuilder() {
    }

    public static SimulationParametersTOBuilder aSimulationParametersTO() {
        return new SimulationParametersTOBuilder();
    }


    public SimulationParametersTOBuilder withHistoricalBufferTrimSizePercentage(double historicalBufferTrimSizePercentage) {
        this.historicalBufferTrimSizePercentage = historicalBufferTrimSizePercentage;
        return this;
    }

    public SimulationParametersTOBuilder withMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
        return this;
    }

    public SimulationParametersTOBuilder withDigitalCurrencyAmount(double digitalCurrencyAmount) {
        this.digitalCurrencyAmount = digitalCurrencyAmount;
        return this;
    }

    public SimulationParametersTOBuilder withDoubleStep(double doubleStep) {
        this.doubleStep = doubleStep;
        return this;
    }

    public SimulationParametersTOBuilder withPercentageBuyFrom(double percentageBuyFrom) {
        this.percentageBuyFrom = percentageBuyFrom;
        return this;
    }

    public SimulationParametersTOBuilder withPercentageBuyTo(double percentageBuyTo) {
        this.percentageBuyTo = percentageBuyTo;
        return this;
    }

    public SimulationParametersTOBuilder withPercentageSelFrom(double percentageSelFrom) {
        this.percentageSelFrom = percentageSelFrom;
        return this;
    }

    public SimulationParametersTOBuilder withPercentageSelTo(double percentageSelTo) {
        this.percentageSelTo = percentageSelTo;
        return this;
    }

    public SimulationParametersTOBuilder withTriggerTargetCountFrom(int triggerTargetCountFrom) {
        this.triggerTargetCountFrom = triggerTargetCountFrom;
        return this;
    }

    public SimulationParametersTOBuilder withTriggerTargetCountTo(int triggerTargetCountTo) {
        this.triggerTargetCountTo = triggerTargetCountTo;
        return this;
    }

    public SimulationParametersTOBuilder withMaximumLoosePercentageFrom(double maximumLoosePercentageFrom) {
        this.maximumLoosePercentageFrom = maximumLoosePercentageFrom;
        return this;
    }

    public SimulationParametersTOBuilder withMaximumLoosePercentageTo(double maximumLoosePercentageTo) {
        this.maximumLoosePercentageTo = maximumLoosePercentageTo;
        return this;
    }

    public SimulationParametersTOBuilder withNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        return this;
    }

    public SimulationParametersTO build() {
        SimulationParametersTO simulationParametersTO = new SimulationParametersTO();
        simulationParametersTO.setDoubleStep(doubleStep);
        simulationParametersTO.setPercentageBuyFrom(percentageBuyFrom);
        simulationParametersTO.setPercentageBuyTo(percentageBuyTo);
        simulationParametersTO.setPercentageSelFrom(percentageSelFrom);
        simulationParametersTO.setPercentageSelTo(percentageSelTo);
        simulationParametersTO.setTriggerTargetCountFrom(triggerTargetCountFrom);
        simulationParametersTO.setTriggerTargetCountTo(triggerTargetCountTo);
        simulationParametersTO.setMaximumLoosePercentageFrom(maximumLoosePercentageFrom);
        simulationParametersTO.setMaximumLoosePercentageTo(maximumLoosePercentageTo);
        simulationParametersTO.setNumberOfThreads(numberOfThreads);
        simulationParametersTO.setHistoricalBufferTrimSizePercentage(historicalBufferTrimSizePercentage);
        simulationParametersTO.setMoneyAmount(moneyAmount);
        simulationParametersTO.setDigitalCurrencyAmount(digitalCurrencyAmount);
        return simulationParametersTO;
    }
}
