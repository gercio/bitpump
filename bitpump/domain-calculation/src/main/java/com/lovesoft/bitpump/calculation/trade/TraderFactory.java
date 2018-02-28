package com.lovesoft.bitpump.calculation.trade;

import com.lovesoft.bitpump.calculation.trade.action.SimulationActionDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDecider;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.action.TrendActionDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.amount.TradeAmountDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;
import com.lovesoft.bitpump.support.OptionalConsumer;
import com.lovesoft.bitpump.to.TradeWalletTO;
import com.lovesoft.bitpump.exchange.Exchange;
import com.lovesoft.bitpump.exchange.ExchangeBuilder;
import com.lovesoft.bitpump.to.ExchangeDataTO;

import java.util.Optional;

public class TraderFactory {
    private Exchange exchange;
    private TradeWallet tradeWallet;
    private Trader trader;
    private TradeActionDecider tradeActionDecider;
    private TrendActionDeciderBuilder tradeActionDeciderBuilder = new TradeActionDeciderBuilder().buildTrendTradeActionDecider();
    private SimulationActionDeciderBuilder simulationActionDeciderBuilder = new TradeActionDeciderBuilder().buildSimulationActionDecider();

    private Optional<Double> stopLoosPercentageOptional = Optional.empty();

    public TraderFactory() {
        // default values
        tradeActionDeciderBuilder.withPercentage(5);
        tradeActionDeciderBuilder.withMaximumHistoricalTransactions(5);
    }

    public TraderFactory withMaximumHistoricalTransactions(long maximumHistoricalTransactions) {
        this.tradeActionDeciderBuilder.withMaximumHistoricalTransactions(maximumHistoricalTransactions);
        return this;
    }
    public TraderFactory withPercentage(double percentage) {
        this.tradeActionDeciderBuilder.withPercentage(percentage);
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

    public Trader createDefaultTrader() {
        tradeWallet = new TradeWallet( new TradeWalletTO(0,0));
        exchange = new ExchangeBuilder().withExchangeData( new ExchangeDataTO()).withTradeWallet(tradeWallet).build();
        tradeActionDecider = tradeActionDeciderBuilder.build();
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
