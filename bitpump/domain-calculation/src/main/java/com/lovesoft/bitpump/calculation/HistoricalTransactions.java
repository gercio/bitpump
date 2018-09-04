package com.lovesoft.bitpump.calculation;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.exchange.HistoricalTransactionTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HistoricalTransactions {
    private List<HistoricalTransactionTO> transactions = new ArrayList<>();
    private HashSet<HistoricalTransactionTO> set = new HashSet<>();
    private long maximumTransactions;
    private Long lastTransactionTime;

    public HistoricalTransactions(long maximumTransactions) {
        Preconditions.checkArgument(maximumTransactions > 0, "MaximumTransactions should be grater than 0. MaximumTransactions = " + maximumTransactions);
        this.maximumTransactions = maximumTransactions;
    }

    public long size() {
        return transactions.size();
    }

    public List<HistoricalTransactionTO> getReadOnlyTransactionList() {
        List<HistoricalTransactionTO> list = new ArrayList<>(transactions.size());
        list.addAll(transactions);
        return list;
    }

    public void add(HistoricalTransactionTO historicalTransaction) {
        if(set.contains(historicalTransaction) || !isYoungEnough(historicalTransaction)) {
            return;
        }
        if(transactions.size() + 1 > maximumTransactions) {
            set.remove(transactions.remove(0));
        }
        transactions.add(historicalTransaction);
        set.add(historicalTransaction);
    }

    public boolean isEmpty() {
        return transactions.isEmpty();
    }

    public void clearAndTakeOnlyNewest() {
        if(!isEmpty()) {
            lastTransactionTime = transactions.get(transactions.size() - 1).getTransactionTimeInMs();
        }
        transactions.clear();
        set.clear();
    }

    private boolean isYoungEnough(HistoricalTransactionTO transaction) {
        if(lastTransactionTime == null) {
            return true;
        }
        return transaction.getTransactionTimeInMs() > lastTransactionTime;
    }
}
