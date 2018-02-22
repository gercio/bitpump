package com.lovesoft.bitpump.calculation.trade.action;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.support.WithLog;
import com.lovesoft.bitpump.to.TradeAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class TradeActionTrigger implements WithLog {
    private int targetCount;
    private int count;
    private Optional<TradeAction> lastAction = Optional.empty();
    private final Logger LOG = LoggerFactory.getLogger(TradeActionTrigger.class);

    public TradeActionTrigger(int targetCount) {
       setTargetCount(targetCount);
    }

    public void count(TradeAction tradeAction) {
        if(!lastAction.isPresent()) {
            lastAction = Optional.ofNullable(tradeAction);
            count = 0;
        }
        lastAction.ifPresent(ta -> {
            if(ta.equals(tradeAction)) {
                count++;
            } else {
                lastAction = Optional.ofNullable(tradeAction);
                count = 1;
            }
        });
        LOG.debug("Trade action {}  count = {}.", tradeAction, count);
    }

    public boolean checkBuyTriggeredAndReset() {
        return checkIfTriggered(TradeAction.SELL, TradeAction.BUY);
    }

    public boolean checkSellTriggeredAndReset() {
        return checkIfTriggered(TradeAction.BUY, TradeAction.SELL);
    }

    private boolean checkIfTriggered(TradeAction taElse, TradeAction taToCheck) {
        return Optional.of(count >= targetCount && lastAction.orElse(taElse).equals(taToCheck)).map(isTriggered -> {
            if(isTriggered) {
                count = 0;
            }
            return isTriggered;
        }).get();
    }

    public void setTargetCount(int targetCount) {
        Preconditions.checkArgument(targetCount > 0, "Wrong targetCount value " + targetCount);
        this.targetCount = targetCount;
    }
}
