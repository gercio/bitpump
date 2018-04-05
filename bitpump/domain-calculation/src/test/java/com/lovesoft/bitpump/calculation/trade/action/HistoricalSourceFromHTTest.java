package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoricalSourceFromHTTest {

    @Test
    public void testIt() {
        List<HistoricalTransactionTO> list = new ArrayList<>();

        list.add(new HistoricalTransactionTO(1,10, 100));
        list.add(new HistoricalTransactionTO(2,20, 200));
        list.add(new HistoricalTransactionTO(3,30, 300));
        HistoricalSourceFromHT sut = new HistoricalSourceFromHT(list);

        Assertions.assertEquals(Arrays.asList(10.0, 20.0, 30.0), sut.getHistoricalTransactions());
        Assertions.assertEquals(Arrays.asList(100.0, 200.0, 300.0), sut.getHistoricalTransactionsMVA());
    }
}
