package com.lovesoft.bitpump.calculation.trade.amount;

import com.lovesoft.bitpump.to.TradeAction;
import com.lovesoft.bitpump.to.TradeAmountTO;
import com.lovesoft.bitpump.to.TradeWalletTO;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaximumTradeAmountDeciderTest {

    private TradeAmountDecider maximumTradeAmountDecider;

    @BeforeEach
    void beforeEach() {
        maximumTradeAmountDecider = createDecider(100, 10);
    }

    private TradeAmountDecider createDecider(int moneyAmount, double digitalCurrencyAmount) {
        TradeAmountDecider tradeAmountDecider = new TradeAmountDeciderBuilder().build();
        tradeAmountDecider.setTradeWallet( new TradeWalletTO(moneyAmount, digitalCurrencyAmount));
        return tradeAmountDecider;
    }

    @Test
    public void shouldBuyMax() {
        TradeAmountTO tradeAmountTO = maximumTradeAmountDecider.calculateAmount(TradeAction.BUY).get();
        assertNotNull(tradeAmountTO);
        assertEquals(TradeAction.BUY, tradeAmountTO.getAction());
        assertEquals(100, tradeAmountTO.getAmount()); // Buy digital currency for all money
    }

    @Test
    public void shouldSellMax() {
        TradeAmountTO tradeAmountTO = maximumTradeAmountDecider.calculateAmount(TradeAction.SELL).get();
        assertNotNull(tradeAmountTO);
        assertEquals(TradeAction.SELL, tradeAmountTO.getAction());
        assertEquals(10, tradeAmountTO.getAmount()); // Sell digital currency
    }

    @Test
    public void shouldNotBuyWhenThereIsNoMoney() {
        maximumTradeAmountDecider = createDecider(0, 10);
        assertFalse( () -> maximumTradeAmountDecider.calculateAmount(TradeAction.BUY).isPresent(),  "When there is no money, there should not be trade amount.");
    }

    @Test
    public void shouldNotSellWhenThereIsNoDigitalCurrency() {
        maximumTradeAmountDecider = createDecider(1000, 0);
        assertFalse( () -> maximumTradeAmountDecider.calculateAmount(TradeAction.SELL).isPresent(),  "When there is no digitalCurrency, there should not be trade amount.");
    }

    @Test
    public void shouldNotBeAnyAmountIfWalletIsEmpty2() {
        maximumTradeAmountDecider = createDecider(10, 0);
        assertFalse( () -> maximumTradeAmountDecider.calculateAmount(TradeAction.SELL).isPresent(),  "When wallet is empty, there should not be trade amount.");
    }

    @Test
    public void shouldNotBeAnyAmountIfWalletIsEmpty3() {
        maximumTradeAmountDecider = createDecider(0, 10);
        assertFalse( () -> maximumTradeAmountDecider.calculateAmount(TradeAction.BUY).isPresent(),  "When wallet is empty, there should not be trade amount.");
    }

//    @Test
//    public void shouldNotBuyTwice() {
//        TradeAmountTO tradeAmountTO = maximumTradeAmountDecider.calculateAmount(TradeAction.BUY).get();
//        assertEquals(TradeAction.BUY, tradeAmountTO.getAction());
//        // Put some money into wallet
//        maximumTradeAmountDecider.setTradeWallet(new TradeWalletTO(200, 1));
//        // Try to buy again
//        assertFalse( () ->  maximumTradeAmountDecider.calculateAmount(TradeAction.BUY).isPresent(),  "It's not ok to buy twice, first DC need to be sold!");
//    }
//
//    @Test
//    public void shouldSelTwice() {
//        TradeAmountTO tradeAmountTO = maximumTradeAmountDecider.calculateAmount(TradeAction.SELL).get();
//        assertEquals(TradeAction.SELL, tradeAmountTO.getAction());
//        maximumTradeAmountDecider.setTradeWallet(new TradeWalletTO(200, 1));
//        // Try to sell again
//        assertFalse( () ->  maximumTradeAmountDecider.calculateAmount(TradeAction.SELL).isPresent(),  "It's not ok to sell twice, first DC need to be buy!");
//    }
}
