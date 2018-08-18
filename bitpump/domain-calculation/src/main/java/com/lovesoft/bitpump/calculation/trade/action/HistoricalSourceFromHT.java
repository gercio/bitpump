package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
import com.lovesoft.bitpump.simulation.HistoricalTransactionSource;

import java.util.ArrayList;
import java.util.List;

public class HistoricalSourceFromHT implements HistoricalTransactionSource {

    private List<HistoricalTransactionTO> htList = new ArrayList<>();

    public HistoricalSourceFromHT(List<HistoricalTransactionTO> historicalTransactionTOList) {
        historicalTransactionTOList.forEach(to -> {
            htList.add(new HistoricalTransactionTO(to));
        });
    }

    @Override
    public List<HistoricalTransactionTO> getHistoricalTransactions() {
        return htList;
    }
}
