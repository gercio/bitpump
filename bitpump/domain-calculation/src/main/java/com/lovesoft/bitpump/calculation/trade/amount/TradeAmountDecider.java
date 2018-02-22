package com.lovesoft.bitpump.calculation.trade.amount;

import com.lovesoft.bitpump.to.TradeAction;
import com.lovesoft.bitpump.to.TradeAmountTO;
import com.lovesoft.bitpump.to.TradeWalletTO;

import java.util.Optional;

public interface TradeAmountDecider {
    void setTradeWallet(TradeWalletTO tradeWallet);
    Optional<TradeAmountTO> calculateAmount(TradeAction tradeAction);
}
