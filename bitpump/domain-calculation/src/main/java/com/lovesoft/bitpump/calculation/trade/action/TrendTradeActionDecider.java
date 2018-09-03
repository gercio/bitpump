package com.lovesoft.bitpump.calculation.trade.action;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.calculation.HistoricalTransactions;
import com.lovesoft.bitpump.commons.MathSupport;
import com.lovesoft.bitpump.commons.OptionalConsumer;
import com.lovesoft.bitpump.commons.WithLog;
import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Simple trader decider, based on trend observation.
 * @author Patryk Kałużny 2018.02.13
 *
 */
public class TrendTradeActionDecider implements TradeActionDecider, WithLog {
    private static Logger LOG = LoggerFactory.getLogger(TrendTradeActionDecider.class);
    private double percentageUpBuy;
    private double percentageDownSell;
    private HistoricalTransactions transactions;
    private TradeActionTrigger trigger = new TradeActionTrigger(1);

    protected TrendTradeActionDecider(double percentageUpBuy, double percentageDownSell, HistoricalTransactions transactions) {
        Preconditions.checkArgument(percentageUpBuy > 0 && percentageUpBuy < 100, "Bad percentageUpBuy " + percentageUpBuy);
        Preconditions.checkArgument(percentageDownSell > 0 && percentageDownSell < 99, "Bad percentageDownSell " + percentageDownSell);
        Preconditions.checkNotNull(transactions);
        this.percentageUpBuy = percentageUpBuy;
        this.percentageDownSell = percentageDownSell;
        this.transactions = transactions;
        logDebug(LOG, "percentageUpBuy = {}", percentageUpBuy);
    }

    void setTargetBuyCount(int targetBuyCount) {
        trigger.setTargetBuyCount(targetBuyCount);
    }

    void setTargetSellCount(int targetSellCount) {
        trigger.setTargetSellCount(targetSellCount);
    }


    @Override
    public Optional<TradeAction> calculateTradeAction(ExchangeDataTO exchangeData) {
        if(transactions.isEmpty() && exchangeData.getHistoricalTransactions().isEmpty()) {
            logDebug(LOG, "No historical transactions. No trade Action.");
            return Optional.empty();
        }

        Optional<TradeAction> tradeAction = Optional.empty();
        for(HistoricalTransactionTO historicalTransaction : exchangeData.getHistoricalTransactions()) {
            transactions.add(historicalTransaction);
            tradeAction = Optional.ofNullable(calculateTradeAction(reverseHistory()).orElse(tradeAction.orElse(null)));
        }
        OptionalConsumer.of(tradeAction).ifPresent((ta) -> afterTradeActionFinding());
        return tradeAction;
    }

    private Optional<TradeAction> calculateTradeAction(List<HistoricalTransactionTO> reversedHistory) {
        return Optional.ofNullable(checkTrendGoesUp(reversedHistory).orElseGet(() -> checkTrendGoesDown(reversedHistory)));
    }

    private void afterTradeActionFinding() {
        // Clear history - we do not need to keep it back, since trade action was made
        transactions.clearAndTakeOnlyNewest();
    }

    private List<HistoricalTransactionTO> reverseHistory() {
        List<HistoricalTransactionTO> reversedHistory = new ArrayList<>(transactions.getReadOnlyTransactionList());
        Collections.reverse(reversedHistory);
        return reversedHistory;
    }

    private TradeAction checkTrendGoesDown(List<HistoricalTransactionTO> reversedHistory) {
        double lastPrice = reversedHistory.get(0).getTransactionPriceMVA();
        double highestPrice = getHighestPrice(reversedHistory);
        double calculatedPercentage = MathSupport.calculatePercentageOfXisY(lastPrice, highestPrice);
        logDebug(LOG, "Found highest price {} and last price {}. Transaction number is {}. Calculated price down percent = {}", highestPrice, lastPrice, transactions.size(), 100 - calculatedPercentage);
        if(calculatedPercentage >= 100 + percentageDownSell) {
            // OK, we should sell, check the counter
            trigger.count(TradeAction.SELL);
            if(trigger.checkSellTriggeredAndReset()) {
                // Counter ok, sell it!
                return TradeAction.SELL;
            }
        }
        return null;
    }

    private Optional<TradeAction> checkTrendGoesUp(List<HistoricalTransactionTO> reversedHistory) {
        double lastPrice = reversedHistory.get(0).getTransactionPriceMVA();
        double lowestPrice = getLowestPrice(reversedHistory);
        double calculatedPercentage = MathSupport.calculatePercentageOfXisY(lastPrice, lowestPrice);
        logDebug(LOG,"Found lowest price {} and last price {}. Transactions number is {}. Calculated price up percent = {}", lowestPrice, lastPrice, transactions.size(), 100 - calculatedPercentage);
        if(calculatedPercentage <= 100 - percentageUpBuy) {
            // Price goes up for at least percentageUpBuy, so buy it!
            // Check the counter
            trigger.count(TradeAction.BUY);
            if(trigger.checkBuyTriggeredAndReset()) {
                return Optional.of(TradeAction.BUY);
            }
        }
        return Optional.empty();
    }

    private double getHighestPrice(List<HistoricalTransactionTO> reversedHistory) {
        double highest = -1.0;
        for(HistoricalTransactionTO to: reversedHistory) {
            if(to.getTransactionPriceMVA() > highest) {
                highest = to.getTransactionPriceMVA();
            }
        }
        return highest;
    }

    private double getLowestPrice(List<HistoricalTransactionTO> reversedHistory) {
        double lowest = Double.MAX_VALUE;
        for(HistoricalTransactionTO to: reversedHistory) {
            if(to.getTransactionPriceMVA() < lowest) {
                lowest = to.getTransactionPriceMVA();
            }
        }
        return lowest;
    }
}
