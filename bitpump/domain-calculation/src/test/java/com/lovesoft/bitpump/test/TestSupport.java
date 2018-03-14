package com.lovesoft.bitpump.test;

import com.lovesoft.bitpump.calculation.trade.TraderFactory;
import com.lovesoft.bitpump.calculation.trade.action.TrendTradeActionDeciderParameters;

public class TestSupport {

    public static TraderFactory createDefaultTraderFactory() {
        TraderFactory traderFactory = new TraderFactory();
        TrendTradeActionDeciderParameters param = new TrendTradeActionDeciderParameters();
        param.setPercentageUpBuy(5);
        param.setPercentageDownSell(5);
        traderFactory.withParameters(param);
        return traderFactory;
    }
}
