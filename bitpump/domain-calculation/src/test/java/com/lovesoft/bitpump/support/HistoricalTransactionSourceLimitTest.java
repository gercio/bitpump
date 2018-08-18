package com.lovesoft.bitpump.support;

import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
import com.lovesoft.bitpump.test.SimpleHistoricalTransactionSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HistoricalTransactionSourceLimitTest {

    @Test
    public void testIt() {
        HistoricalTransactionSourceLimit limit = new HistoricalTransactionSourceLimit(SimpleHistoricalTransactionSource.build(1.0, 2.0, 3.0, 4.0), 2);

        List<HistoricalTransactionTO> list =  limit.getHistoricalTransactions();

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(Double.valueOf(1.0), (Double)list.get(0).getTransactionPrice());
        Assertions.assertEquals(Double.valueOf(2.0), (Double)list.get(1).getTransactionPrice());
    }
}
