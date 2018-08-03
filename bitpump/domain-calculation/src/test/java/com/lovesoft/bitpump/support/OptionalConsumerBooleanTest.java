package com.lovesoft.bitpump.support;

import com.lovesoft.bitpump.commons.OptionalConsumerBoolean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalConsumerBooleanTest {

    @Test
    public void testPresent() {
        boolean result = OptionalConsumerBoolean.of(Optional.of(5.0)).ifPresent(v -> v == 5.0).ifNotPresent(() -> false).getBoolean();
        Assertions.assertTrue(result, "Must be true because there was optional and it's value was 5");
    }

    @Test
    public void testNotPresent() {
        boolean result = OptionalConsumerBoolean.of(Optional.ofNullable((Double)null)).ifPresent( v -> false).ifNotPresent(() -> true).getBoolean();
        Assertions.assertTrue(result, "Must be true because optional was not present");
    }

    @Test
    public void testThrowsExceptionWhenUsageIsBadly() {
        Assertions.assertThrows(IllegalStateException.class, () -> OptionalConsumerBoolean.of(Optional.of(5.0)).getBoolean());
    }
}
