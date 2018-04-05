package com.lovesoft.bitpump.support;

import com.lovesoft.bitpump.test.SimpleHistoricalTransactionSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HistoricalTransactionSourceLimitTest {

    @Test
    public void testIt() {
        HistoricalTransactionSourceLimit limit = new HistoricalTransactionSourceLimit(SimpleHistoricalTransactionSource.build(1.0, 2.0, 3.0, 4.0), 2);

        List<Double> list =  limit.getHistoricalTransactions();

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(Double.valueOf(1.0), list.get(0));
        Assertions.assertEquals(Double.valueOf(2.0), list.get(1));
    }
}
