package com.lovesoft.bitpump.simulation;

import com.lovesoft.bitpump.calculation.Parameters;
import com.lovesoft.bitpump.calculation.trade.TraderFactory;
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

        Parameters parameters = new Parameters();

        parameters.put(TraderFactory.ACTION_DECIDER_BUILDER_NAME, TraderFactory.SIMULATION_ACTION_DECIDER);
        parameters.put(TraderFactory.NUMBER_OF_THREADS, 4);
        parameters.put(TraderFactory.MAXIMUM_LOOSE_PERCENTAGE_FROM, 3);
        parameters.put(TraderFactory.MAXIMUM_LOOSE_PERCENTAGE_TO, 12);
        parameters.put(TraderFactory.DOUBLE_STEP, 0.1);
        parameters.put(TraderFactory.PERCENTAGE_BUY_FROM, 0.1);
        parameters.put(TraderFactory.PERCENTAGE_SEL_FROM, 0.1);
        parameters.put(TraderFactory.PERCENTAGE_BUY_TO, 0.3);
        parameters.put(TraderFactory.PERCENTAGE_SEL_TO, 0.3);
        parameters.put(TraderFactory.TRIGGER_TARGET_COUNT_FROM, 1);
        parameters.put(TraderFactory.TRIGGER_TARGET_COUNT_TO, 20);
        parameters.put(TraderFactory.NUMBER_OF_HISTORICAL_DATA_TO_RUN_SIMULATION, 3000);

        simulation = new TraderSimulation(parameters, history);
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
