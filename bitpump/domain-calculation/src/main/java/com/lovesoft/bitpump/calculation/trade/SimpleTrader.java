package com.lovesoft.bitpump.calculation.trade;

import com.lovesoft.bitpump.support.OptionalConsumerBoolean;
import com.lovesoft.bitpump.to.TradeAction;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDecider;
import com.lovesoft.bitpump.calculation.trade.amount.TradeAmountDecider;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;
import com.lovesoft.bitpump.to.TradeWalletTO;
import com.lovesoft.bitpump.exchange.Exchange;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.support.WithLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class SimpleTrader implements Trader, WithLog {
    private Logger LOG = LoggerFactory.getLogger(SimpleTrader.class);

    private Exchange exchange;
    private TradeActionDecider tradeActionDecider;
    private TradeAmountDecider tradeAmountDecider;
    private TradeWallet tradeWallet;
    private Optional<StopLoose> stopLoose = Optional.empty();
    private TradeData tradeData = new TradeData();

    protected SimpleTrader(Exchange exchange, TradeActionDecider tradeActionDecider, TradeAmountDecider tradeAmountDecider, TradeWallet tradeWallet) {
        this.exchange = exchange;
        this.tradeActionDecider = tradeActionDecider;
        this.tradeAmountDecider = tradeAmountDecider;
        this.tradeWallet = tradeWallet;
    }

    public void setStopLoose(StopLoose stopLoose) {
        this.stopLoose = Optional.of(stopLoose);
        stopLoose.setTradeData(tradeData);
    }

    @Override
    public void doTrades() {
        ExchangeDataTO exchangeData = exchange.getExchangeData();
        tradeData.setSellExchangeRate(exchangeData.getSellExchangeRate());


        if(tradeWallet.isEmpty()) {
            logWarn(LOG, "Wallet is empty - no trade will be made! Please top up your wallet :)");
        } else {
            tradeActionDecider.loadExchangeData(exchangeData);
            if(isStopLoose()) {
                // Now do panic sell!!!
                logDebug(LOG, "Stop loos started! Exchange rate goes so low {} that we need to sell it all!", tradeData.getSellExchangeRate());
                letsTrade(TradeAction.SELL, exchangeData);
            } else {
                tradeActionDecider.calculateTradeAction().ifPresent(ta -> letsTrade(ta, exchangeData));
            }
        }
    }

    private boolean isStopLoose() {
        return OptionalConsumerBoolean.of(stopLoose).ifPresent(sl -> sl.stopLoose()).ifNotPresent(() -> false).getBoolean();
    }

    @Override
    public TradeWallet getActualWallet() {
        return tradeWallet;
    }

    private void letsTrade(TradeAction ta, ExchangeDataTO exchangeData) {
        tradeData.setSellExchangeRate(exchangeData.getSellExchangeRate());
        if(tradeData.getLastAction().isPresent() && tradeData.getLastAction().get().equals(ta)) {
            logDebug(LOG, "Can't do " + ta + " right now (this was last action)");
            return;
        }
        tradeAmountDecider.setTradeWallet(tradeWallet.getTraderWalletTO());
        tradeAmountDecider.calculateAmount(ta).ifPresent(amount -> {
            if(ta.equals(TradeAction.SELL)) {
                if(tradeData.getLastBuyExchangeRate().isPresent() && !isStopLoose()) {
                    if (tradeData.getLastBuyExchangeRate().get() > exchangeData.getSellExchangeRate()) {
                        logDebug(LOG, "Can't sell it now, because there will be money loos. Buy was for {} and wanna sell for {}", tradeData.getLastBuyExchangeRate().get(), exchangeData.getSellExchangeRate());
                        return;
                    }
                }
                tradeData.setLastBuyExchangeRate(null);
            }
            setTradeWallet(exchange.doTradeAction(amount));
            tradeData.setLastAction(amount.getAction());
            if(ta.equals(TradeAction.BUY)) {
                tradeData.setLastBuyExchangeRate(exchangeData.getBuyExchangeRate());
            }
        });
    }

    private void setTradeWallet(TradeWalletTO tradeWallet) {
        this.tradeWallet.setValuesFromTO(tradeWallet);
    }
}
