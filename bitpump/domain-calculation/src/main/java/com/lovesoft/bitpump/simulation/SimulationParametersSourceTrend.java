package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.calculation.trade.action.TrendTradeActionDeciderParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class SimulationParametersSourceTrend implements  SimulationParametersSource{
    private SimulationParametersTO parameters;
    private HistoricalTransactionSource historicalSource;

    public SimulationParametersSourceTrend(SimulationParametersTO parameters, HistoricalTransactionSource historicalSource) {
        this.parameters = parameters;
        this.historicalSource = historicalSource;
    }

    private IntStream getMaximumLooseStream() {
        return getIntStream((int) parameters.getMaximumLoosePercentageFrom(), (int) parameters.getMaximumLoosePercentageTo());
    }

    private IntStream getTriggerTargetStream() {
        return IntStream.iterate((parameters.getTriggerTargetCountFrom()), n -> n + 1).limit(parameters.getTriggerTargetCountTo() - parameters.getTriggerTargetCountFrom() + 1l);
    }

    private IntStream getIntStream(int from, int to) {
        return IntStream.iterate(from, n -> n + 1).limit(to - from + 1l);
    }

    private DoubleStream getPercentageSellStream() {
        return getDoubleStream(parameters.getPercentageSelFrom(), parameters.getPercentageSelTo());
    }

    private DoubleStream getPercentageBuyStream() {
        return getDoubleStream(parameters.getPercentageBuyFrom(), parameters.getPercentageBuyTo());
    }

    private DoubleStream getDoubleStream(double from, double to) {
        return DoubleStream.iterate(from, n -> n + parameters.getDoubleStep())
                .limit((long) ((to - from) / parameters.getDoubleStep()) + 1);
    }

    @Override
    public List<ParametersTO> getParameters() {
        List<ParametersTO> list = new ArrayList<>();
        getPercentageBuyStream()
                .forEach(percentageBuy ->  getPercentageSellStream()
                        .forEach(percentageSell -> getTriggerTargetStream()
                                .forEach(triggerTargetSellCount -> getTriggerTargetStream()
                                        .forEach(triggerTargetBuyCount -> getMaximumLooseStream()
                                                .forEach(maximumLoosPercentage -> {

                                                    TrendTradeActionDeciderParameters param = new TrendTradeActionDeciderParameters();
                                                    param.setTriggerTargetSellCount(triggerTargetSellCount);
                                                    param.setTriggerTargetBuyCount(triggerTargetBuyCount);
                                                    param.setPercentageDownSell(percentageSell);
                                                    param.setPercentageUpBuy(percentageBuy);

                                                    ParametersTO parametersTo = ParametersTOBuilder.aParametersTO().withHistoricalTransactionSource(historicalSource)
                                                            .withMaximumLoosePercentage(maximumLoosPercentage)
                                                            .withStartDigitalCurrencyAmount(parameters.getDigitalCurrencyAmount())
                                                            .withStartMoneyAmount(parameters.getMoneyAmount())
                                                            .withTradeActionDeciderParameters(param).build();

                                                    list.add(parametersTo);
                                                })))));
        return list;
    }
}
