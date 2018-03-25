package com.lovesoft.bitpump.calculation.trade.action;

import com.lovesoft.bitpump.simulation.SimulationParametersTO;
import com.lovesoft.bitpump.to.TradeWalletTO;

import java.util.function.Supplier;

public class SimulationActionDeciderParameters implements TradeActionParameters, WalletToSupplierObserver {
    private SimulationParametersTO parameters;
    private int numberOfHistoricalTransactionsToRunSimulation;
    private Supplier<TradeWalletTO> walletToSupplier;

    public SimulationParametersTO getParameters() {
        return parameters;
    }

    public void setParameters(SimulationParametersTO parameters) {
        this.parameters = parameters;
    }

    public int getNumberOfHistoricalTransactionsToRunSimulation() {
        return numberOfHistoricalTransactionsToRunSimulation;
    }

    public void setNumberOfHistoricalTransactionsToRunSimulation(int numberOfHistoricalTransactionsToRunSimulation) {
        this.numberOfHistoricalTransactionsToRunSimulation = numberOfHistoricalTransactionsToRunSimulation;
    }

    public Supplier<TradeWalletTO> getWalletToSupplier() {
        return walletToSupplier;
    }

    public void setWalletToSupplier(Supplier<TradeWalletTO> walletToSupplier) {
        this.walletToSupplier = walletToSupplier;
    }

    @Override
    public void observerNewWalletToSupplier(Supplier<TradeWalletTO> walletToSupplier) {
        if(getWalletToSupplier() == null) {
            setWalletToSupplier(walletToSupplier);
        }
    }

    @Override
    public String toString() {
        return "SimulationActionDeciderParameters{" +
                "parameters=" + parameters +
                ", numberOfHistoricalTransactionsToRunSimulation=" + numberOfHistoricalTransactionsToRunSimulation +
                '}';
    }
}
