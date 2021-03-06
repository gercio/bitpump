package com.lovesoft.bitpump.calculation.trade.action.candle;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.calculation.candle.CandleChart;
import com.lovesoft.bitpump.calculation.candle.CandleChartBuilder;
import com.lovesoft.bitpump.calculation.candle.CandleValue;
import com.lovesoft.bitpump.calculation.trade.action.TradeActionDecider;
import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;
import com.lovesoft.bitpump.to.ExchangeDataTO;
import com.lovesoft.bitpump.to.TradeAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CandleTimeIndicatorActionDecider implements TradeActionDecider {

    private CTIADParameters parameters;
    private Counter redCount;
    private Counter greenCount;
    private List<HistoricalTransactionTO> historicalTransactions = new ArrayList<>();

    public CandleTimeIndicatorActionDecider(CTIADParameters parameters) {
        Preconditions.checkNotNull(parameters);
        this.parameters = parameters;
        this.redCount = new Counter(parameters.getRedCounter());
        this.greenCount = new Counter(parameters.getRedCounter());
    }

    @Override
    public Optional<TradeAction> calculateTradeAction(ExchangeDataTO exchangeData) {
        CandleChart candleChart = generateCandleChart(exchangeData);
        calculateGreenRedCounters(candleChart);
        return calculateTradeAction();
    }

    private Optional<TradeAction> calculateTradeAction() {
        if(greenCount.isTriggered()) {
            // Green counter big enough to trigger sell
            historicalTransactions.clear();
            return Optional.of(TradeAction.SELL);
        }
        if(redCount.isTriggered()) {
            // Red counter big enough to trigger buy
            historicalTransactions.clear();
            return Optional.of(TradeAction.BUY);
        }
        return Optional.empty();
    }

    private void calculateGreenRedCounters(CandleChart candleChart) {
        List<CandleValue> candleValues = candleChart.getValues();
        CandleValue cvPrev = candleValues.get(0);
        for(int i = parameters.getPreviousCandleToCompare(); i < candleValues.size(); ++ i) {
            CandleValue cv = candleValues.get(i);
            if(cv.haveBiggerValue(cvPrev)) {
                greenCount.count();
                redCount.reset();
            } else {
                redCount.count();
                greenCount.reset();
            }
            cvPrev = candleValues.get(i-parameters.getPreviousCandleToCompare() + 1);
        }
    }

    private CandleChart generateCandleChart(ExchangeDataTO exchangeData) {
        historicalTransactions.addAll(exchangeData.getHistoricalTransactions());
        CandleChartBuilder builder = new CandleChartBuilder();
        return builder.build(historicalTransactions, parameters.getChartTimeInterval());
    }
}
