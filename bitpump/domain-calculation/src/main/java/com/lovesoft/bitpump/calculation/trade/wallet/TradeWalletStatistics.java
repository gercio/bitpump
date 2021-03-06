package com.lovesoft.bitpump.calculation.trade.wallet;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.commons.MathSupport;
import com.lovesoft.bitpump.commons.WithLog;
import com.lovesoft.bitpump.exchange.Exchange;
import com.lovesoft.bitpump.to.TradeWalletTO;

import java.util.Optional;

public class TradeWalletStatistics implements WithLog {
    private double startAssetValue;
    private double lastAssetValue;
    private Exchange exchange;
    private Optional<TradeWalletTO> lastWallet = Optional.empty();

    public TradeWalletStatistics(Exchange exchange) {
        Preconditions.checkNotNull(exchange);
        this.exchange = exchange;
    }

    public void start(TradeWalletTO start) {
        Preconditions.checkNotNull(start);
        this.startAssetValue = calculate(start);
    }

    private double calculate(TradeWalletTO walletTO) {
        return exchange.calculateMoneyFromDC(walletTO.getDigitalCurrencyAmount()) + walletTO.getMoneyAmount();
    }

    public synchronized void updateWalletTO(TradeWalletTO last) {
        this.lastWallet = Optional.of(last);
        this.lastAssetValue = calculate(last);
    }

    public synchronized double calculateAssetChangeInPercentage() {
        if(startAssetValue != 0) {
            return MathSupport.calculatePercentageOfXisY(startAssetValue, lastAssetValue) - 100;
        }
        return 0;
    }

    public Optional<TradeWalletTO> getLastWallet() {
        return lastWallet;
    }
}
