package com.lovesoft.bitpump.calculation.trade;

import com.lovesoft.bitpump.calculation.trade.action.TradeActionDecider;
import com.lovesoft.bitpump.calculation.trade.amount.TradeAmountDecider;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;
import com.lovesoft.bitpump.commons.OptionalConsumer;
import com.lovesoft.bitpump.exchange.Exchange;

import java.util.Optional;

public class TraderBuilder {

    private Exchange exchange;
    private TradeActionDecider tradeActionDecider;
    private TradeAmountDecider tradeAmountDecider;
    private TradeWallet tradeWallet;
    private Optional<StopLoose> stopLooseOptional = Optional.empty();

    public void wthStopLoose(StopLoose stopLoose) {
        this.stopLooseOptional = Optional.of(stopLoose);
    }

    public TraderBuilder withExchange(Exchange exchange) {
        this.exchange = exchange;
        return this;
    }

    public TraderBuilder withTradeActionDecider(TradeActionDecider tradeActionDecider) {
        this.tradeActionDecider = tradeActionDecider;
        return this;
    }

    public TraderBuilder withTradeWallet(TradeWallet tradeWallet) {
        this.tradeWallet = tradeWallet;
        return this;
    }

    public TraderBuilder withTradeAmountDecider(TradeAmountDecider tradeActionDecider) {
        this.tradeAmountDecider = tradeActionDecider;
        return this;
    }

    public Trader build() {
        SimpleTrader trader = new SimpleTrader(exchange, tradeActionDecider, tradeAmountDecider, tradeWallet);
        OptionalConsumer.of(stopLooseOptional).ifPresent(trader::setStopLoose);
        return trader;
    }
}
