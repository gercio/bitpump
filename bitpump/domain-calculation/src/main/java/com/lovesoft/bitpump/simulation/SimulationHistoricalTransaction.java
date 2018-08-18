package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
import com.lovesoft.bitpump.simulation.SimulationDataSupport.ChartName;
import com.lovesoft.bitpump.support.AverageWeightValueCalculator;

import java.util.ArrayList;
import java.util.List;

public class SimulationHistoricalTransaction implements HistoricalTransactionSource {
    private ChartName chartName;
    private List<HistoricalTransactionTO> historicalTransactionCache = new ArrayList<>();
    private int movingAverageNumberOfElements;

    public SimulationHistoricalTransaction(int movingAverageNumberOfElements) {
        this.movingAverageNumberOfElements = movingAverageNumberOfElements;
    }

    @Override
    public synchronized List<HistoricalTransactionTO> getHistoricalTransactions() {
        if(historicalTransactionCache.isEmpty()) {
            historicalTransactionCache.addAll(new SimulationDataSupport().readChart(chartName));
            calculateMVA();
        }
        return historicalTransactionCache;
    }

    private void calculateMVA() {
        AverageWeightValueCalculator calculator = new AverageWeightValueCalculator(movingAverageNumberOfElements);
        historicalTransactionCache.forEach(ht -> {
            calculator.addValue(ht.getTransactionPrice(), 1.0);
            ht.setTransactionPriceMVA(calculator.calculateAverageValue());
        });
    }

    public void setChartName(ChartName chartName) {
        this.chartName = chartName;
        historicalTransactionCache.clear();
    }
}
