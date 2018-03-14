package com.lovesoft.bitpump.calculation.trade;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDecider;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDeciderBuilder;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionParameters;
import com.lovesoft.bitpump.calculation.trade.action.WalletToSupplierObserver;
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

    public Trader createDefaultTrader() {
        Preconditions.checkNotNull(parameters, "Parameters are null, please set them.");
        tradeWallet = new TradeWallet( new TradeWalletTO(0,0));
        if(parameters instanceof WalletToSupplierObserver) {
            ((WalletToSupplierObserver) parameters).observerNewWalletToSupplier(tradeWallet);
        }
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
