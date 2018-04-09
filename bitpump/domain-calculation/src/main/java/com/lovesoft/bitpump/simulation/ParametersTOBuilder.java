package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.calculation.trade.action.TradeActionParameters;

public final class ParametersTOBuilder {
    private double maximumLoosePercentage;
    private double startDigitalCurrencyAmount;
    private double startMoneyAmount;
    private HistoricalTransactionSource historicalTransactionSource;
    private TradeActionParameters parameters;
    private boolean calculateStatisticsOnlyForDX = false;

    private ParametersTOBuilder() {
    }

    public static ParametersTOBuilder aParametersTO() {
        return new ParametersTOBuilder();
    }

    public ParametersTOBuilder withStartDigitalCurrencyAmount(double startDigitalCurrencyAmount) {
        this.startDigitalCurrencyAmount = startDigitalCurrencyAmount;
        return this;
    }

    public ParametersTOBuilder withCalculateStatisticsOnlyForDX(boolean calculateStatisticsOnlyForDX) {
        this.calculateStatisticsOnlyForDX = calculateStatisticsOnlyForDX;
        return this;
    }

    public ParametersTOBuilder withStartMoneyAmount(double startMoneyAmount) {
        this.startMoneyAmount = startMoneyAmount;
        return this;
    }

    public ParametersTOBuilder withTradeActionDeciderParameters(TradeActionParameters parameters) {
        this.parameters = parameters;
        return this;
    }

    public ParametersTOBuilder withMaximumLoosePercentage(double maximumLoosePercentage) {
        this.maximumLoosePercentage = maximumLoosePercentage;
        return this;
    }

    public ParametersTOBuilder withHistoricalTransactionSource(HistoricalTransactionSource historicalTransactionSource) {
        this.historicalTransactionSource = historicalTransactionSource;
        return this;
    }

    public ParametersTO build() {
        ParametersTO parametersTO = new ParametersTO();
        parametersTO.setMaximumLoosePercentage(maximumLoosePercentage);
        parametersTO.setHistoricalTransactionSource(historicalTransactionSource);
        parametersTO.setStartDigitalCurrencyAmount(startDigitalCurrencyAmount);
        parametersTO.setStartMoneyAmount(startMoneyAmount);
        parametersTO.setTrendParameters(parameters);
        parametersTO.setCalculateStatisticsOnlyForDX(calculateStatisticsOnlyForDX);
        return parametersTO;
    }
}
