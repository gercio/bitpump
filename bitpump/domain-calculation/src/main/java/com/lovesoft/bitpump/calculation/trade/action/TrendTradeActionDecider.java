package com.lovesoft.bitpump.calculation.trade.action;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.calculation.HistoricalTransactions;
import com.lovesoft.bitpump.support.MathSupport;
import com.lovesoft.bitpump.support.OptionalConsumer;
import com.lovesoft.bitpump.support.WithLog;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.HistoricalTransactionTO;
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

    public void setTriggerTargetCount(int targetCount) {
        this.trigger.setTargetCount(targetCount);
    }

    @Override
    public Optional<TradeAction> calculateTradeAction() {
        if(transactions.isEmpty()) {
            logDebug(LOG, "No historical transactions. No trade Action.");
            return Optional.empty();
        }

        List<HistoricalTransactionTO> reversedHistory = reverseHistory();
        Optional<TradeAction> tradeAction = Optional.ofNullable(checkTrendGoesUp(reversedHistory).orElseGet(() -> checkTrendGoesDown(reversedHistory)));
        OptionalConsumer.of(tradeAction).ifPresent(this::afterTradeActionFinding);
        return tradeAction;
    }

    private void afterTradeActionFinding(TradeAction tradeAction) {
        // Clear history - we do not need to keep it back, since trade action was made
        transactions.clearAndTakeOnlyNewest();
    }

    @Override
    public void loadExchangeData(ExchangeDataTO exchangeData) {
        transactions.addAll(exchangeData.getHistoricalTransactions());
    }

    private List<HistoricalTransactionTO> reverseHistory() {
        List<HistoricalTransactionTO> reversedHistory = new ArrayList<>();
        reversedHistory.addAll(transactions.getReadOnlyTransactionList());
        Collections.reverse(reversedHistory);
        return reversedHistory;
    }

    private TradeAction checkTrendGoesDown(List<HistoricalTransactionTO> reversedHistory) {
        double lastPrice = reversedHistory.get(0).getTransactionPrice();
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
        double lastPrice = reversedHistory.get(0).getTransactionPrice();
        double lowestPrice = getLowestPrice(reversedHistory);
        double calculatedPercentage = MathSupport.calculatePercentageOfXisY(lastPrice, lowestPrice);
        logDebug(LOG,"Found lowest price {} and last price {}. Transaction number is {}. Calculated price up percent = {}", lowestPrice, lastPrice, transactions.size(), 100 - calculatedPercentage);
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
            if(to.getTransactionPrice() > highest) {
                highest = to.getTransactionPrice();
            }
        }
        return highest;
    }

    private double getLowestPrice(List<HistoricalTransactionTO> reversedHistory) {
        double lowest = Double.MAX_VALUE;
        for(HistoricalTransactionTO to: reversedHistory) {
            if(to.getTransactionPrice() < lowest) {
                lowest = to.getTransactionPrice();
            }
        }
        return lowest;
    }
}
