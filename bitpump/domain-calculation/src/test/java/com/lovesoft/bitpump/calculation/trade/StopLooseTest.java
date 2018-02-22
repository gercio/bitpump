package com.lovesoft.bitpump.calculation.trade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StopLooseTest {

    @Test
    public void testStopLooseNegative() {
        StopLoose sl =  new StopLoose(10.0);
        TradeData tradeData  = new TradeData();
        sl.setTradeData(tradeData);
        tradeData.setLastBuyExchangeRate(10.0);
        tradeData.setSellExchangeRate(10.0);
        Assertions.assertFalse(sl.stopLoose());
    }

    @Test
    public void testStopLoosePositive() {
        StopLoose sl =  new StopLoose(10.0);
        TradeData tradeData  = new TradeData();
        sl.setTradeData(tradeData);
        tradeData.setLastBuyExchangeRate(10.0);
        tradeData.setSellExchangeRate(8.99);
        Assertions.assertTrue(sl.stopLoose());
    }
}
