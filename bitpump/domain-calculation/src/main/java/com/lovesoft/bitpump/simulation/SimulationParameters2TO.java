package com.lovesoft.bitpump.simulation;

public class SimulationParameters2TO {

    private int numberOfThreads;
    private double historicalBufferTrimSizePercentage;
    private double digitalCurrencyAmount;
    private double moneyAmount;


    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public double getHistoricalBufferTrimSizePercentage() {
        return historicalBufferTrimSizePercentage;
    }

    public void setHistoricalBufferTrimSizePercentage(double historicalBufferTrimSizePercentage) {
        this.historicalBufferTrimSizePercentage = historicalBufferTrimSizePercentage;
    }

    public double getDigitalCurrencyAmount() {
        return digitalCurrencyAmount;
    }

    public void setDigitalCurrencyAmount(double digitalCurrencyAmount) {
        this.digitalCurrencyAmount = digitalCurrencyAmount;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "SimulationParameters2TO{" +
                "numberOfThreads=" + numberOfThreads +
                ", historicalBufferTrimSizePercentage=" + historicalBufferTrimSizePercentage +
                ", digitalCurrencyAmount=" + digitalCurrencyAmount +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
