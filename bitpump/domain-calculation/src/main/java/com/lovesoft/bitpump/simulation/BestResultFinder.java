package com.lovesoft.bitpump.simulation;

import com.google.common.util.concurrent.AtomicDouble;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWalletStatistics;
import com.lovesoft.bitpump.commons.OptionalConsumerWithResult;
import com.lovesoft.bitpump.commons.WithLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

public class BestResultFinder implements WithLog {
    private SortedMap<Double, ParametersTO> sortedResult = Collections.synchronizedSortedMap(new TreeMap<>());
    private static final Logger LOG = LoggerFactory.getLogger(BestResultFinder.class);
    private static final double NO_RESULT = -999999991;
    private final long maxResults;
    private AtomicDouble bestResultPercentage = new AtomicDouble(NO_RESULT);
    private AtomicReference<ParametersTO> bestParameters = new AtomicReference<>();

    public BestResultFinder() {
        maxResults = 100;
    }

    public BestResultFinder(long maximumResults) {
        maxResults = maximumResults;
    }

    public synchronized void findBestResult(TradeWalletStatistics st, ParametersTO simulationParameters) {
        final double percentage = st.calculateAssetChangeInPercentage();
        if(!sortedResult.isEmpty() && sortedResult.lastKey() < percentage) {
            logDebug(LOG, "New best result  {} %  for parameters {}", percentage, simulationParameters);
            bestResultPercentage.set(percentage);
            bestParameters.set(simulationParameters);
        }
        if(sortedResult.isEmpty()) {
            bestResultPercentage.set(percentage);
            bestParameters.set(simulationParameters);
        }
        sortedResult.put(percentage, simulationParameters);
        if(sortedResult.size() > maxResults) {
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


    public String getResults() {
        return OptionalConsumerWithResult.of(getBestResult(), String.class)
                .ifPresent(p -> "--> Found best result for parameters " + p)
                .ifNotPresent(() -> "No result").getResult()
                .orElse(null);
    }

    public Optional<ParametersTO> getBestResult() {
        if(sortedResult.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(sortedResult.get(sortedResult.lastKey()));
    }
}
