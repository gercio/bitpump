package com.lovesoft.bitpump.calculation;

import com.lovesoft.bitpump.to.HistoricalTransactionTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoricalTransactionsTest {

    private HistoricalTransactions historicalTransactions = new HistoricalTransactions(3);

    @Test
    public void shouldNotHaveMoreThenMaximumTransactions() {
        historicalTransactions.add(getHistoricalTransaction(1));
        historicalTransactions.add(getHistoricalTransaction(2));
        historicalTransactions.add(getHistoricalTransaction(3));
        historicalTransactions.add(getHistoricalTransaction(4));
        historicalTransactions.add(getHistoricalTransaction(5));

        assertEquals(3, historicalTransactions.size());
        assertFalse(shouldContainPrice(historicalTransactions, 1));
        assertFalse(shouldContainPrice(historicalTransactions, 2));
        assertTrue(shouldContainPrice(historicalTransactions, 3));
        assertTrue(shouldContainPrice(historicalTransactions, 4));
        assertTrue(shouldContainPrice(historicalTransactions, 5));
    }

//    @Test
//    public void shouldKeepOrder() {
//        historicalTransactions.add(getHistoricalTransaction(2));
//        historicalTransactions.add(getHistoricalTransaction(1));
//        historicalTransactions.add(getHistoricalTransaction(3));
//
//        assertEquals(1, historicalTransactions.getReadOnlyTransactionList().get(0).getTransactionPrice());
//        assertEquals(2, historicalTransactions.getReadOnlyTransactionList().get(1).getTransactionPrice());
//        assertEquals(3, historicalTransactions.getReadOnlyTransactionList().get(2).getTransactionPrice());
//    }

    private HistoricalTransactionTO getHistoricalTransaction(long l) {
        return new HistoricalTransactionTO(l, l);
    }

    private boolean shouldContainPrice(HistoricalTransactions historicalTransactions, int price) {
        return historicalTransactions.getReadOnlyTransactionList().stream().filter(ht -> ht.getTransactionPrice() == price).findFirst().isPresent();
    }

    @Test
    public void shouldKeepTransactions() {
        historicalTransactions.add(getHistoricalTransaction(1));
        historicalTransactions.add(getHistoricalTransaction(2));
        historicalTransactions.add(getHistoricalTransaction(3));

        assertEquals(1, historicalTransactions.getReadOnlyTransactionList().get(0).getTransactionPrice());
    }

    @Test
    public void shouldClearAndTakeOnlyNewest() {
        historicalTransactions.add(new HistoricalTransactionTO(10, 1));
        historicalTransactions.add(new HistoricalTransactionTO(20,2));
        historicalTransactions.add(new HistoricalTransactionTO(30,3));

        historicalTransactions.clearAndTakeOnlyNewest();
        checkSize(0);

        // IT should not take same transactions again
        historicalTransactions.add(new HistoricalTransactionTO(10, 1));
        checkSize(0);
    }

    private void checkSize(int expected) {
        assertEquals(expected, historicalTransactions.getReadOnlyTransactionList().size());
    }

    @Test
    public void shouldNotAddSameTransactionMultipleTimes() {
        HistoricalTransactionTO historicalTransaction = new HistoricalTransactionTO(10, 1);
        historicalTransactions.add(historicalTransaction);
        historicalTransactions.add(historicalTransaction);

        checkSize(1);
    }



}