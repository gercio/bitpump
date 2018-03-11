package com.lovesoft.bitpump.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created 26.02.2018 13:12.
 */
public class EstimatedTimeToFinishTest {

    @Test public void testGetEstimatedTimeToFinish() throws InterruptedException {
        EstimatedTimeToFinish timeToFinish = new EstimatedTimeToFinish(10);
        Thread.sleep(10);
        long mil = timeToFinish.getEstimatedTimeToFinish(1);
        Assertions.assertTrue(20 < mil && mil < 500, " Bad value " + mil + " it should be around 90 milliseconds");
    }

    @Test public void testGetEstimatedTimeOfFinish() throws InterruptedException {
        EstimatedTimeToFinish timeToFinish = new EstimatedTimeToFinish(10000);
        Thread.sleep(10);
        String formatted = timeToFinish.printEstimatedTimeToFinish(1);
        System.out.println("Date time " + formatted );
        Assertions.assertFalse(formatted.isEmpty(), "Bad formatted date " + formatted);

    }
}
