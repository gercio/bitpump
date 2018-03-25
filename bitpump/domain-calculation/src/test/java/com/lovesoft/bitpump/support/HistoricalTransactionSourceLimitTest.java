package com.lovesoft.bitpump.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class HistoricalTransactionSourceLimitTest {

    @Test
    public void testIt() {
        HistoricalTransactionSourceLimit limit = new HistoricalTransactionSourceLimit(() -> Arrays.asList(1.0, 2.0, 3.0, 4.0), 2);

        List<Double> list =  limit.getHistoricalTransactions();

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(Double.valueOf(1.0), list.get(0));
        Assertions.assertEquals(Double.valueOf(2.0), list.get(1));
    }
}
