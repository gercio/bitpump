package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.to.TradeWalletTO;

import java.util.function.Supplier;

public interface WalletToSupplierObserver {
    void observerNewWalletToSupplier(Supplier<TradeWalletTO> walletToSupplier);
}
