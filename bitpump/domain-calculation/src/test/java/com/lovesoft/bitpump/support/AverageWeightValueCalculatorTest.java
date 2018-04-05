package com.lovesoft.bitpump.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AverageWeightValueCalculatorTest {

    @Test
    public void testIt() {
        AverageWeightValueCalculator calculator = new AverageWeightValueCalculator(2);

        calculator.addValue(1.0, 1.0);
        checkCalculation(calculator, 1.0);

        calculator.addValue(2.0, 1.0);
        checkCalculation(calculator, 1.5);

        calculator.addValue(3.0, 1.0);
        checkCalculation(calculator, 2.5);

        calculator.addValue(9.0, 1.0);
        checkCalculation(calculator, 6.0);
    }

    private void checkCalculation(AverageWeightValueCalculator calculator, double expected) {
        Assertions.assertEquals(expected, calculator.calculateAverageValue());
    }
}
