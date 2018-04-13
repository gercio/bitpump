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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CandleTimeIndicatorActionDeciderTest {

    @Test
    public void testIt() {
        CTIADParameters parameters = new CTIADParameters();
        parameters.setGreenCounter(4);
        parameters.setPreviousCandleToCompare(2);
        CandleTimeIndicatorActionDecider decider = new CandleTimeIndicatorActionDecider(parameters);

        ExchangeDataTOBuilder builder = new ExchangeDataTOBuilder();
        List<HistoricalTransactionTO> historicalTransactions = new ArrayList<>();
        // Create four candles with price which is rising
        final long interval =  ChartTimeInterval.HOUR_4.getIntervalInMS();
        historicalTransactions.addAll(builder.build(10,15,5,12, interval));
        historicalTransactions.addAll(builder.build(11,16,7,14, interval * 2));
        historicalTransactions.addAll(builder.build(12,17,7,16, interval * 3));
        historicalTransactions.addAll(builder.build(13,18,7,18, interval * 4));
        historicalTransactions.addAll(builder.build(14,20,4,19, interval * 5));
        historicalTransactions.addAll(builder.build(14,20,10,20, interval * 6));

        ExchangeDataTO ed = new ExchangeDataTO(historicalTransactions);
        Optional<TradeAction> ta = decider.calculateTradeAction(ed);

        assertTrue(ta.isPresent());
        // As green counter is set to 4 and there is for green candles, then decider should make a sold action
        assertEquals(ta.get(), TradeAction.SELL);
    }

}