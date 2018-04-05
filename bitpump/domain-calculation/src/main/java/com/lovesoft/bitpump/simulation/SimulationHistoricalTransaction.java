package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.simulation.SimulationDataSupport.ChartName;
import com.lovesoft.bitpump.support.AverageWeightValueCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimulationHistoricalTransaction implements HistoricalTransactionSource {
    private ChartName chartName;
    private List<Double> historicalTransactionCache = new ArrayList<>();
    private List<Double> historicalTransactionCacheMVA = new ArrayList<>();
    private Optional<Integer> movingAverageNumberOfElements = Optional.empty();

    public SimulationHistoricalTransaction() {
    }

    @Override
    public synchronized List<Double> getHistoricalTransactions() {
        if(historicalTransactionCache.isEmpty()) {
            historicalTransactionCache.addAll(new SimulationDataSupport().readChart(chartName));
            calculateMVA();
        }
        return historicalTransactionCache;
    }

    private void calculateMVA() {
        if(movingAverageNumberOfElements.isPresent()) {
            historicalTransactionCacheMVA.clear();
            AverageWeightValueCalculator calculator = new AverageWeightValueCalculator(movingAverageNumberOfElements.get());
            historicalTransactionCache.forEach(ht -> {
                calculator.addValue(ht, 1.0);
                historicalTransactionCacheMVA.add(calculator.calculateAverageValue());
            });
        }
    }

    @Override
    public List<Double> getHistoricalTransactionsMVA() {
        return historicalTransactionCacheMVA;
    }

    public void setChartName(ChartName chartName) {
        this.chartName = chartName;
        historicalTransactionCache.clear();
    }
}
