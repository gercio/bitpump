package com.lovesoft.bitpump.calculation;

import com.lovesoft.bitpump.to.HistoricalTransactionTO;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class HistoricalTransactions {
    private TreeSet<HistoricalTransactionTO> transactions = new TreeSet<>();
    private long maximumTransactions;
    private Long lastTransactionTime;

    public HistoricalTransactions(long maximumTransactions) {
        assert maximumTransactions > 0;
        this.maximumTransactions = maximumTransactions;
    }

    public long size() {
        return transactions.size();
    }

    public List<HistoricalTransactionTO> getReadOnlyTransactionList() {
        // Keep this object in charge of his list of transaction.
//        return Collections.unmodifiableList(transactions.stream().collect(Collectors.toList()));
        List<HistoricalTransactionTO> list = new ArrayList<>(transactions.size());
        list.addAll(transactions);
        return list;
    }

    public void addAll(List<HistoricalTransactionTO> historicalTransactions) {
        historicalTransactions.stream().forEach(this::add);
    }

    public void add(HistoricalTransactionTO historicalTransaction) {
        if(!isYoungEnough(historicalTransaction)) {
            return;
        }
        if(transactions.size() + 1 > maximumTransactions) {
            transactions.remove(transactions.first());
        }
        transactions.add(historicalTransaction);
    }

    public boolean isEmpty() {
        return transactions.isEmpty();
    }

    public void clearAndTakeOnlyNewest() {
        if(!isEmpty()) {
            lastTransactionTime = transactions.last().getTransactionTimeInMs();
        }
        transactions.clear();
    }

    private boolean isYoungEnough(HistoricalTransactionTO transaction) {
        if(lastTransactionTime == null) {
            return true;
        }
        return transaction.getTransactionTimeInMs() > lastTransactionTime;
    }
}
