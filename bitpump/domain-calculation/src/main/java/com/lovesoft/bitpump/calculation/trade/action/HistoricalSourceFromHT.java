package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.simulation.HistoricalTransactionSource;
import com.lovesoft.bitpump.support.BitPumpRuntimeException;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;

import java.util.ArrayList;
import java.util.List;

public class HistoricalSourceFromHT implements HistoricalTransactionSource {

    private List<Double> htList = new ArrayList<>();
    private List<Double> htMVAList = new ArrayList<>();

    public HistoricalSourceFromHT(List<HistoricalTransactionTO> historicalTransactionTOList) {
        historicalTransactionTOList.forEach(to -> {
            htList.add(to.getTransactionPrice());
            to.getTransactionPriceMVA().ifPresent( price -> htMVAList.add(price));
            to.getTransactionPriceMVA().orElseThrow(() -> new BitPumpRuntimeException("No price MVA for history. What to do?"));
        });
    }

    @Override
    public List<Double> getHistoricalTransactions() {
        return htList;
    }

    @Override
    public List<Double> getHistoricalTransactionsMVA() {
        return htMVAList;
    }
}
