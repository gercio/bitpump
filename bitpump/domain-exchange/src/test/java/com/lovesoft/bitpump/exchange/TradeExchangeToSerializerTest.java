package com.lovesoft.bitpump.exchange;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Patryk Kaluzny on 15.08.18 10:12 at Milky Way Galaxy.
 */
public class TradeExchangeToSerializerTest {

    @Test
    public void testIt() {
        // arrange
        List<TradeExchangeTO> trades = new ArrayList<>();
        TradeExchangeTO te = new TradeExchangeTO();
        te.setDate(new Date());
        te.setPrice(2018d);
        trades.add(te);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();


        // act
        TradeExchangeToSerializer.save(trades, outStream);
        List<HistoricalTransactionTO> tradesSerialized = TradeExchangeToSerializer.load(new ByteArrayInputStream(outStream.toByteArray()));

        // assert
        Assertions.assertTrue(tradesSerialized.size() == 1);
        Assertions.assertTrue(tradesSerialized.get(0).getTransactionPrice() == te.getPrice());
        Assertions.assertNotNull(tradesSerialized.get(0).getTimeInMS() > 0);
    }
}
