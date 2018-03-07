package com.lovesoft.bitpump.calculation.trade;

import com.lovesoft.bitpump.calculation.BadParametersException;
import com.lovesoft.bitpump.calculation.Parameters;
import com.lovesoft.bitpump.calculation.trade.action.SimulationActionDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDecider;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.action.TrendActionDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.amount.TradeAmountDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;
import com.lovesoft.bitpump.simulation.SimulationParametersTO;
import com.lovesoft.bitpump.simulation.SimulationParametersTOBuilder;
import com.lovesoft.bitpump.support.OptionalConsumer;
import com.lovesoft.bitpump.to.TradeWalletTO;
import com.lovesoft.bitpump.exchange.Exchange;
import com.lovesoft.bitpump.exchange.ExchangeBuilder;
import com.lovesoft.bitpump.to.ExchangeDataTO;

import java.util.Optional;

public class TraderFactory {
    public static final String ACTION_DECIDER_BUILDER_NAME = "ActionDeciderBuilderName";
    public static final String TREND_ACTION_DECIDER = "TrendActionDecider";
    public static final String SIMULATION_ACTION_DECIDER = "SimulationActionDecider";
    public static final String MAXIMUM_HISTORICAL_TRANSACTIONS = "MaximumHistoricalTransactions";
    public static final String MAXIMUM_LOOS_PERCENTAGE = "MaximumLoosPercentage";
    public static final String PERCENTAGE_DOWN_SELL = "PercentageDownSell";
    public static final String PERCENTAGE_UP_BUY = "PercentageUpBuy";
    public static final String TRIGGER_TRADE_BUY_COUNT = "TriggerTradeBuyCount";
    public static final String TRIGGER_TARGET_SELL_COUNT = "TriggerTargetSellCount";
    public static final String NUMBER_OF_THREADS = "NumberOfThreads";
    public static final String DOUBLE_STEP = "DoubleStep";
    public static final String MAXIMUM_LOOSE_PERCENTAGE_FROM = "MaximumLoosePercentageFrom";
    public static final String MAXIMUM_LOOSE_PERCENTAGE_TO = "MaximumLoosePercentageTo";
    public static final String PERCENTAGE_BUY_FROM = "PercentageBuyFrom";
    public static final String PERCENTAGE_SEL_FROM = "PercentageSelFrom";
    public static final String TRIGGER_TARGET_COUNT_FROM = "TriggerTargetCountFrom";
    public static final String TRIGGER_TARGET_COUNT_TO = "TriggerTargetCountTo";
    public static final String NUMBER_OF_HISTORICAL_DATA_TO_RUN_SIMULATION = "NumberOfHistoricalDataToRunSimulation";
    public static final String PERCENTAGE_BUY_TO = "PercentageBuyTo";
    public static final String PERCENTAGE_SEL_TO = "PercentageSelTo";

    private Exchange exchange;
    private TradeWallet tradeWallet;
    private Trader trader;
    private TradeActionDecider tradeActionDecider;
    private TrendActionDeciderBuilder tradeActionDeciderBuilder = new TradeActionDeciderBuilder().buildTrendTradeActionDecider();
    private SimulationActionDeciderBuilder simulationActionDeciderBuilder;

    private Optional<Double> stopLoosPercentageOptional = Optional.empty();

    public TraderFactory() {
        // default values
        tradeActionDeciderBuilder.withPercentage(5);
        tradeActionDeciderBuilder.withMaximumHistoricalTransactions(5);
    }

    public TraderFactory withParameters(Parameters parameters) {
        simulationActionDeciderBuilder = null;
        String builderName = parameters.getOptional(ACTION_DECIDER_BUILDER_NAME)
                                       .orElseThrow(() ->  new BadParametersException(ACTION_DECIDER_BUILDER_NAME, "Missing value"));
        if(TREND_ACTION_DECIDER.equals(builderName)) {
            withMaximumHistoricalTransactions(getLong(parameters, MAXIMUM_HISTORICAL_TRANSACTIONS));
            withMaximumLoosePercentage(getDouble(parameters, MAXIMUM_LOOS_PERCENTAGE));
            withPercentageDownSell(getDouble(parameters, PERCENTAGE_DOWN_SELL));
            withPercentageUpBuy(getDouble(parameters, PERCENTAGE_UP_BUY));
            withTriggerTargetBuyCount(getLong(parameters, TRIGGER_TRADE_BUY_COUNT).intValue());
            withTriggerTargetSellCount(getLong(parameters, TRIGGER_TARGET_SELL_COUNT).intValue());
        } else if(SIMULATION_ACTION_DECIDER.equals(builderName)) {
            simulationActionDeciderBuilder = new TradeActionDeciderBuilder().buildSimulationActionDecider();
            SimulationParametersTO simPar = SimulationParametersTOBuilder.aSimulationParametersTO().withNumberOfThreads(getLong(parameters, NUMBER_OF_THREADS).intValue())
                                         .withDoubleStep(getDouble(parameters, DOUBLE_STEP))
                                         .withMaximumLoosePercentageFrom(getDouble(parameters, MAXIMUM_LOOSE_PERCENTAGE_FROM))
                                         .withMaximumLoosePercentageTo(getDouble(parameters, MAXIMUM_LOOSE_PERCENTAGE_TO))
                                         .withPercentageBuyFrom(getDouble(parameters, PERCENTAGE_BUY_FROM))
                                         .withPercentageSelFrom(getDouble(parameters, PERCENTAGE_SEL_FROM))
                                         .withPercentageBuyTo(getDouble(parameters, PERCENTAGE_BUY_TO))
                                         .withPercentageSelTo(getDouble(parameters, PERCENTAGE_SEL_TO))
                                         .withTriggerTargetCountFrom(getLong(parameters, TRIGGER_TARGET_COUNT_FROM).intValue())
                                         .withTriggerTargetCountTo(getLong(parameters, TRIGGER_TARGET_COUNT_TO).intValue())
                                         .build();
            simulationActionDeciderBuilder.withParameters(simPar);
            simulationActionDeciderBuilder.withNumberOfHistoricalTransactionsToRunSimulation(getLong(parameters, NUMBER_OF_HISTORICAL_DATA_TO_RUN_SIMULATION).intValue() );
        } else {
            throw new BadParametersException(ACTION_DECIDER_BUILDER_NAME, "Bad value " + builderName);
        }

        return this;
    }

    private Long getLong(Parameters parameters, String fieldName) {
        return parameters.getOptionalLong(fieldName).orElseThrow(() -> new BadParametersException(fieldName, "Bad value " + parameters.get(fieldName)));
    }

    private Double getDouble(Parameters parameters, String paramName) {
        return parameters.getOptionalDouble(paramName)
                                             .orElseThrow(() -> new BadParametersException(paramName, "Bad value " + parameters.get(paramName)));
    }

    public TraderFactory withMaximumHistoricalTransactions(long maximumHistoricalTransactions) {
        this.tradeActionDeciderBuilder.withMaximumHistoricalTransactions(maximumHistoricalTransactions);
        return this;
    }

    public TraderFactory withMaximumLoosePercentage(double stopLoosPercentage) {
        this.stopLoosPercentageOptional = Optional.of(stopLoosPercentage);
        return this;
    }

    public TraderFactory withPercentageDownSell(double percentageDownSell) {
        this.tradeActionDeciderBuilder.withPercentageDownSell(percentageDownSell);
        return this;
    }

    public TraderFactory withPercentageUpBuy(double percentageUpBuy) {
        this.tradeActionDeciderBuilder.withPercentageUpBuy(percentageUpBuy);
        return this;
    }

    public TraderFactory withTriggerTargetBuyCount(int triggerTargetCount) {
        this.tradeActionDeciderBuilder.withTriggerTargetBuyCount(triggerTargetCount);
        return this;
    }

    public TraderFactory withTriggerTargetSellCount(int triggerTargetCount) {
        this.tradeActionDeciderBuilder.withTriggerTargetSellCount(triggerTargetCount);
        return this;
    }

    // TODO separate factory from builders!
    // Agree with above!!!
    public Trader createDefaultTrader() {
        tradeWallet = new TradeWallet( new TradeWalletTO(0,0));
        exchange = new ExchangeBuilder().withExchangeData( new ExchangeDataTO()).withTradeWallet(tradeWallet).build();
        if(simulationActionDeciderBuilder != null) {
            tradeActionDecider = simulationActionDeciderBuilder.build();
        } else {
            tradeActionDecider = tradeActionDeciderBuilder.build();
        }
        TraderBuilder traderBuilder = new TraderBuilder().withExchange(exchange)
                .withTradeActionDecider(tradeActionDecider)
                .withTradeAmountDecider(new TradeAmountDeciderBuilder().build())
                .withTradeWallet(tradeWallet);
        OptionalConsumer.of(this.stopLoosPercentageOptional).ifPresent(p -> traderBuilder.wthStopLoose(new StopLoose(p)));
        trader = traderBuilder.build();
        return trader;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public TradeWallet getTradeWallet() {
        return tradeWallet;
    }

    public Trader getTrader() {
        return trader;
    }

    public TradeActionDecider getTradeActionDecider() {
        return tradeActionDecider;
    }
}
