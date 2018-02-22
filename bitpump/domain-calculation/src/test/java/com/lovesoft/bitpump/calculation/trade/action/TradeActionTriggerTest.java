package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.to.TradeAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TradeActionTriggerTest {

    private TradeActionTrigger tradeActionTrigger = new TradeActionTrigger(3);

    @Test
    public void testCountBuy() {
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
        tradeActionTrigger.count(TradeAction.BUY);
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
        tradeActionTrigger.count(TradeAction.BUY);
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
        tradeActionTrigger.count(TradeAction.BUY);
        Assertions.assertTrue(tradeActionTrigger.checkBuyTriggeredAndReset());
        Assertions.assertFalse(tradeActionTrigger.checkSellTriggeredAndReset());
    }
    @Test
    public void testCountBuyAndThenSell() {
        tradeActionTrigger.count(TradeAction.BUY);
        tradeActionTrigger.count(TradeAction.BUY);
        tradeActionTrigger.count(TradeAction.SELL);
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
    }

    @Test
    public void testCountSellAndThenBuy() {
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(TradeAction.BUY);
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
    }

    @Test
    public void testCountSell() {
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(TradeAction.SELL);
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
        Assertions.assertTrue(tradeActionTrigger.checkSellTriggeredAndReset());
    }

    @Test
    public void testNull() {
        tradeActionTrigger.count(null);
        tradeActionTrigger.count(null);
        tradeActionTrigger.count(null);
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
        Assertions.assertFalse(tradeActionTrigger.checkSellTriggeredAndReset());
    }
    @Test
    public void testSellThenNull() {
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(null);
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
        Assertions.assertFalse(tradeActionTrigger.checkSellTriggeredAndReset());
    }

    @Test
    public void test3SellThenNull() {
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(null);
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
        Assertions.assertFalse(tradeActionTrigger.checkSellTriggeredAndReset());
    }

    @Test
    public void testNullThen3Sell() {
        tradeActionTrigger.count(null);
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(TradeAction.SELL);
        tradeActionTrigger.count(TradeAction.SELL);
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
        Assertions.assertTrue(tradeActionTrigger.checkSellTriggeredAndReset());
    }

    @Test
    public void testMultipleBuy() {
        tradeActionTrigger.count(TradeAction.BUY);
        tradeActionTrigger.count(TradeAction.BUY);
        tradeActionTrigger.count(TradeAction.BUY);
        Assertions.assertTrue(tradeActionTrigger.checkBuyTriggeredAndReset());
        Assertions.assertFalse(tradeActionTrigger.checkSellTriggeredAndReset());

        tradeActionTrigger.count(TradeAction.BUY);
        Assertions.assertFalse(tradeActionTrigger.checkBuyTriggeredAndReset());
        Assertions.assertFalse(tradeActionTrigger.checkSellTriggeredAndReset());
        tradeActionTrigger.count(TradeAction.BUY);
        tradeActionTrigger.count(TradeAction.BUY);
        Assertions.assertTrue(tradeActionTrigger.checkBuyTriggeredAndReset());
        Assertions.assertFalse(tradeActionTrigger.checkSellTriggeredAndReset());
    }

}
