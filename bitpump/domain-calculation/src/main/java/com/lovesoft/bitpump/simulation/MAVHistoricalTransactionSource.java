package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.support.AverageWeightValueCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Moving average historical transaction source
 */
public class MAVHistoricalTransactionSource implements HistoricalTransactionSource {
    private AverageWeightValueCalculator calculator;
    private HistoricalTransactionSource historicalTransactionSource;

    public MAVHistoricalTransactionSource(int numberOfItems, HistoricalTransactionSource historicalTransactionSource) {
        this.calculator = new AverageWeightValueCalculator(numberOfItems);
        this.historicalTransactionSource = historicalTransactionSource;
    }

    @Override
    public List<Double> getHistoricalTransactions() {
        List<Double> outList = new ArrayList<>();
        historicalTransactionSource.getHistoricalTransactions().forEach(ht -> {
            calculator.addValue(ht, 1.0);
            outList.add(calculator.calculateAverageValue());
        });
        return outList;
    }
}
