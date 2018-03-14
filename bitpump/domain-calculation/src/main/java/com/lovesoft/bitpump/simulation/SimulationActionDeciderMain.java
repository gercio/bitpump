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
    private SimulationHistoricalTransaction history = new SimulationHistoricalTransaction();
    private static final Logger LOG = LoggerFactory.getLogger(SimulationActionDeciderMain.class);

    public void run() {

        ParametersTO parameters = new ParametersTO();
        parameters.setHistoricalTransactionSource(history);
        parameters.setMaximumLoosePercentage(3);
        parameters.setStartMoneyAmount(100);
        parameters.setStartDigitalCurrencyAmount(0);
        SimulationActionDeciderParameters param = new SimulationActionDeciderParameters();
        param.setNumberOfHistoricalTransactionsToRunSimulation(3000);

        SimulationParametersTO simParam = new SimulationParametersTO();
        simParam.setDoubleStep(0.1);
        simParam.setMaximumLoosePercentageFrom(3);
        simParam.setMaximumLoosePercentageTo(12);
        simParam.setNumberOfThreads(4);
        simParam.setPercentageBuyFrom(0.1);
        simParam.setPercentageBuyTo(0.3);
        simParam.setPercentageSelFrom(0.1);
        simParam.setPercentageSelTo(0.3);
        simParam.setTriggerTargetCountFrom(1);
        simParam.setMaximumLoosePercentageTo(20);
        simParam.setHistoricalBufferTrimSizePercentage(80);

        param.setParameters(simParam);
        parameters.setTrendParameters(param);

        simulation = new TraderSimulation(parameters);
        param.setWalletToSupplier(simulation.getWalletTOSupplier());
        simulation.setPrintSummary(true);
        history.setChartName(ChartName.Bitmarket24_05);
        Thread t = new Thread(simulation);
        t.start();

        while(t.isAlive()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logInfo(LOG, "Current wallet statistics earnings -> {} with wallet {} ", simulation.getTradeWalletStatistics().calculateAssetChangeInPercentage(), simulation.getTradeWalletStatistics().getLastWallet().orElse(null) );
        }

    }
    public static void main(String[] arg) {
       new SimulationActionDeciderMain().run();
    }
}
