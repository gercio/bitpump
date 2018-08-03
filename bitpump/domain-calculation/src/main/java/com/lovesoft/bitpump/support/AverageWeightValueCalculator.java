package com.lovesoft.bitpump.support;

import com.lovesoft.bitpump.commons.BitPumpRuntimeException;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class AverageWeightValueCalculator {
    private int averageRange;
    private Deque<Double> values = new LinkedList<>();
    private Deque<Double> weights = new LinkedList<>();

    public AverageWeightValueCalculator(int averageRange) {
        if(averageRange < 1) {
            throw new BitPumpRuntimeException("Size need to be greater than zero. Current averageRange " + averageRange);
        }
        this.averageRange = averageRange;
    }

    public void addValue(double value, double weight) {
        if(values.size() == averageRange) {
            values.removeLast();
            weights.removeLast();
        }
        values.addFirst(value);
        weights.addFirst(weight);
    }

    public double calculateAverageValue() {
        if(values.isEmpty()) {
            throw new BitPumpRuntimeException("There is no data yet.");
        }
        if(values.size() != weights.size()) {
            throw new BitPumpRuntimeException("Size is not same. Something goes wrong!");
        }
        double sum = 0.0;
        double sumWeight = 0.0;
        Iterator<Double> vIt = values.iterator();
        Iterator<Double> wIt = weights.iterator();
        while(vIt.hasNext()) {
            double value = vIt.next();
            double weight = wIt.next();
            sum += value * weight;
            sumWeight += weight;
        }
        return sum /  sumWeight;
    }
}