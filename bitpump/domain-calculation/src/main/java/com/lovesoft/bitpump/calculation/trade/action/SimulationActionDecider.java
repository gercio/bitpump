package com.lovesoft.bitpump.calculation.trade.action;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.commons.OptionalConsumerWithResult;
import com.lovesoft.bitpump.commons.WithLog;
import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
import com.lovesoft.bitpump.simulation.ParametersTO;
import com.lovesoft.bitpump.simulation.SimulationParametersTO;
import com.lovesoft.bitpump.simulation.TraderSimulationRunner;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAction;
import com.lovesoft.bitpump.to.TradeWalletTO;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Use simulation calculation for historical data to find best parameters to run TrendTradeActionDecider.
 */
public class SimulationActionDecider implements TradeActionDecider, WithLog {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SimulationActionDecider.class);
    private Optional<ParametersTO> bestParameters = Optional.empty();
    private Optional<Double> bestPercentage = Optional.empty();

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


    private void runSimulation() {
        logInfo(LOG, "Run simulation.");
        TradeWalletTO to = tradeWalletSupplier.get();
        parameters.setDigitalCurrencyAmount(to.getDigitalCurrencyAmount());
        parameters.setMoneyAmount(to.getMoneyAmount());

        TraderSimulationRunner runner = new TraderSimulationRunner( new HistoricalSourceFromHT(historicalTransactionsBuffer.getHistoricalTransactionsTO()), parameters);
        runner.setSleepTime(10);
        runner.setPrintProgress(false);
        runner.execute();
        bestParameters = runner.getParametersForBestResult();
        bestPercentage = runner.getPercentageForBestResult();

        printLogWithBestParameters();

        // Make some space for new data before run simulation again
        historicalTransactionsBuffer.trimToPercentOfCapacity(parameters.getHistoricalBufferTrimSizePercentage());

        // Create new
        // It could be better option to just update TradeActionDecider parameters instead of creating it from scratch every time.
        tradeActionDecider = new TrendActionDeciderBuilder().build((TrendTradeActionDeciderParameters) bestParameters.get().getTrendParameters());
    }

    private void printLogWithBestParameters() {
        List<HistoricalTransactionTO> historicalTransactions = historicalTransactionsBuffer.getHistoricalTransactionsTO();
        HistoricalTransactionTO start = historicalTransactions.get(0);
        HistoricalTransactionTO end = historicalTransactions.get(historicalTransactions.size() - 1);
        logInfo(LOG,"Simulation finished. Found new best TrendTradeActionDecider parameters {} with simulated earnings {} for historical parameters start {} end  {}", bestParameters.orElse(null), bestPercentage.orElse(null), start.getTransactionTimeInMs(), end.getTransactionTimeInMs() );
    }

    @Override
    public Optional<TradeAction> calculateTradeAction(ExchangeDataTO exchangeData) {
        historicalTransactionsBuffer.keep(exchangeData.getHistoricalTransactions());
        if(historicalTransactionsBuffer.isOverLoaded()) {
            logDebug(LOG, "It's time to run simulation. Historical transaction size " + historicalTransactionsBuffer.getHistoricalTransactionsTO().size());
            runSimulation();
        }

        return OptionalConsumerWithResult.of(tradeActionDecider, TradeAction.class).ifPresent(tad -> {
            TradeAction t = tad.calculateTradeAction(exchangeData).orElse(null);
            logDebug( LOG,"Trader after simulation found TA {} ", t);
            return t;
        }).ifNotPresent(() -> {
            logWarn(LOG, "TradeActionDecider does not exist yet!");
            return null;
        }).getResult();
    }
}
