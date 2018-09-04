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
    private final long maxValue;

    public EstimatedTimeToFinish(long maxValue) {
        this.maxValue = maxValue;
    }

    public long getEstimatedTimeToFinish(long currentValue) {
        long actualTime = Instant.now().toEpochMilli();
        double progressPercent = MathSupport.calculatePercentageOfXisY(maxValue, currentValue);
        double timePerPercentage = (actualTime - startTime) / progressPercent;
        return (long) (timePerPercentage * (100 - progressPercent));
    }

    public String printEstimatedTimeToFinish(long currentValue) {
        long msToFinish = getEstimatedTimeToFinish(currentValue);
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.plusSeconds((long) (msToFinish / 1000.0));
        dateTime = dateTime.truncatedTo(ChronoUnit.SECONDS);
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
