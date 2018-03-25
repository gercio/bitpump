package com.lovesoft.bitpump.simulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class MAVHistoricalTransactionSourceTest {

    @Test
    public void testIt() {
        MAVHistoricalTransactionSource source = new MAVHistoricalTransactionSource(2, () -> Arrays.asList(1.0, 2.0, 3.0, 9.0));
        List<Double> hist = source.getHistoricalTransactions();

        Assertions.assertEquals(4, hist.size());

        Assertions.assertEquals(Double.valueOf(1.0), hist.get(0));
        Assertions.assertEquals(Double.valueOf(1.5), hist.get(1));
        Assertions.assertEquals(Double.valueOf(2.5), hist.get(2));
        Assertions.assertEquals(Double.valueOf(6.0), hist.get(3));
    }
}
