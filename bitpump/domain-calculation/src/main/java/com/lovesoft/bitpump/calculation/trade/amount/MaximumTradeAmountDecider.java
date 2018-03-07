package com.lovesoft.bitpump.calculation.trade.amount;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.to.TradeAction;
import com.lovesoft.bitpump.to.TradeAmountTO;
import com.lovesoft.bitpump.to.TradeWalletTO;
import com.lovesoft.bitpump.support.BitPumpRuntimeException;
import com.lovesoft.bitpump.support.WithLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Always sells or buy for maximum possible amounts.
 */
public class MaximumTradeAmountDecider implements TradeAmountDecider, WithLog {
    private static Logger LOG = LoggerFactory.getLogger(MaximumTradeAmountDecider.class);
    private TradeWalletTO tradeWallet;

    protected MaximumTradeAmountDecider() {

    }

    @Override
    public void setTradeWallet(TradeWalletTO tradeWallet) {
        this.tradeWallet = tradeWallet;
    }

    @Override
    public Optional<TradeAmountTO> calculateAmount(TradeAction tradeAction) {
        Preconditions.checkNotNull(tradeWallet);
        Preconditions.checkNotNull(tradeAction);

        if (tradeWallet.isEmpty()) {
            logDebug(LOG, "There is no money in wallet. No trade will be made.");
            return Optional.empty();
        }

        switch (tradeAction) {
            case BUY:
                logDebug(LOG, "Let's buy some digital currency.");
                return calculateTradeAmount(tradeAction, tradeWallet.haveNoMoney(), tradeWallet.getMoneyAmount());
            case SELL:
                logDebug(LOG, "Let's sell some digital currency.");
                return calculateTradeAmount(tradeAction, tradeWallet.haveNoDigitalCurrency(), tradeWallet.getDigitalCurrencyAmount());
            default:
                throw new BitPumpRuntimeException("Unknown TradeAction " + tradeAction);
        }
    }

    private Optional<TradeAmountTO> calculateTradeAmount(TradeAction tradeAction, boolean noAssets, double moneyAmount) {
        if (noAssets) {
            return Optional.empty();
        }
        return Optional.of(new TradeAmountTO(tradeAction, moneyAmount));
    }
}
