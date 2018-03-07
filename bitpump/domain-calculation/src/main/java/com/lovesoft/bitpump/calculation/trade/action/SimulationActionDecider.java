package com.lovesoft.bitpump.calculation.trade.action;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.simulation.ParametersTO;
import com.lovesoft.bitpump.simulation.SimulationParametersTO;
import com.lovesoft.bitpump.simulation.TraderSimulationRunner;
import com.lovesoft.bitpump.support.OptionalConsumerWithResult;
import com.lovesoft.bitpump.support.WithLog;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import com.lovesoft.bitpump.to.TradeAction;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

/**
 * Use simulation calculation for historical data to find best parameters to run TrendTradeActionDecider.
 */
public class SimulationActionDecider implements TradeActionDecider, WithLog {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SimulationActionDecider.class);
    private SimulationParametersTO parameters;
    private Optional<ParametersTO> bestParameters = Optional.empty();
    private Optional<TradeActionDecider> tradeActionDecider = Optional.empty();
    private HistoricalTransactionsBuffer historicalTransactionsBuffer;

    protected SimulationActionDecider(SimulationParametersTO parameters,  HistoricalTransactionsBuffer historicalTransactionsBuffer) {
        Preconditions.checkNotNull(parameters);
        Preconditions.checkNotNull(historicalTransactionsBuffer);
        this.parameters = parameters;
        this.historicalTransactionsBuffer = historicalTransactionsBuffer;
    }

    /**
     * Run simulation for historical data and find best Parameters.
     * @param htList
     */
    @Override
    public void loadHistoricalData(List<HistoricalTransactionTO> htList) {
        Preconditions.checkNotNull(htList);
        historicalTransactionsBuffer.keep(htList);
        runSimulation();
    }

    private void runSimulation() {
        logInfo(LOG, "Run simulation with parameters {} ", parameters);
        TraderSimulationRunner runner = new TraderSimulationRunner(() ->  historicalTransactionsBuffer.getHistoricalTransactions().stream().map(ht -> ht.getTransactionPrice()).collect(Collectors.toList()) , parameters);
        runner.setSleepTime(10);
        runner.setPrintProgreess(false);
        runner.execute();
        bestParameters = runner.getParametersForBestResult();
        logInfo(LOG,"Found new best TrendTradeActionDecider parameters {} ", bestParameters.orElse(null));

        // Make some space for new data before run simulation again
        historicalTransactionsBuffer.trimToHalfCapacity();

        // Create new
        // It could be better option to just update TradeActionDecider parameters instead of creating it from scratch every time.
        tradeActionDecider = Optional.of(new TradeActionDeciderBuilder().buildTrendTradeActionDecider().withParameters(bestParameters.get()).build());
    }

    @Override
    public Optional<TradeAction> calculateTradeAction(ExchangeDataTO exchangeData) {
        historicalTransactionsBuffer.keep(exchangeData.getHistoricalTransactions());
        if(historicalTransactionsBuffer.isOverLoaded()) {
            logDebug(LOG, "It's time to run simulation. Historical transaction size " + historicalTransactionsBuffer.getHistoricalTransactions().size());
            runSimulation();
        }
        return OptionalConsumerWithResult.of(tradeActionDecider, TradeAction.class)
                                         .ifPresent(tad -> tad.calculateTradeAction(exchangeData).orElse(null))
                                         .ifNotPresent(() -> null)
                                         .getResult();
    }
}
