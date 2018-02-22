package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.simulation.SimulationDataSupport.ChartName;

import java.util.ArrayList;
import java.util.List;

public class SimulationHistoricalTransaction implements HistoricalTransactionSource {
    private ChartName chartName;
    private List<Double> historicalTransactionCache = new ArrayList<>();

    public SimulationHistoricalTransaction() {
    }

    @Override
    public synchronized List<Double> getHistoricalTransactions() {
        if(historicalTransactionCache.isEmpty()) {
            historicalTransactionCache.addAll(new SimulationDataSupport().readChart(chartName));
        }
        return historicalTransactionCache;
    }

    public void setChartName(ChartName chartName) {
        this.chartName = chartName;
        historicalTransactionCache.clear();
    }
}
