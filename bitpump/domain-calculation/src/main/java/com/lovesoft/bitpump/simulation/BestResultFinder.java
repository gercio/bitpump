package com.lovesoft.bitpump.simulation;

import com.google.common.util.concurrent.AtomicDouble;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWalletStatistics;
import com.lovesoft.bitpump.support.WithLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

public class BestResultFinder implements WithLog {
    private SortedMap<Double, ParametersTO> sortedResult = Collections.synchronizedSortedMap(new TreeMap<>());
    private final static Logger LOG = LoggerFactory.getLogger(BestResultFinder.class);
    private final double NO_RESULT = -999999991;
    private final long MAX_RESULTS;
    private AtomicDouble bestResultPercentage = new AtomicDouble(NO_RESULT);

    public BestResultFinder() {
        MAX_RESULTS = 100;
    }

    public BestResultFinder(long maximumResults) {
        MAX_RESULTS = maximumResults;
    }

    public void findBestResult(TradeWalletStatistics st, ParametersTO simulationParameters) {
        final double percentage = st.calculateAssetChangeInPercentage();
        if(!sortedResult.isEmpty() && sortedResult.lastKey() < percentage) {
            logInfo(LOG, "New best result  {} %  for parameters {}", percentage, simulationParameters);
            bestResultPercentage.set(percentage);
        }
        sortedResult.put(percentage, simulationParameters);
        if(sortedResult.size() > MAX_RESULTS) {
            // Just remove most week result from map - to not kill memory!
            sortedResult.remove(sortedResult.firstKey());
        }
    }

    public Optional<Double> getActualBestResult() {
        if(NO_RESULT == bestResultPercentage.get()) {
            return Optional.empty();
        }
        return Optional.of(bestResultPercentage.get());
    }

    public void printResultsToLog() {
        logInfo(LOG, getResults());
    }

    public String getResults() {
        if(sortedResult.isEmpty()) {
            return "No results found!";
        } else {
            StringBuffer sb = new StringBuffer();
            double bestResult = sortedResult.lastKey();
            sortedResult.subMap(bestResult - 5, bestResult + 0.1).forEach( (p, param) -> sb.append("--> Found best result " + p + " %  for parameters " + param + " \n"));
            return sb.toString();
        }
    }
}
