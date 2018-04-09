package com.lovesoft.bitpump.simulation;

public class SimulationParametersTO {

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
    private double digitalCurrencyAmount;
    private double moneyAmount;
    private boolean calculateStatisticsOnlyForDX;

    public double getDoubleStep() {
        return doubleStep;
    }

    public void setDoubleStep(double doubleStep) {
        this.doubleStep = doubleStep;
    }

    public double getPercentageBuyFrom() {
        return percentageBuyFrom;
    }

    public void setPercentageBuyFrom(double percentageBuyFrom) {
        this.percentageBuyFrom = percentageBuyFrom;
    }

    public double getPercentageBuyTo() {
        return percentageBuyTo;
    }

    public void setPercentageBuyTo(double percentageBuyTo) {
        this.percentageBuyTo = percentageBuyTo;
    }

    public double getPercentageSelFrom() {
        return percentageSelFrom;
    }

    public void setPercentageSelFrom(double percentageSelFrom) {
        this.percentageSelFrom = percentageSelFrom;
    }

    public double getPercentageSelTo() {
        return percentageSelTo;
    }

    public void setPercentageSelTo(double percentageSelTo) {
        this.percentageSelTo = percentageSelTo;
    }

    public int getTriggerTargetCountFrom() {
        return triggerTargetCountFrom;
    }

    public void setTriggerTargetCountFrom(int triggerTargetCountFrom) {
        this.triggerTargetCountFrom = triggerTargetCountFrom;
    }

    public int getTriggerTargetCountTo() {
        return triggerTargetCountTo;
    }

    public void setTriggerTargetCountTo(int triggerTargetCountTo) {
        this.triggerTargetCountTo = triggerTargetCountTo;
    }

    public double getMaximumLoosePercentageFrom() {
        return maximumLoosePercentageFrom;
    }

    public void setMaximumLoosePercentageFrom(double maximumLoosePercentageFrom) {
        this.maximumLoosePercentageFrom = maximumLoosePercentageFrom;
    }

    public double getMaximumLoosePercentageTo() {
        return maximumLoosePercentageTo;
    }

    public void setMaximumLoosePercentageTo(double maximumLoosePercentageTo) {
        this.maximumLoosePercentageTo = maximumLoosePercentageTo;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public double getHistoricalBufferTrimSizePercentage() {
        return historicalBufferTrimSizePercentage;
    }

    public void setHistoricalBufferTrimSizePercentage(double historicalBufferTrimSizePercentage) {
        this.historicalBufferTrimSizePercentage = historicalBufferTrimSizePercentage;
    }

    @Override
    public String toString() {
        return "SimulationParametersTO{" +
                "doubleStep=" + doubleStep +
                ", percentageBuyFrom=" + percentageBuyFrom +
                ", percentageBuyTo=" + percentageBuyTo +
                ", percentageSelFrom=" + percentageSelFrom +
                ", percentageSelTo=" + percentageSelTo +
                ", triggerTargetCountFrom=" + triggerTargetCountFrom +
                ", triggerTargetCountTo=" + triggerTargetCountTo +
                ", maximumLoosePercentageFrom=" + maximumLoosePercentageFrom +
                ", maximumLoosePercentageTo=" + maximumLoosePercentageTo +
                ", numberOfThreads=" + numberOfThreads +
                ", historicalBufferTrimSizePercentage=" + historicalBufferTrimSizePercentage +
                ", digitalCurrencyAmount=" + digitalCurrencyAmount +
                ", moneyAmount=" + moneyAmount +
                ", calculateStatisticsOnlyForDX=" + calculateStatisticsOnlyForDX +
                '}';
    }

    public void setDigitalCurrencyAmount(double digitalCurrencyAmount) {
        this.digitalCurrencyAmount = digitalCurrencyAmount;
    }

    public double getDigitalCurrencyAmount() {
        return digitalCurrencyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public boolean getCalculateStatisticsOnlyForDX() {
        return calculateStatisticsOnlyForDX;
    }

    public void setCalculateStatisticsOnlyForDX(boolean calculateStatisticsOnlyForDX) {
        this.calculateStatisticsOnlyForDX = calculateStatisticsOnlyForDX;
    }
}
