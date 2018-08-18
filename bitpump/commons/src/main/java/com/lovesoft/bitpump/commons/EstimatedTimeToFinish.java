package com.lovesoft.bitpump.commons;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created 26.02.2018 12:59.
 */
public class EstimatedTimeToFinish {
    private long startTime = Instant.now().toEpochMilli();
    private final long MAX_VALUE;

    public EstimatedTimeToFinish(long maxValue) {
        MAX_VALUE = maxValue;
    }

    public long getEstimatedTimeToFinish(long currentValue) {
        long actualTime = Instant.now().toEpochMilli();
        double progressPercent = MathSupport.calculatePercentageOfXisY(MAX_VALUE, currentValue);
        double timePerPercentge = (actualTime - startTime) / progressPercent;
        long estimatedTimeToFinish = (long) (timePerPercentge * (100 - progressPercent));
        return estimatedTimeToFinish;
    }

    public String printEstimatedTimeToFinish(long currentValue) {
        long msToFinish = getEstimatedTimeToFinish(currentValue);
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.plusSeconds((long) (msToFinish / 1000.0));
        dateTime = dateTime.truncatedTo(ChronoUnit.SECONDS);
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
