package com.lovesoft.bitpump.calculation.trade;

import com.lovesoft.bitpump.ExchangeDataTOBuilder;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;
import com.lovesoft.bitpump.exchange.LocalExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TraderTest {
    private Trader trader;
    private LocalExchange exchange;
    private TradeWallet tradeWallet;

    @BeforeEach
    public void beforeEach() {
        createTrader(null);
    }

    private void createTrader(Double maximumLoosePercentage) {
        TraderFactory traderFactory = new TraderFactory();
        Optional.ofNullable(maximumLoosePercentage).ifPresent( p -> traderFactory.withMaximumLoosePercentage(p));
        trader = traderFactory.createDefaultTrader();
        exchange = (LocalExchange) traderFactory.getExchange();
        tradeWallet = trader.getActualWallet();
    }

    @Test
    public void testTradeWithBuy() {
        tradeWallet.setMoneyAmount(100);
        exchange.addHistoricalTransactions(new ExchangeDataTOBuilder().createHistoricalTransactions(10d, 20d, 30d, 100d));

        // test
        trader.doTrades();

        // We should trade all money for digital currency
        assertEquals(0, tradeWallet.getMoneyAmount());
        // One DC cost 100 money
        assertEquals(1, tradeWallet.getDigitalCurrencyAmount());
    }

    @Test
    public void testTradeWithSell() {
        tradeWallet.setMoneyAmount(100);
        tradeWallet.setDigitalCurrencyAmount(2);
        exchange.addHistoricalTransactions(new ExchangeDataTOBuilder().createHistoricalTransactions(100d, 90d, 70d, 60d));

        // test
        trader.doTrades();

        // We should trade all digital currency for money
        // 220 = 100 + 2 * 60
        assertEquals(220, tradeWallet.getMoneyAmount());
        assertEquals(0, tradeWallet.getDigitalCurrencyAmount());
    }

    @Test
    public void shouldNotTradeBecauseNoMoney() {
        tradeWallet.setMoneyAmount(0); // No money = no trade

        // test
        trader.doTrades();
        assertEquals(0, tradeWallet.getMoneyAmount());
        assertEquals(0, tradeWallet.getDigitalCurrencyAmount());
    }

    @Test
    public void testTradeWithStopLoos() {
        createTrader(50.0);
        ExchangeDataTOBuilder builder = new ExchangeDataTOBuilder();
        tradeWallet.setMoneyAmount(100);
        exchange.addHistoricalTransactions(builder.createHistoricalTransactions(10d, 20d, 30d, 100d));
        // Buy
        trader.doTrades();

        // Now simulate crash!
        exchange.addHistoricalTransactions(builder.createHistoricalTransactions(20d, 10d, 9d, 1d));
        trader.doTrades();

        // Everything should be sold out!
        assertEquals(0, tradeWallet.getDigitalCurrencyAmount());
    }
}
