package com.lovesoft.bitpump.calculation.trade;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDecider;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionParameters;
import com.lovesoft.bitpump.calculation.trade.amount.TradeAmountDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;
import com.lovesoft.bitpump.exchange.Exchange;
import com.lovesoft.bitpump.exchange.ExchangeBuilder;
import com.lovesoft.bitpump.support.OptionalConsumer;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeWalletTO;

import java.util.Optional;

public class TraderFactory {
    private Exchange exchange;
    private TradeWallet tradeWallet;
    private Trader trader;
    private TradeActionDecider tradeActionDecider;
    private TradeActionDeciderBuilder tradeActionDeciderBuilder = new TradeActionDeciderBuilder();
    private TradeActionParameters parameters;

    private Optional<Double> stopLoosPercentageOptional = Optional.empty();

    public TraderFactory() {
        // default values
//        tradeActionDeciderBuilder.withPercentage(5);
//        tradeActionDeciderBuilder.withMaximumHistoricalTransactions(5);
    }

    public TraderFactory withParameters(TradeActionParameters parameters) {
        this.parameters = parameters;
        return this;
    }

    public TraderFactory withStopLoosPercentage(double slPercentage) {
        this.stopLoosPercentageOptional = Optional.of(slPercentage);
        return this;
    }

//    public TraderFactory withParameters(TradeActionParameters parameters) {
//        simulationActionDeciderBuilder = null;
//        String builderName = parameters.getOptional(ACTION_DECIDER_BUILDER_NAME)
//                                       .orElseThrow(() ->  new BadParametersException(ACTION_DECIDER_BUILDER_NAME, "Missing value"));
//        if(TREND_ACTION_DECIDER.equals(builderName)) {
//            withMaximumHistoricalTransactions(getLong(parameters, MAXIMUM_HISTORICAL_TRANSACTIONS));
//            withMaximumLoosePercentage(getDouble(parameters, MAXIMUM_LOOS_PERCENTAGE));
//            withPercentageDownSell(getDouble(parameters, PERCENTAGE_DOWN_SELL));
//            withPercentageUpBuy(getDouble(parameters, PERCENTAGE_UP_BUY));
//            withTriggerTargetBuyCount(getLong(parameters, TRIGGER_TRADE_BUY_COUNT).intValue());
//            withTriggerTargetSellCount(getLong(parameters, TRIGGER_TARGET_SELL_COUNT).intValue());
//        } else if(SIMULATION_ACTION_DECIDER.equals(builderName)) {
//            simulationActionDeciderBuilder = new TradeActionDeciderBuilder().buildSimulationActionDecider();
//            SimulationParametersTO simPar = SimulationParametersTOBuilder.aSimulationParametersTO().withNumberOfThreads(getLong(parameters, NUMBER_OF_THREADS).intValue())
//                                         .withDoubleStep(getDouble(parameters, DOUBLE_STEP))
//                                         .withMaximumLoosePercentageFrom(getDouble(parameters, MAXIMUM_LOOSE_PERCENTAGE_FROM))
//                                         .withMaximumLoosePercentageTo(getDouble(parameters, MAXIMUM_LOOSE_PERCENTAGE_TO))
//                                         .withPercentageBuyFrom(getDouble(parameters, PERCENTAGE_BUY_FROM))
//                                         .withPercentageSelFrom(getDouble(parameters, PERCENTAGE_SEL_FROM))
//                                         .withPercentageBuyTo(getDouble(parameters, PERCENTAGE_BUY_TO))
//                                         .withPercentageSelTo(getDouble(parameters, PERCENTAGE_SEL_TO))
//                                         .withTriggerTargetCountFrom(getLong(parameters, TRIGGER_TARGET_COUNT_FROM).intValue())
//                                         .withTriggerTargetCountTo(getLong(parameters, TRIGGER_TARGET_COUNT_TO).intValue())
//                                         .build();
//            simulationActionDeciderBuilder.withParameters(simPar);
//            simulationActionDeciderBuilder.withWalletToSupplier(() -> this.tradeWallet.getTraderWalletTO());
//            simulationActionDeciderBuilder.withNumberOfHistoricalTransactionsToRunSimulation(getLong(parameters, NUMBER_OF_HISTORICAL_DATA_TO_RUN_SIMULATION).intValue() );
//        } else {
//            throw new BadParametersException(ACTION_DECIDER_BUILDER_NAME, "Bad value " + builderName);
//        }
//
//        return this;
//    }


    public Trader createDefaultTrader() {
        Preconditions.checkNotNull(parameters, "Parameters are null, please set them.");
        tradeWallet = new TradeWallet( new TradeWalletTO(0,0));
        exchange = new ExchangeBuilder().withExchangeData( new ExchangeDataTO()).withTradeWallet(tradeWallet).build();
        tradeActionDecider = tradeActionDeciderBuilder.build(parameters);
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
}
