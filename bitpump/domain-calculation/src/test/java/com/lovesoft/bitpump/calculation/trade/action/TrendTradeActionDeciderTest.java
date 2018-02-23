package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.ExchangeDataTOBuilder;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrendTradeActionDeciderTest {

    private TradeActionDecider trendTradeActionDecider;
    private ExchangeDataTO exchangeData;

    @BeforeEach
    void beforeEach() {
        trendTradeActionDecider = new TradeActionDeciderBuilder().withPercentage(5).withMaximumHistoricalTransactions(10).build();
    }

    @Test
    void shouldBuyWhenPriceIsGoingMoreThan5PercentUp() {
        // Price is going up more then 5% -> buy it!
        exchangeData = new ExchangeDataTOBuilder().build(95d, 96d, 97d, 98d, 99d, 100d);
        trendTradeActionDecider.loadExchangeData(exchangeData);

        TradeAction ta = trendTradeActionDecider.calculateTradeAction().get();

        assertNotNull(ta);
        assertEquals(TradeAction.BUY, ta);
    }

    @Test
    void shouldDoNothingWhenThereIsNoHistoricalData() {
        exchangeData = new ExchangeDataTOBuilder().build();
        trendTradeActionDecider.loadExchangeData(exchangeData);

        assertFalse(trendTradeActionDecider.calculateTradeAction().isPresent());
    }

    @Test
    void shouldNotBuyWhenPriceIsGoingLessThan5PercentUp() {
        exchangeData = new ExchangeDataTOBuilder().build(96d, 96d, 97d, 98d, 99d, 100d);
        trendTradeActionDecider.loadExchangeData(exchangeData);

        assertFalse(trendTradeActionDecider.calculateTradeAction().isPresent());
    }

    @Test
    void shouldSellWhenPriceIsGoingDownMoreThan5PercentUp() {
        exchangeData = new ExchangeDataTOBuilder().build(100d, 99d, 98d, 97d, 96d, 95d);
        trendTradeActionDecider.loadExchangeData(exchangeData);

        TradeAction ta = trendTradeActionDecider.calculateTradeAction().get();

        assertNotNull(ta);
        assertEquals(TradeAction.SELL, ta);
    }

//    @Test
//    void shouldNotSellForSmallerExchangeRateTheBuyWasMade() {
//
//        ExchangeDataTOBuilder builder = new ExchangeDataTOBuilder();
//        TraderFactory tf = new TraderFactory();
//        tf.createDefaultTrader();
//        LocalSimulationExchange exchange = (LocalSimulationExchange) tf.getExchange();
//        trendTradeActionDecider = tf.getTradeActionDecider();
//
//        exchange.addHistoricalTransactions(builder.createHistoricalTransactions(95d, 96d, 97d, 98d, 99d, 100d));
//        tf.getTrader().doTrades();
//        assertEquals(TradeAction.BUY, trendTradeActionDecider.calculateTradeAction().get());
//
//        // Now price goes down - trader should not sell
//        exchange.addHistoricalTransactions(builder.createHistoricalTransactions(100d, 99d, 98d, 97d, 96d, 95d, 94d));
//        tf.getTrader().doTrades();
//        Optional<TradeAction> ta = trendTradeActionDecider.calculateTradeAction();
//        assertFalse(ta.isPresent(), "Trader should not sell now!");
//    }
}
