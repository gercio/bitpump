package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.calculation.Parameters;
import com.lovesoft.bitpump.calculation.trade.TraderFactory;
import com.lovesoft.bitpump.simulation.SimulationDataSupport.ChartName;
import com.lovesoft.bitpump.calculation.trade.wallet.TradeWalletStatistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TraderSimulationTest {
    private TraderSimulation simulation;
    private SimulationHistoricalTransaction history = new SimulationHistoricalTransaction();

    @BeforeEach
    public void beforeEach() {
        initializeTest(1, 1, 1, 90);
    }

    private void initializeTest(double percentageBuy, double percentageSel, int triggerTargetCount, double maximumLoosePercentage) {
        ParametersTO parametersTO = ParametersTOBuilder.aParametersTO()
                .withPercentageBuy(percentageBuy)
                .withPercentageSel(percentageSel)
                .withTriggerTargetBuyCount(triggerTargetCount)
                .withTriggerTargetSellCount(triggerTargetCount)
                .withMaximumLoosePercentage(maximumLoosePercentage)
                .withHistoricalTransactionSource(history).build();
        simulation = new TraderSimulation(parametersTO);
    }

    private void initializeTest(Parameters parameters) {
        simulation = new TraderSimulation(parameters, history);
    }

    @Test
    public void testChart01() {
        doTrade(ChartName.chart01);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() > 5, " To low earnings % " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }

    private TradeWalletStatistics tradeWalletStatistics() {
        return simulation.getTradeWalletStatistics();
    }

    @Test
    public void testChart02() {
        doTrade(ChartName.chart02);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() > 10, " To low earnings % " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }

    @Test
    public void testChart03() {
        doTrade(ChartName.chart03);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() >= 0, " You should NOT loos any money on that test " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }

    @Test
    public void testChart04() {
        doTrade(ChartName.chart04);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() >= 0.85, " Should be sold at top with earnings " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }

    @Test
    public void testBitmarket24_01() {
        //Best result = 11,35 for parameters ParametersTO{percentageBuy=0.7, percentageSel=0.1, triggerTargetBuyCount=6, triggerTargetSellCount=8, maximumLoosePercentage=3.200000000000001}
        initializeTest(0.85, 0.35, 5, 5.0);
        doTrade(ChartName.Bitmarket24_01);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() >= 0.85, " Should be sold at top with earnings " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }

    @Test
    public void testBitmarket24_02() {
        // New best result  8.623292904800806 %  for parameters ParametersTO{percentageBuy=0.1, percentageSel=0.35, triggerTargetCount=6, maximumLoosePercentage=5.0}
        //--> Found best result 11.468182694495184 %  for parameters ParametersTO{percentageBuy=0.30000000000000004, percentageSel=0.30000000000000004, triggerTargetBuyCount=3, triggerTargetSellCount=4, maximumLoosePercentage=7.0}

        initializeTest(0.1, 0.35, 6, 5.0);
        doTrade(ChartName.Bitmarket24_02);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() >= 8, " Should be sold at top with earnings " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }



    @Test
    public void testBitmarket24_03() {
        //New best result  18.109467087063706 %  for parameters ParametersTO{percentageBuy=0.7, percentageSel=0.4, triggerTargetCount=8, maximumLoosePercentage=5.0}
        //New best result  18.74830108703732 %  for parameters ParametersTO{percentageBuy=0.6, percentageSel=0.4, triggerTargetCount=8, maximumLoosePercentage=3.100000000000001}
        //Best result = 18,98 for parameters ParametersTO{percentageBuy=0.5, percentageSel=0.1, triggerTargetBuyCount=9, triggerTargetSellCount=6, maximumLoosePercentage=3.0}
        initializeTest(0.6, 0.4, 8, 3.1);
        doTrade(ChartName.Bitmarket24_03);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() >= 8, " Should be sold at top with earnings " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }

    @Test
    public void testBitmarket24_04() {
        // New best result  11.184704952107936 %  for parameters ParametersTO{percentageBuy=2.2, percentageSel=0.7, triggerTargetCount=4, maximumLoosePercentage=5.0}
        initializeTest(2.2, 0.7, 4, 5.0);
        doTrade(ChartName.Bitmarket24_04);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() >= 11, " Should be sold at top with earnings " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }

    @Test
    public void testBitmarket24_05_simulation() {
        // New best result  11.184704952107936 %  for parameters ParametersTO{percentageBuy=2.2, percentageSel=0.7, triggerTargetCount=4, maximumLoosePercentage=5.0}
        Parameters parameters = new Parameters();

        parameters.put(TraderFactory.ACTION_DECIDER_BUILDER_NAME, TraderFactory.SIMULATION_ACTION_DECIDER);
        parameters.put(TraderFactory.NUMBER_OF_THREADS, 4);
        parameters.put(TraderFactory.DOUBLE_STEP, 0.5);
        parameters.put(TraderFactory.MAXIMUM_LOOSE_PERCENTAGE_FROM, 4);
        parameters.put(TraderFactory.MAXIMUM_LOOSE_PERCENTAGE_TO, 6);
        parameters.put(TraderFactory.PERCENTAGE_BUY_FROM, 0.1);
        parameters.put(TraderFactory.PERCENTAGE_SEL_FROM, 0.1);
        parameters.put(TraderFactory.PERCENTAGE_BUY_TO, 3);
        parameters.put(TraderFactory.PERCENTAGE_SEL_TO, 3);
        parameters.put(TraderFactory.TRIGGER_TARGET_COUNT_FROM, 1);
        parameters.put(TraderFactory.TRIGGER_TARGET_COUNT_TO, 10);
        parameters.put(TraderFactory.NUMBER_OF_HISTORICAL_DATA_TO_RUN_SIMULATION, 500);

        initializeTest(parameters);
        doTrade(ChartName.Bitmarket24_05);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() >= 11, " Should be sold at top with earnings " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }



    private void doTrade(ChartName chartName) {
        history.setChartName(chartName);
        simulation.run();
    }

}
