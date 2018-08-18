package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.calculation.trade.TraderFactory;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWalletStatistics;
import com.lovesoft.bitpump.commons.WithLog;
import com.lovesoft.bitpump.exchange.Exchange;
import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
import com.lovesoft.bitpump.exchange.LocalSimulationExchange;
import com.lovesoft.bitpump.to.TradeWalletTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TraderSimulation implements Runnable, WithLog {
    private TraderFactory traderFactory;
    private long counter;
    private TradeWalletStatistics tradeWalletStatistics;
    private HistoricalTransactionSource historicalTransactionSource;
    private Logger LOG = LoggerFactory.getLogger(TraderSimulation.class);
    private String parameters;
    private Optional<Consumer<TradeWalletStatistics>> statisticsConsumer = Optional.empty();
    private boolean printSummary = false;
    private ParametersTO parametersTo;

    public TraderSimulation(ParametersTO parameters) {
        this.parameters = parameters.toString();
        historicalTransactionSource = parameters.getHistoricalTransactionSource();
        traderFactory = new TraderFactory();
        traderFactory.withParameters(parameters.getTrendParameters())
                .withStopLoosPercentage(parameters.getMaximumLoosePercentage())
                .createDefaultTrader();

        counter = 0;
        traderFactory.getTradeWallet().addDigitalCurrency(parameters.getStartDigitalCurrencyAmount());
        traderFactory.getTradeWallet().addMoneyAmount(parameters.getStartMoneyAmount());
        tradeWalletStatistics = new TradeWalletStatistics(getExchange());
        this.parametersTo = parameters;
    }

    public Exchange getExchange() {
        return traderFactory.getExchange();
    }

    public void setPrintSummary(boolean printSummary) {
        this.printSummary = printSummary;
    }

    public void setStatisticsConsumer(Consumer<TradeWalletStatistics> statisticsConsumer) {
        this.statisticsConsumer = Optional.of(statisticsConsumer);
    }

    public void run() {
        // remove all historical transactions
        TradeWallet tradeWallet = traderFactory.getTradeWallet();
        LocalSimulationExchange exchange = (LocalSimulationExchange) getExchange();

        if(parametersTo != null) {
            tradeWallet.setDigitalCurrencyAmount(parametersTo.getStartDigitalCurrencyAmount());
            tradeWallet.setMoneyAmount(parametersTo.getStartMoneyAmount());
        } else {
            tradeWallet.setDigitalCurrencyAmount(0);
            tradeWallet.setMoneyAmount(100);
        }

        for(int i = 0 ; i < historicalTransactionSource.getHistoricalTransactions().size(); ++i) {
            HistoricalTransactionTO ht = historicalTransactionSource.getHistoricalTransactions().get(i);
            long timeInMs = ht.getTimeInMS() < 1? counter++ : ht.getTimeInMS();
            ht = new HistoricalTransactionTO(timeInMs, ht.getTransactionPrice(), ht.getTransactionPriceMVA());

            double exchangeRate = ht.getTransactionPrice();
            // Simulate that something new trade came up
            exchange.keepOnlyThisHistoricalTransaction(ht);

            // Update statistics
            if(counter <= 1) {
                tradeWalletStatistics.start(tradeWallet.getTraderWalletTO());
            }

            // Do trading!
            if(LOG.isDebugEnabled()) {
                LOG.debug("Exchange Rate " + exchangeRate + " wallet " + tradeWallet.getTraderWalletTO());
            }
            traderFactory.getTrader().doTrades();
            tradeWalletStatistics.updateWalletTO(tradeWallet.getTraderWalletTO());
        }

        tradeWalletStatistics.updateWalletTO(tradeWallet.getTraderWalletTO());
        statisticsConsumer.ifPresent(c -> c.accept(tradeWalletStatistics));
        if(printSummary) {
            logInfo(LOG, "Result for {} is  percentage = {}", parameters.toString(), getTradeWalletStatistics().calculateAssetChangeInPercentage() );
        }
    }

    public TradeWalletStatistics getTradeWalletStatistics() {
        return tradeWalletStatistics;
    }

    public Supplier<TradeWalletTO> getWalletTOSupplier() {
        return traderFactory.getTradeWallet();
    }
}
