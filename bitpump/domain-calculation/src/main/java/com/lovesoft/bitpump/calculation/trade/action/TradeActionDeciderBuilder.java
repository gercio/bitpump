package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.support.BitPumpRuntimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TradeActionDeciderBuilder {
    private List<TradeActionBuilder> builders = new ArrayList<>();

    public TradeActionDeciderBuilder() {
        builders.add(new TrendActionDeciderBuilder());
        builders.add(new SimulationActionDeciderBuilder());
    }

    public TradeActionDecider build(TradeActionParameters  parameters) {
        return builders.stream()
                .map(tad -> (Optional<TradeActionDecider>) tad.build(parameters))
                .filter( o -> o.isPresent())
                .findFirst()
                .orElseThrow( () -> new BitPumpRuntimeException("Can't find builder for parameters " + parameters)).get();
    }
}
