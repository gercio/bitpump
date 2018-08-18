package com.lovesoft.bitpump.exchange;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;
import com.lovesoft.bitpump.commons.BitPumpRuntimeException;
import com.lovesoft.bitpump.commons.WithLog;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAmountTO;
import com.lovesoft.bitpump.to.TradeWalletTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * Exchange used for simulation/test purpose only
 * @author Patryk Kaluzny 2018.02.09
 */

public class LocalSimulationExchange implements Exchange, WithLog {
    private static Logger LOG = LoggerFactory.getLogger(LocalSimulationExchange.class);
    private List<HistoricalTransactionTO> historicalTransactions;
    private TradeWallet tradeWallet;

    protected LocalSimulationExchange(ExchangeDataTO exchangeData, TradeWallet tradeWallet) {
        Preconditions.checkNotNull(exchangeData);
        Preconditions.checkNotNull(tradeWallet);
        this.historicalTransactions = Lists.newArrayList(exchangeData.getHistoricalTransactions());
        this.tradeWallet = tradeWallet;
    }

    @Override
    public ExchangeDataTO getExchangeData() {
        double exchangeRate = calculateExchangeRate();
        return new ExchangeDataTO(historicalTransactions, exchangeRate, exchangeRate);
    }

    @Override
    public TradeWalletTO doTradeAction(TradeAmountTO amount) {
        logDebug(LOG, "Do trading. Amount = {}. Wallet before {}", amount, tradeWallet.getTraderWalletTO() );

        amount.getAction().runIfBuy(() -> {
            if(amount.getAmount() > tradeWallet.getMoneyAmount()) {
                throw new BitPumpRuntimeException("Not enough money to do transaction. In wallet is " + tradeWallet.getMoneyAmount() + " and requested amount is " + amount.getAmount());
            }
            tradeWallet.addMoneyAmount(-amount.getAmount());
            tradeWallet.addDigitalCurrency( calculateDCFromMoney(amount.getAmount()));

        }).runIfSell(() -> {
            if(amount.getAmount() > tradeWallet.getDigitalCurrencyAmount()) {
                throw new BitPumpRuntimeException("Not enough digital currency to do transaction. In wallet is " + tradeWallet.getDigitalCurrencyAmount() + " and requested amount is " + amount.getAmount());
            }
            tradeWallet.addMoneyAmount(calculateMoneyFromDC(amount.getAmount()));
            tradeWallet.addDigitalCurrency( -amount.getAmount());
        });

        logDebug(LOG, "Wallet after  {}  Exchange rate = {}", tradeWallet.getTraderWalletTO(), calculateExchangeRate());
        return tradeWallet.getTraderWalletTO();
    }


    public double calculateMoneyFromDC(double digitalCurrencyAmount) {
        return digitalCurrencyAmount * calculateExchangeRate();
    }

    public double calculateDCFromMoney(double moneyAmount) {
        return moneyAmount / calculateExchangeRate();
    }

    private double calculateExchangeRate() {
        List<HistoricalTransactionTO> htList = historicalTransactions;
        if(htList.isEmpty()) {
            throw new BitPumpRuntimeException("Can't calculate exchange rate. Historical transaction list is empty!");
        }

        // Treat last historical price like current exchange rate
        return htList.get(htList.size() - 1).getTransactionPrice();
    }

    public void keepOnlyThisHistoricalTransaction(Collection<HistoricalTransactionTO> historicalTransactions) {
        this.historicalTransactions.clear();
        this.historicalTransactions.addAll( historicalTransactions);
    }

    public void keepOnlyThisHistoricalTransaction(HistoricalTransactionTO historicalTransaction) {
        this.historicalTransactions.clear();
        this.historicalTransactions.add( historicalTransaction);
    }
}
