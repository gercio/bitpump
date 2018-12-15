package com.lovesoft.bitpump.simulation;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.commons.EstimatedTimeToFinish;
import com.lovesoft.bitpump.commons.WithLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class TraderSimulationRunner2 implements WithLog {
    private static final Logger LOG = LoggerFactory.getLogger(TraderSimulationRunner2.class);
    private ThreadPoolExecutor executor;
    private BestResultFinder bestResultFinder = new BestResultFinder();
    private EstimatedTimeToFinish timeToFinish;
    private SimulationParameters2TO parameters;
    private SimulationParametersSource parametersSource;
    private long sleepTime = 2000;
    private boolean printProgress = true;

    public TraderSimulationRunner2(SimulationParameters2TO parameters, SimulationParametersSource parametersSource) {
        Preconditions.checkArgument(parameters.getNumberOfThreads() > 0, "To low thread number " + parameters.getNumberOfThreads() );
        this.executor = new ScheduledThreadPoolExecutor(parameters.getNumberOfThreads());
        this.parameters = parameters;
        this.parametersSource = parametersSource;
        logDebug(LOG, "Using Simulation Parameters -> {}", this.parameters);
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void setPrintProgress(boolean printProgress) {
        this.printProgress = printProgress;
    }

    public void execute() {
        logInfo(LOG, "Starting Cosmic Simulation... Please wait a while, I am calculating fast for you (: ");
        runSimulation();
        waitForSimulationToFinish();
    }

    public Optional<ParametersTO> getParametersForBestResult() {
        return bestResultFinder.getBestResult();
    }

    public Optional<Double> getPercentageForBestResult() {
        return bestResultFinder.getActualBestResult();
    }

    private void runSimulation() {
        parametersSource.getParameters().forEach(par -> {
            ParametersTO pTo = ParametersTOBuilder.aParametersTO().withHistoricalTransactionSource(par.getHistoricalTransactionSource())
                    .withMaximumLoosePercentage(par.getMaximumLoosePercentage())
                    .withTradeActionDeciderParameters(par.getTrendParameters())
                    .withStartMoneyAmount(this.parameters.getMoneyAmount())
                    .withStartDigitalCurrencyAmount(this.parameters.getDigitalCurrencyAmount())
                    .build();
            runSimulation(pTo);
        });
    }

    private void waitForSimulationToFinish() {
        try {
            timeToFinish = new EstimatedTimeToFinish( executor.getTaskCount());
            logInfo(LOG, "Waiting for execution. Task to be finished: " + executor.getTaskCount());
            boolean isFinished = isFinished();
            while (!isFinished) {
                Thread.sleep(sleepTime);
                printProgress();
                isFinished = isFinished();
            }
            executor.shutdown();
            logInfo(LOG, "Simulation is finished!");
        } catch (InterruptedException e) {
            logWarn(LOG, "Exception " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void printProgress() {
        if(printProgress && LOG.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Progress " + executor.getCompletedTaskCount() + " / " + executor.getTaskCount());
            sb.append("\t Time to finish [" + timeToFinish.printEstimatedTimeToFinish(executor.getCompletedTaskCount()) + "] ");
            bestResultFinder.getActualBestResult().ifPresent( r -> sb.append(String.format("\t\t Best result = %5.2f" ,r)));
            bestResultFinder.getActualBestResultParameters().ifPresent(p -> sb.append(" for parameters " + p));
            LOG.info(sb.toString());
        }
    }

    private boolean isFinished() {
        return executor.getCompletedTaskCount() >= executor.getTaskCount();
    }
    private void runSimulation(ParametersTO parameters) {
        TraderSimulation simulation = new TraderSimulation(parameters);
        handleStatistics(parameters, simulation);
        executor.execute(simulation);
    }

    private void handleStatistics(ParametersTO parameters, TraderSimulation simulation) {
        simulation.setStatisticsConsumer( st -> bestResultFinder.findBestResult(st, parameters) );
    }
}
