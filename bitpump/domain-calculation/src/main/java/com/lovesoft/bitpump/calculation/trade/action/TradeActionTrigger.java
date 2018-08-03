package com.lovesoft.bitpump.calculation.trade.action;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.commons.WithLog;
import com.lovesoft.bitpump.to.TradeAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class TradeActionTrigger implements WithLog {
    private int targetBuyCount;
    private int targetSellCount;
    private int count;
    private Optional<TradeAction> lastAction = Optional.empty();
    private final Logger LOG = LoggerFactory.getLogger(TradeActionTrigger.class);

    public TradeActionTrigger(int targetBuyCount) {
        setTargetBuyCount(targetBuyCount);
        setTargetSellCount(targetBuyCount);
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
        return checkIfTriggered(TradeAction.SELL, TradeAction.BUY, targetBuyCount);
    }

    public boolean checkSellTriggeredAndReset() {
        return checkIfTriggered(TradeAction.BUY, TradeAction.SELL, targetSellCount);
    }

    private boolean checkIfTriggered(TradeAction taElse, TradeAction taToCheck, int target) {
        return Optional.of(count >= target && lastAction.orElse(taElse).equals(taToCheck)).map(isTriggered -> {
            if(isTriggered) {
                count = 0;
            }
            return isTriggered;
        }).get();
    }

    public void setTargetBuyCount(int targetBuyCount) {
        Preconditions.checkArgument(targetBuyCount > 0, "Wrong targetBuyCount value " + targetBuyCount);
        this.targetBuyCount = targetBuyCount;
    }

    public void setTargetSellCount(int targetSellCount) {
        Preconditions.checkArgument(targetSellCount > 0, "Wrong targetBuyCount value " + targetSellCount);
        this.targetSellCount = targetSellCount;
    }

    public void setTargetCount(int targetCount) {
        setTargetBuyCount(targetCount);
        setTargetSellCount(targetCount);
    }
}
