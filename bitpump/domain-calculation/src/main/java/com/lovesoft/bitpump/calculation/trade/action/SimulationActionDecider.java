package com.lovesoft.bitpump.calculation.trade.action;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.simulation.SimulationParametersTO;
import com.lovesoft.bitpump.simulation.TraderSimulationRunner;
import com.lovesoft.bitpump.support.OptionalConsumerWithResult;
import com.lovesoft.bitpump.support.WithLog;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import com.lovesoft.bitpump.to.TradeAction;
import com.lovesoft.bitpump.to.TradeWalletTO;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Use simulation calculation for historical data to find best parameters to run TrendTradeActionDecider.
 */
public class SimulationActionDecider implements TradeActionDecider, WithLog {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SimulationActionDecider.class);
    private Optional<TrendTradeActionDeciderParameters> bestParameters = Optional.empty();
    private SimulationParametersTO parameters;
    private Optional<TradeActionDecider> tradeActionDecider = Optional.empty();
    private HistoricalTransactionsBuffer historicalTransactionsBuffer;
    private Supplier<TradeWalletTO> tradeWalletSupplier;

    protected SimulationActionDecider(SimulationParametersTO parameters,  HistoricalTransactionsBuffer historicalTransactionsBuffer, Supplier<TradeWalletTO> tradeWalletSupplier) {
        Preconditions.checkNotNull(parameters);
        Preconditions.checkNotNull(historicalTransactionsBuffer);
        Preconditions.checkNotNull(tradeWalletSupplier);
        this.parameters = parameters;
        this.historicalTransactionsBuffer = historicalTransactionsBuffer;
        this.tradeWalletSupplier = tradeWalletSupplier;
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
        logInfo(LOG, "Run simulation.");
        TradeWalletTO to = tradeWalletSupplier.get();
        parameters.setDigitalCurrencyAmount(to.getDigitalCurrencyAmount());
        parameters.setMoneyAmount(to.getMoneyAmount());

        TraderSimulationRunner runner = new TraderSimulationRunner(() ->  historicalTransactionsBuffer.getHistoricalTransactions().stream().map(ht -> ht.getTransactionPrice()).collect(Collectors.toList()) , parameters);
        runner.setSleepTime(10);
        runner.setPrintProgreess(false);
        runner.execute();
        bestParameters = runner.getParametersForBestResult();
        printLogWithBestParameters();

        // Make some space for new data before run simulation again
        historicalTransactionsBuffer.trimToPercentOfCapacity(parameters.getHistoricalBufferTrimSizePercentage());

        // Create new
        // It could be better option to just update TradeActionDecider parameters instead of creating it from scratch every time.
        tradeActionDecider = new TrendActionDeciderBuilder().build(bestParameters.get());
    }

    private void printLogWithBestParameters() {
        List<HistoricalTransactionTO> historicalTransactions = historicalTransactionsBuffer.getHistoricalTransactions();
        HistoricalTransactionTO start = historicalTransactions.get(0);
        HistoricalTransactionTO end = historicalTransactions.get(historicalTransactions.size() - 1);
        logInfo(LOG,"Simulation finished. Found new best TrendTradeActionDecider parameters {} for historical parameters start {} end  {}", bestParameters.orElse(null), start.getTransactionTimeInMs(), end.getTransactionTimeInMs() );
    }

    @Override
    public Optional<TradeAction> calculateTradeAction(ExchangeDataTO exchangeData) {
        historicalTransactionsBuffer.keep(exchangeData.getHistoricalTransactions());
        if(historicalTransactionsBuffer.isOverLoaded()) {
            logDebug(LOG, "It's time to run simulation. Historical transaction size " + historicalTransactionsBuffer.getHistoricalTransactions().size());
            runSimulation();
        }

        return OptionalConsumerWithResult.of(tradeActionDecider, TradeAction.class).ifPresent(tad -> {
            TradeAction t = tad.calculateTradeAction(exchangeData).orElse(null);
            logDebug( LOG,"Trader after simulation found TA {} ", t);
            return t;
        }).ifNotPresent(() -> {
            logError(LOG, "!!!!!tradeActionDecider does not exist!!!!");
            return null;
        }).getResult();
    }
}
