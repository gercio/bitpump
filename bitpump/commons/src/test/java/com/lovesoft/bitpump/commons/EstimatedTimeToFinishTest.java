package com.lovesoft.bitpump.commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Created 26.02.2018 13:12.
 */
public class EstimatedTimeToFinishTest {

    @Test public void testGetEstimatedTimeToFinish() {
        EstimatedTimeToFinish timeToFinish = new EstimatedTimeToFinish(10);
        await().atMost(10, TimeUnit.MILLISECONDS);
        long mil = timeToFinish.getEstimatedTimeToFinish(1);
        Assertions.assertTrue(20 < mil && mil < 500, " Bad value " + mil + " it should be around 90 milliseconds");
    }

    @Test public void testGetEstimatedTimeOfFinish() {
        EstimatedTimeToFinish timeToFinish = new EstimatedTimeToFinish(10000);
        await().atMost(10, TimeUnit.MILLISECONDS);
        String formatted = timeToFinish.printEstimatedTimeToFinish(1);
        System.out.println("Date time " + formatted );
        Assertions.assertFalse(formatted.isEmpty(), "Bad formatted date " + formatted);

    }
}
