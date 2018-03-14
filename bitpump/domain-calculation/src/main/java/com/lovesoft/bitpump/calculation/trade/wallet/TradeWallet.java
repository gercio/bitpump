package com.lovesoft.bitpump.calculation.trade.wallet;

import com.lovesoft.bitpump.to.TradeWalletTO;

import java.util.function.Supplier;

public class TradeWallet implements Supplier<TradeWalletTO> {
    private double moneyAmount;
    private double digitalCurrencyAmount;

    public TradeWallet(TradeWalletTO tradeWallet) {
        setValuesFromTO(tradeWallet);
    }

    public TradeWalletTO getTraderWalletTO() {
        return new TradeWalletTO(moneyAmount, digitalCurrencyAmount);
    }

    public TradeWallet setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
        return this;
    }

    public TradeWallet setDigitalCurrencyAmount(double digitalCurrencyAmount) {
        this.digitalCurrencyAmount = digitalCurrencyAmount;
        return this;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public double getDigitalCurrencyAmount() {
        return digitalCurrencyAmount;
    }

    public boolean isEmpty() {
        return getTraderWalletTO().isEmpty();
    }

    public void setValuesFromTO(TradeWalletTO tradeWalletTO) {
        setMoneyAmount(tradeWalletTO.getMoneyAmount())
                .setDigitalCurrencyAmount(tradeWalletTO.getDigitalCurrencyAmount());
    }

    public void addMoneyAmount(double moneyAmount) {
        this.moneyAmount += moneyAmount;
    }

    public void addDigitalCurrency(double digitalCurrencyAmount) {
        this.digitalCurrencyAmount += digitalCurrencyAmount;
    }

    @Override
    public TradeWalletTO get() {
        return getTraderWalletTO();
    }
}
