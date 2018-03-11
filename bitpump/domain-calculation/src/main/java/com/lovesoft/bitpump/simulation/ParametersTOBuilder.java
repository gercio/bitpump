package com.lovesoft.bitpump.simulation;

public final class ParametersTOBuilder {
    private double percentageBuy;
    private double percentageSel;
    private int triggerTargetBuyCount;
    private int triggerTargetSellCount;
    private double maximumLoosePercentage;
    private double startDigitalCurrencyAmount;
    private double startMoneyAmount;
    private HistoricalTransactionSource historicalTransactionSource;

    private ParametersTOBuilder() {
    }

    public static ParametersTOBuilder aParametersTO() {
        return new ParametersTOBuilder();
    }


    public ParametersTOBuilder withStartDigitalCurrencyAmount(double startDigitalCurrencyAmount) {
        this.startDigitalCurrencyAmount = startDigitalCurrencyAmount;
        return this;
    }

    public ParametersTOBuilder withStartMoneyAmount(double startMoneyAmount) {
        this.startMoneyAmount = startMoneyAmount;
        return this;
    }

    public ParametersTOBuilder withPercentageBuy(double percentageBuy) {
        this.percentageBuy = percentageBuy;
        return this;
    }

    public ParametersTOBuilder withPercentageSel(double percentageSel) {
        this.percentageSel = percentageSel;
        return this;
    }

    public ParametersTOBuilder withTriggerTargetBuyCount(int triggerTargetCount) {
        this.triggerTargetBuyCount = triggerTargetCount;
        return this;
    }

    public ParametersTOBuilder withTriggerTargetSellCount(int triggerTargetSellCount) {
        this.triggerTargetSellCount = triggerTargetSellCount;
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
        parametersTO.setPercentageBuy(percentageBuy);
        parametersTO.setPercentageSel(percentageSel);
        parametersTO.setTriggerTargetBuyCount(triggerTargetBuyCount);
        parametersTO.setTriggerTargetSellCount(triggerTargetSellCount);
        parametersTO.setMaximumLoosePercentage(maximumLoosePercentage);
        parametersTO.setHistoricalTransactionSource(historicalTransactionSource);
        return parametersTO;
    }
}
