package com.lovesoft.bitpump.simulation;

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
                .withTriggerTargetCount(triggerTargetCount)
                .withMaximumLoosePercentage(maximumLoosePercentage)
                .withHistoricalTransactionSource(history).build();
        simulation = new TraderSimulation(parametersTO);
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
        initializeTest(0.85, 0.35, 5, 5.0);
        doTrade(ChartName.Bitmarket24_01);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() >= 0.85, " Should be sold at top with earnings " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }

    @Test
    public void testBitmarket24_02() {
        // New best result  8.623292904800806 %  for parameters ParametersTO{percentageBuy=0.1, percentageSel=0.35, triggerTargetCount=6, maximumLoosePercentage=5.0}
        initializeTest(0.1, 0.35, 6, 5.0);
        doTrade(ChartName.Bitmarket24_02);
        Assertions.assertTrue(tradeWalletStatistics().calculateAssetChangeInPercentage() >= 8, " Should be sold at top with earnings " + tradeWalletStatistics().calculateAssetChangeInPercentage());
    }



    private void doTrade(ChartName chartName) {
        history.setChartName(chartName);
        simulation.run();
    }

}
