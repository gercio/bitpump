package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.calculation.trade.wallet.TradeWalletStatistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BestResultFinderTest {

    @Test
    public void findBestResult() {
        BestResultFinder finder = new BestResultFinder(1);
        findBestResult(finder, 1.0, 10.0);
        findBestResult(finder, 2.0, 20.0);
        findBestResult(finder, -2.0, -20.0);
        findBestResult(finder, 5.1, 50.0);
        findBestResult(finder, -1.0, -10.0);

        Assertions.assertTrue(finder.getActualBestResult().get() == 5.1);
        Assertions.assertTrue(finder.getActualBestResultParameters().isPresent());
        String results = finder.getResults();
        Assertions.assertTrue(results.contains("50.0"), "Bad results: " + results);
    }

    private void findBestResult(BestResultFinder finder, double percentage, double percentageBuy) {
        TradeWalletStatistics mockStatistics = mock(TradeWalletStatistics.class);
        when(mockStatistics.calculateAssetChangeInPercentage()).thenReturn(percentage);
        ParametersTO parameters = ParametersTOBuilder.aParametersTO().withPercentageBuy(percentageBuy).build();
        finder.findBestResult(mockStatistics, parameters);
    }
}
