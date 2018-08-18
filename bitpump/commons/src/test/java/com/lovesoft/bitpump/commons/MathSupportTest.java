package com.lovesoft.bitpump.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathSupportTest {
    @Test
    public void testCalculatePercentageOfYisX() {
        double percent = MathSupport.calculatePercentageOfXisY(100, 100);
        assertEquals(percent, 100.0);

        percent = MathSupport.calculatePercentageOfXisY(100,50); // 50 is 50% of 100
        assertEquals(percent, 50.0);

        percent = MathSupport.calculatePercentageOfXisY(100, 200);
        assertEquals(percent, 200.0);

        percent = MathSupport.calculatePercentageOfXisY(1356, 176.28);
        assertEquals(percent, 13.0); // 176.28 is 13% of 1356
    }

    @Test
    public void testCalculatePercentageOfYisXWithZero() {
        // Divide by zero inside!
        assertThrows( IllegalArgumentException.class, () -> MathSupport.calculatePercentageOfXisY(0, 0));
    }


    @Test
    public void testCalculateValueOfXPercentFromY() {
        double value = MathSupport.calculateValueOfXPercentFromY(1, 100); // 1% from 100 = 1
        assertEquals(value, 1.0);

        value = MathSupport.calculateValueOfXPercentFromY(10, 123); // 10% from 123 = 12,3
        assertEquals(value, 12.3);
    }
}
