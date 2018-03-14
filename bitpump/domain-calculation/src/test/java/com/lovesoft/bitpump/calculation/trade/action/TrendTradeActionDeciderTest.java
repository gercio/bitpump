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

        TrendTradeActionDeciderParameters par = new TrendTradeActionDeciderParameters();
        par.setPercentageDownSell(5);
        par.setPercentageUpBuy(5);
        trendTradeActionDecider = new TradeActionDeciderBuilder().build(par);
    }

    @Test
    void shouldBuyWhenPriceIsGoingMoreThan5PercentUp() {
        // Price is going up more then 5% -> buy it!
        exchangeData = new ExchangeDataTOBuilder().build(95d, 96d, 97d, 98d, 99d, 100d);
        TradeAction ta = trendTradeActionDecider.calculateTradeAction(exchangeData).get();

        assertNotNull(ta);
        assertEquals(TradeAction.BUY, ta);
    }

    @Test
    void shouldDoNothingWhenThereIsNoHistoricalData() {
        exchangeData = new ExchangeDataTOBuilder().build();
        assertFalse(trendTradeActionDecider.calculateTradeAction(exchangeData).isPresent());
    }

    @Test
    void shouldNotBuyWhenPriceIsGoingLessThan5PercentUp() {
        exchangeData = new ExchangeDataTOBuilder().build(96d, 96d, 97d, 98d, 99d, 100d);
        assertFalse(trendTradeActionDecider.calculateTradeAction(exchangeData).isPresent());
    }

    @Test
    void shouldSellWhenPriceIsGoingDownMoreThan5PercentUp() {
        exchangeData = new ExchangeDataTOBuilder().build(100d, 99d, 98d, 97d, 96d, 95d);
        TradeAction ta = trendTradeActionDecider.calculateTradeAction(exchangeData).get();

        assertNotNull(ta);
        assertEquals(TradeAction.SELL, ta);
    }
}
