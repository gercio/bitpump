package com.lovesoft.bitpump.simulation;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.support.EstimatedTimeToFinish;
import com.lovesoft.bitpump.support.WithLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class TraderSimulationRunner implements WithLog {
    private final static Logger LOG = LoggerFactory.getLogger(TraderSimulationRunner.class);
    private ThreadPoolExecutor executor;
    private SimulationParametersTO parameters;
    private HistoricalTransactionSource historical;
    private BestResultFinder bestResultFinder = new BestResultFinder();
    private EstimatedTimeToFinish timeToFinish;

    public TraderSimulationRunner(HistoricalTransactionSource historical, SimulationParametersTO parameters) {
        Preconditions.checkArgument(parameters.getNumberOfThreads() > 0, "To low thread number " + parameters.getNumberOfThreads() );
        this.executor = new ScheduledThreadPoolExecutor(parameters.getNumberOfThreads());
        this.historical = historical;
        this.parameters = parameters;
    }

    public void execute() {
        System.out.println("Starting....");
        logInfo(LOG, "Starting Cosmic Simulation... Please wait a while, I am calculating fast for you (: ");
        logInfo(LOG, "Using Simulation Parameters -> {}", this.parameters);
        runSimulation();
        waitForSimulationToFinish();
        bestResultFinder.printResultsToLog();
    }

    public Optional<ParametersTO> getParametersForBestResult() {
        return bestResultFinder.getBestResult();
    }

    private void runSimulation() {
        getPercentageBuyStream()
                .forEach(percentageBuy ->  getPercentageSellStream()
                .forEach(percentageSell -> getTriggerTargetStream()
                .forEach(triggerTargetSellCount -> getTriggerTargetStream()
                .forEach(triggerTargetBuyCount -> getMaximumLooseStream()
                .forEach(maximumLoosPercentage -> {

                    ParametersTO parameters = ParametersTOBuilder.aParametersTO().withHistoricalTransactionSource(historical)
                            .withMaximumLoosePercentage(maximumLoosPercentage)
                            .withTriggerTargetSellCount(triggerTargetSellCount)
                            .withTriggerTargetBuyCount(triggerTargetBuyCount)
                            .withPercentageSel(percentageSell)
                            .withPercentageBuy(percentageBuy).build();

                    runSimulation(parameters);
        })))));
    }

    private void waitForSimulationToFinish() {
        try {
            timeToFinish = new EstimatedTimeToFinish( executor.getTaskCount());
            logInfo(LOG, "Waiting for execution. Task to be finished: " + executor.getTaskCount());
            boolean isFinished = isFinished();
            while (!isFinished) {
                Thread.sleep(2000);
                printProgress();
                isFinished = isFinished();
            }
            executor.shutdown();
            logInfo(LOG, "Simulation is finished!");
        } catch (InterruptedException e) {
            logWarn(LOG, "Exception " + e.getMessage());
        }
    }

    private void printProgress() {
        StringBuilder sb = new StringBuilder();
        sb.append("Progress " + executor.getCompletedTaskCount() + " / " + executor.getTaskCount());
        sb.append("\t Time to finish [" + timeToFinish.printEstimatedTimeToFinish(executor.getCompletedTaskCount()) + "] ");
        bestResultFinder.getActualBestResult().ifPresent( r -> sb.append(String.format("\t\t Best result = %5.2f" ,r)));
        bestResultFinder.getActualBestResultParameters().ifPresent(p -> sb.append(" for parameters " + p));
        System.out.println(sb.toString());
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

    private IntStream getMaximumLooseStream() {
        return getIntStream((int) parameters.getMaximumLoosePercentageFrom(), (int) parameters.getMaximumLoosePercentageTo());
    }

    private IntStream getTriggerTargetStream() {
        return IntStream.iterate((parameters.getTriggerTargetCountFrom()), n -> n + 1).limit(parameters.getTriggerTargetCountTo() - parameters.getTriggerTargetCountFrom() + 1);
    }

    private IntStream getIntStream(int from, int to) {
        return IntStream.iterate(from, n -> n + 1).limit(to - from + 1);
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
}
