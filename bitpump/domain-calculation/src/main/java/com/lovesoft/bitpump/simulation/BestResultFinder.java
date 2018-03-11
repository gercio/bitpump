package com.lovesoft.bitpump.simulation;

import com.google.common.util.concurrent.AtomicDouble;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWalletStatistics;
import com.lovesoft.bitpump.support.OptionalConsumerWithResult;
import com.lovesoft.bitpump.support.WithLog;
import java.util.Collections;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BestResultFinder implements WithLog {
    private SortedMap<Double, ParametersTO> sortedResult = Collections.synchronizedSortedMap(new TreeMap<>());
    private final static Logger LOG = LoggerFactory.getLogger(BestResultFinder.class);
    private final double NO_RESULT = -999999991; // Probably this can be removed
    private final long MAX_RESULTS;
    private AtomicDouble bestResultPercentage = new AtomicDouble(NO_RESULT);
    private AtomicReference<ParametersTO> bestParameters = new AtomicReference<>();

    public BestResultFinder() {
        MAX_RESULTS = 100;
    }

    public BestResultFinder(long maximumResults) {
        MAX_RESULTS = maximumResults;
    }

    public void findBestResult(TradeWalletStatistics st, ParametersTO simulationParameters) {
        final double percentage = st.calculateAssetChangeInPercentage();
        if(!sortedResult.isEmpty() && sortedResult.lastKey() < percentage) {
            logDebug(LOG, "New best result  {} %  for parameters {}", percentage, simulationParameters);
            bestResultPercentage.set(percentage);
            bestParameters.set(simulationParameters);
        }
        sortedResult.put(percentage, simulationParameters);
        if(sortedResult.size() > MAX_RESULTS) {
            // Just remove most old result from map - to not kill memory!
            sortedResult.remove(sortedResult.firstKey());
        }
    }

    public Optional<Double> getActualBestResult() {
        if(NO_RESULT == bestResultPercentage.get()) {
            return Optional.empty();
        }
        return Optional.of(bestResultPercentage.get());
    }

    public Optional<ParametersTO> getActualBestResultParameters() {
        return Optional.ofNullable(bestParameters.get());
    }

    public void printResultsToLog() {
        logInfo(LOG, getResults());
    }

    public String getResults() {
//        if(sortedResult.isEmpty()) {
//            return "No results found!";
//        } else {
//            StringBuffer sb = new StringBuffer();
//            double bestResult = sortedResult.lastKey();
//            sortedResult.subMap(bestResult - 5, bestResult + 0.1).forEach( (p, param) -> sb.append("--> Found best result " + p + " %  for parameters " + param + " \n"));
//            return sb.toString();
//        }
        return  OptionalConsumerWithResult.of(getBestResult(), String.class).ifPresent(p -> "--> Found best result for parameters " + p).ifNotPresent( () -> "No result").getResult().get();
    }

    public Optional<ParametersTO> getBestResult() {
        if(sortedResult.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(sortedResult.get(sortedResult.lastKey()));
    }
}
