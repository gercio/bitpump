package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.calculation.trade.action.SimulationActionDeciderParameters;
import com.lovesoft.bitpump.commons.WithLog;
import com.lovesoft.bitpump.simulation.SimulationDataSupport.ChartName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created 07.03.2018 18:03.
 */
public class SimulationActionDeciderMain implements WithLog{
    private SimulationHistoricalTransaction history = new SimulationHistoricalTransaction(20);
    private static final Logger LOG = LoggerFactory.getLogger(SimulationActionDeciderMain.class);

    public void run() {

        ParametersTO parameters = new ParametersTO();
        parameters.setHistoricalTransactionSource(history);
        parameters.setMaximumLoosePercentage(5);
        parameters.setStartMoneyAmount(0);
        parameters.setStartDigitalCurrencyAmount(1);
        SimulationActionDeciderParameters param = new SimulationActionDeciderParameters();
        param.setNumberOfHistoricalTransactionsToRunSimulation(300);

        SimulationParametersTO simParam = new SimulationParametersTO();
        simParam.setDoubleStep(1);
        simParam.setPercentageBuyFrom(1);
        simParam.setPercentageBuyTo(7);
        simParam.setPercentageSelFrom(1);
        simParam.setPercentageSelTo(7);
        simParam.setMaximumLoosePercentageFrom(90);
        simParam.setMaximumLoosePercentageTo(90);
        simParam.setNumberOfThreads(4);
        simParam.setTriggerTargetCountFrom(2);
        simParam.setTriggerTargetCountTo(30);
        simParam.setHistoricalBufferTrimSizePercentage(55);

        param.setParameters(simParam);
        parameters.setTrendParameters(param);
        logInfo(LOG, "Starting with parameters {} ", parameters);

        history.setChartName(ChartName.BITMARKET24_05);
        TraderSimulation simulation = new TraderSimulation(parameters);
        param.setWalletToSupplier(simulation.getWalletTOSupplier());
        simulation.setPrintSummary(true);
        Thread t = new Thread(simulation);
        t.start();

        while(t.isAlive()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
               logError(LOG, "Interrupted sleeping." , e);
               throw new RuntimeException(e);
            }
            logInfo(LOG, "Current wallet statistics earnings -> {} with wallet {} with sell exchange rate {} ", simulation.getTradeWalletStatistics().calculateAssetChangeInPercentage(), simulation.getTradeWalletStatistics().getLastWallet().orElse(null),  simulation.getExchange().getExchangeData().getSellExchangeRate());
        }

    }
    public static void main(String[] arg) {
       new SimulationActionDeciderMain().run();
    }
}
