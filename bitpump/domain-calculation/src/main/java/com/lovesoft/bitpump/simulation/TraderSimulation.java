package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.calculation.trade.TraderFactory;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWallet;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWalletStatistics;
import com.lovesoft.bitpump.exchange.LocalSimulationExchange;
import com.lovesoft.bitpump.support.WithLog;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Consumer;

public class TraderSimulation implements Runnable, WithLog {
    private TraderFactory traderFactory;
    private long counter;
    private TradeWalletStatistics tradeWalletStatistics;
    private HistoricalTransactionSource historicalTransactionSource;
    private Logger LOG = LoggerFactory.getLogger(TraderSimulation.class);
    private ParametersTO parameters;
    private Optional<Consumer<TradeWalletStatistics>> statisticsConsumer = Optional.empty();

    public TraderSimulation(ParametersTO parameters) {
        this.parameters = parameters;
        historicalTransactionSource = parameters.getHistoricalTransactionSource();
        traderFactory = new TraderFactory();
        traderFactory.withMaximumHistoricalTransactions(10000).withPercentageUpBuy(parameters.getPercentageBuy())
                .withPercentageDownSell(parameters.getPercentageSel())
                .withTriggerTargetBuyCount(parameters.getTriggerTargetBuyCount())
                .withTriggerTargetSellCount(parameters.getTriggerTargetSellCount())
                .withMaximumLoosePercentage(parameters.getMaximumLoosePercentage())
                .createDefaultTrader();
        counter = 0;
        tradeWalletStatistics = new TradeWalletStatistics(traderFactory.getExchange());
    }


    public void setStatisticsConsumer(Consumer<TradeWalletStatistics> statisticsConsumer) {
        this.statisticsConsumer = Optional.of(statisticsConsumer);
    }

    public void run() {
        // remove all historical transactions
        TradeWallet tradeWallet = traderFactory.getTradeWallet();
        LocalSimulationExchange exchange = (LocalSimulationExchange) traderFactory.getExchange();

        // No DC
        tradeWallet.setDigitalCurrencyAmount(0);
        // 100 PLN
        tradeWallet.setMoneyAmount(100);

        tradeWalletStatistics.start(tradeWallet.getTraderWalletTO());

        historicalTransactionSource.getHistoricalTransactions().forEach(exchangeRate -> {
            // Simulate that something new trade came up
            exchange.keepOnlyThisHistoricalTransaction(new HistoricalTransactionTO(counter++, exchangeRate));

            // Do trading!
            if(LOG.isDebugEnabled()) {
                LOG.debug("Exchange Rate " + exchangeRate + " wallet " + tradeWallet.getTraderWalletTO());
            }
            traderFactory.getTrader().doTrades();
        } );
        tradeWalletStatistics.updateWalletTO(tradeWallet.getTraderWalletTO());
        statisticsConsumer.ifPresent(c -> c.accept(tradeWalletStatistics));
        logDebug(LOG, "Result for {} is  percentage = {}", parameters.toString(), getTradeWalletStatistics().calculateAssetChangeInPercentage() );
    }

    public TradeWalletStatistics getTradeWalletStatistics() {
        return tradeWalletStatistics;
    }
}
