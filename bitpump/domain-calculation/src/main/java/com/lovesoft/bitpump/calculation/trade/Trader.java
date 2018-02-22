package com.lovesoft.bitpump.calculation.trade;

import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;

public interface Trader {
    void doTrades();
    TradeWallet getActualWallet();
}
