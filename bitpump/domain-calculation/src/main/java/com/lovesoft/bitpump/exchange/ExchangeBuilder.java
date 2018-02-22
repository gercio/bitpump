package com.lovesoft.bitpump.exchange;

import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;
import com.lovesoft.bitpump.to.ExchangeDataTO;

public class ExchangeBuilder {

    private ExchangeDataTO exchangeData;
    private TradeWallet tradeWallet;

    public ExchangeBuilder withExchangeData(ExchangeDataTO exchangeData) {
        this.exchangeData = exchangeData;
        return this;
    }

    public Exchange build() {
        return new LocalExchange(exchangeData, tradeWallet); // LocalExchange is just for tests....
    }

    public ExchangeBuilder withTradeWallet(TradeWallet tradeWallet) {
        this.tradeWallet = tradeWallet;
        return this;
    }
}
