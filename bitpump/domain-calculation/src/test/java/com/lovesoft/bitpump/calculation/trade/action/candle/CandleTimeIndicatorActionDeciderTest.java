package com.lovesoft.bitpump.calculation.trade.action.candle;

import com.lovesoft.bitpump.ExchangeDataTOBuilder;
import com.lovesoft.bitpump.calculation.candle.ChartTimeInterval;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import com.lovesoft.bitpump.to.TradeAction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CandleTimeIndicatorActionDeciderTest {

    @Test
    public void testSellAfterGreenTrigger() {
        CandleTimeIndicatorActionDecider decider = createDecider(4, 4, 2);

        // Create four candles with price which is rising
        List<HistoricalTransactionTO> historicalTransactions = new ArrayList<>();
        final long interval =  ChartTimeInterval.HOUR_4.getIntervalInMS();
        historicalTransactions.addAll(buildCandle(12, interval));
        historicalTransactions.addAll(buildCandle(14, interval * 2));
        historicalTransactions.addAll(buildCandle(16, interval * 3));
        historicalTransactions.addAll(buildCandle(18, interval * 4));
        historicalTransactions.addAll(buildCandle(21, interval * 5));
        historicalTransactions.addAll(buildCandle(20, interval * 6));

        Optional<TradeAction> ta = calulateTradeAction(decider, historicalTransactions);

        assertTrue(ta.isPresent());
        // As green counter is set to 4 and there is for green candles, then decider should make a sold action
        assertEquals(ta.get(), TradeAction.SELL);
    }

    private List<HistoricalTransactionTO> buildCandle(double close, long interval) {
        ExchangeDataTOBuilder builder = new ExchangeDataTOBuilder();
        return builder.build(10,15,5,close, interval);
    }

    @Test
    public void testBuyAfterRedTrigger() {
        CandleTimeIndicatorActionDecider decider = createDecider(4, 4, 2);

        // Create four candles with price which is decreasing
        List<HistoricalTransactionTO> historicalTransactions = new ArrayList<>();
        final long interval =  ChartTimeInterval.HOUR_4.getIntervalInMS();
        historicalTransactions.addAll(buildCandle(12, interval));
        historicalTransactions.addAll(buildCandle(10, interval * 2));
        historicalTransactions.addAll(buildCandle(5, interval * 3));
        historicalTransactions.addAll(buildCandle(6, interval * 4));
        historicalTransactions.addAll(buildCandle(1, interval * 5));
        historicalTransactions.addAll(buildCandle(2, interval * 6));

        Optional<TradeAction> ta = calulateTradeAction(decider, historicalTransactions);

        assertTrue(ta.isPresent());
        // As green counter is set to 4 and there is for green candles, then decider should make a sold action
        assertEquals(ta.get(), TradeAction.BUY);
    }

    private Optional<TradeAction> calulateTradeAction(CandleTimeIndicatorActionDecider decider, List<HistoricalTransactionTO> historicalTransactions) {
        ExchangeDataTO ed = new ExchangeDataTO(historicalTransactions);
        return decider.calculateTradeAction(ed);
    }

    @Test
    public void testRedAfterGreen() {
        CandleTimeIndicatorActionDecider decider = createDecider(4, 4, 1);

        List<HistoricalTransactionTO> historicalTransactions = new ArrayList<>();
        final long interval =  ChartTimeInterval.HOUR_4.getIntervalInMS();
        historicalTransactions.addAll(buildCandle(12, interval));
        historicalTransactions.addAll(buildCandle(13, interval * 2));
        historicalTransactions.addAll(buildCandle(14, interval * 3));
        historicalTransactions.addAll(buildCandle(5, interval * 4));
        historicalTransactions.addAll(buildCandle(3, interval * 5));
        historicalTransactions.addAll(buildCandle(2, interval * 6));
        historicalTransactions.addAll(buildCandle(1, interval * 7));

        Optional<TradeAction> ta = calulateTradeAction(decider, historicalTransactions);

        assertTrue(ta.isPresent());
        // As green counter is set to 4 and there is for green candles, then decider should make a sold action
        assertEquals(ta.get(), TradeAction.BUY);
    }

    @Test
    public void shouldNotSellAfterSell() {
        CandleTimeIndicatorActionDecider decider = createDecider(1, 2, 1);

        List<HistoricalTransactionTO> historicalTransactions = new ArrayList<>();
        final long interval =  ChartTimeInterval.HOUR_4.getIntervalInMS();
        historicalTransactions.addAll(buildCandle(12, interval));
        historicalTransactions.addAll(buildCandle(13, interval * 2));
        historicalTransactions.addAll(buildCandle(14, interval * 3));
        historicalTransactions.addAll(buildCandle(5, interval * 4));
        historicalTransactions.addAll(buildCandle(3, interval * 5));
        historicalTransactions.addAll(buildCandle(2, interval * 6));

        Optional<TradeAction> ta = calulateTradeAction(decider, historicalTransactions);

        assertFalse(ta.isPresent());
    }

    @Test
    public void shouldKeepStatus() {
        CandleTimeIndicatorActionDecider decider = createDecider(2, 2, 1);

        List<HistoricalTransactionTO> historicalTransactions = new ArrayList<>();
        final long interval =  ChartTimeInterval.HOUR_4.getIntervalInMS();
        historicalTransactions.addAll(buildCandle(12, interval));
        calulateTradeAction(decider, historicalTransactions);

        historicalTransactions.clear();
        historicalTransactions.addAll(buildCandle(13, interval * 2));
        calulateTradeAction(decider, historicalTransactions);

        historicalTransactions.clear();
        historicalTransactions.addAll(buildCandle(14, interval * 2)); // Green is triggered so its time to sell!
        Optional<TradeAction> ta = calulateTradeAction(decider, historicalTransactions);

        assertTrue(ta.isPresent());
        assertEquals(ta.get(), TradeAction.SELL);
    }

    private CandleTimeIndicatorActionDecider createDecider(int counterForGreen, int counterForRed, int previousCandleToCompare) {
        CTIADParameters parameters = new CTIADParameters();
        parameters.setGreenCounter(counterForGreen);
        parameters.setRedCounter(counterForRed);
        parameters.setPreviousCandleToCompare(previousCandleToCompare);
        parameters.setChartTimeInterval(ChartTimeInterval.HOUR_4);
        return new CandleTimeIndicatorActionDecider(parameters);
    }

}