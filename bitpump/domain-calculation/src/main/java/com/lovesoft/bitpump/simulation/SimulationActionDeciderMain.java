package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.calculation.trade.action.SimulationActionDeciderParameters;
import com.lovesoft.bitpump.simulation.SimulationDataSupport.ChartName;
import com.lovesoft.bitpump.support.WithLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created 07.03.2018 18:03.
 */
public class SimulationActionDeciderMain implements WithLog{
    private TraderSimulation simulation;
    private SimulationHistoricalTransaction history = new SimulationHistoricalTransaction(20);
    private static final Logger LOG = LoggerFactory.getLogger(SimulationActionDeciderMain.class);

    public void run() {

        ParametersTO parameters = new ParametersTO();
        parameters.setHistoricalTransactionSource(history);
        parameters.setMaximumLoosePercentage(5);
        parameters.setStartMoneyAmount(0);
        parameters.setStartDigitalCurrencyAmount(1);
        parameters.setCalculateStatisticsOnlyForDX(true);
        SimulationActionDeciderParameters param = new SimulationActionDeciderParameters();
        param.setNumberOfHistoricalTransactionsToRunSimulation(300);

        SimulationParametersTO simParam = new SimulationParametersTO();
        simParam.setDoubleStep(1);
        simParam.setPercentageBuyFrom(1);
        simParam.setPercentageBuyTo(7);
        simParam.setPercentageSelFrom(1);
        simParam.setPercentageSelTo(7);
        simParam.setMaximumLoosePercentageFrom(99);
        simParam.setMaximumLoosePercentageTo(99);
        simParam.setNumberOfThreads(4);
        simParam.setTriggerTargetCountFrom(2);
        simParam.setTriggerTargetCountTo(30);
        simParam.setHistoricalBufferTrimSizePercentage(55);
        simParam.setCalculateStatisticsOnlyForDX(false);


        param.setParameters(simParam);
        parameters.setTrendParameters(param);
        logInfo(LOG, "Starting with parameters {} ", parameters);

        history.setChartName(ChartName.Bitmarket24_05);
        simulation = new TraderSimulation(parameters);
        param.setWalletToSupplier(simulation.getWalletTOSupplier());
        simulation.setPrintSummary(true);
        Thread t = new Thread(simulation);
        t.start();

        while(t.isAlive()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logInfo(LOG, "Current wallet statistics earnings -> {} with wallet {} with sell exchange rate {} ", simulation.getTradeWalletStatistics().calculateAssetChangeInPercentage(), simulation.getTradeWalletStatistics().getLastWallet().orElse(null),  simulation.getExchange().getExchangeData().getSellExchangeRate());
        }

    }
    public static void main(String[] arg) {
       new SimulationActionDeciderMain().run();
    }
}
