package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.ExchangeDataTOBuilder;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created 25.02.2018 12:44.
 */
public class HistoricalTransactionsBufferTest {

    private List<HistoricalTransactionTO> historicalTransactionList;

    @BeforeEach
    void beforeEach() {
        historicalTransactionList = null;
    }
    @Test
    public void testIsOverLoadedTrue() {
        HistoricalTransactionsBuffer buffer = create(3);
        Assertions.assertTrue(buffer.isOverLoaded());
    }

    @Test
    public void testIsOverLoadedFalse() {
        HistoricalTransactionsBuffer buffer = create(100);
        Assertions.assertFalse(buffer.isOverLoaded());
    }

    @Test
    public void testGetHistoricalTransactions() {
        HistoricalTransactionsBuffer buffer = create(100);
        List<HistoricalTransactionTO> htList = buffer.getHistoricalTransactions();
        Assertions.assertEquals(historicalTransactionList, htList);
    }

    @Test
    public void testTrim() {
        HistoricalTransactionsBuffer buffer = create(4);
        buffer.trimToHalfCapacity();
        List<HistoricalTransactionTO> htList = buffer.getHistoricalTransactions();
        Assertions.assertEquals(historicalTransactionList.subList(3,5), htList);
    }

    @Test
    public void testTrimToPercentOfCapacity() {
        HistoricalTransactionsBuffer buffer = create(5);
        buffer.trimToPercentOfCapacity(80);
        List<HistoricalTransactionTO> htList = buffer.getHistoricalTransactions();
        Assertions.assertEquals(historicalTransactionList.subList(1,5), htList);
    }

    private HistoricalTransactionsBuffer create(int capacity) {
        HistoricalTransactionsBuffer buffer = new HistoricalTransactionsBuffer(capacity);
        ExchangeDataTOBuilder builder = new ExchangeDataTOBuilder();
        historicalTransactionList = builder.createHistoricalTransactions(10d, 20d, 30d, 40d, 50d);
        buffer.keep(historicalTransactionList);
        return buffer;
    }
}
