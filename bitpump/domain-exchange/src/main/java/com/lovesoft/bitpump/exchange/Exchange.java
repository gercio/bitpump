package com.lovesoft.bitpump.exchange;

import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAmountTO;
import com.lovesoft.bitpump.to.TradeWalletTO;

public interface Exchange {
    ExchangeDataTO getExchangeData();
    TradeWalletTO doTradeAction(TradeAmountTO amount);
    double calculateMoneyFromDC(double digitalCurrencyAmount);
    double calculateDCFromMoney(double moneyAmount);
}
