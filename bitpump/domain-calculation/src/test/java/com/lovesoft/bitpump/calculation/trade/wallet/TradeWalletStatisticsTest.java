package com.lovesoft.bitpump.calculation.trade.wallet;

import com.lovesoft.bitpump.calculation.trade.TraderFactory;
import com.lovesoft.bitpump.to.TradeWalletTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeWalletStatisticsTest {

    private TradeWalletStatistics tradeWalletStatistics;

    @BeforeEach
    public void beforeEach() {
        TraderFactory tf = new TraderFactory();
        tf.createDefaultTrader();
        tradeWalletStatistics = new TradeWalletStatistics(tf.getExchange());
    }

    @Test
    public void testCalculateAssetChangeInPercentageForMoney() {
        start(10, 0);
        tradeWalletStatistics.updateWalletTO(new TradeWalletTO(20, 0));
        assertEquals(100, tradeWalletStatistics.calculateAssetChangeInPercentage());
    }

    @Test
    public void testCalculateAssetChangeInPercentageForDC() {
        start(0, 1);
        tradeWalletStatistics.updateWalletTO(new TradeWalletTO(0, 5));
        assertEquals(400, tradeWalletStatistics.calculateAssetChangeInPercentage());
    }

    @Test
    public void testCalculateAverageChangeInPercentageIncludingDigitalCurrency() {
        start(100, 0);
        tradeWalletStatistics.updateWalletTO(new TradeWalletTO(100, 1));
        assertEquals(100, tradeWalletStatistics.calculateAssetChangeInPercentage());
    }

    private void start(double moneyAmount, double dcAmount) {
        tradeWalletStatistics.start(new TradeWalletTO(moneyAmount, dcAmount));
    }

}