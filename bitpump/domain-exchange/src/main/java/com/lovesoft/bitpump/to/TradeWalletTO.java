package com.lovesoft.bitpump.to;

import com.lovesoft.bitpump.support.MathSupport;

public class  TradeWalletTO {
    private double moneyAmount;
    private double digitalCurrencyAmount;

    public TradeWalletTO(double moneyAmount, double digitalCurrencyAmount) {
        this.moneyAmount = moneyAmount;
        this.digitalCurrencyAmount = digitalCurrencyAmount;
    }

    public TradeWalletTO(TradeWalletTO tradeWalletTO) {
        this.moneyAmount = tradeWalletTO.moneyAmount;
        this.digitalCurrencyAmount = tradeWalletTO.digitalCurrencyAmount;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public double getDigitalCurrencyAmount() {
        return digitalCurrencyAmount;
    }

    public boolean haveNoMoney() {
        return MathSupport.isZero(moneyAmount);
    }

    public boolean haveNoDigitalCurrency() {
        return MathSupport.isZero(digitalCurrencyAmount);
    }

    public boolean isEmpty() {
        return haveNoMoney() && haveNoDigitalCurrency();
    }

    public void addMoneyAmount(double moneyToAdd) {
        moneyAmount += moneyToAdd;
    }

    public void addDigitalCurrency(double digitalCurrencyToAdd) {
        digitalCurrencyAmount += digitalCurrencyToAdd;
    }

    @Override
    public String toString() {
        return "TradeWalletTO{" +
                "moneyAmount=" + moneyAmount +
                ", digitalCurrencyAmount=" + digitalCurrencyAmount +
                '}';
    }
}
