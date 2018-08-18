package com.lovesoft.bitpump.commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Created 24.02.2018 19:20.
 */
public class OptionalConsumerWithResultTest {

    @Test
    public void testIfNotPresent() {
        Optional<String> oString = Optional.empty();
        Optional<Double> oDouble = OptionalConsumerWithResult.of(oString, Double.class).ifNotPresent(() -> 1.0).ifPresent(s -> s.hashCode() + 2.0).getResult();
        Assertions.assertEquals(1.0d, (double) oDouble.get());
    }

    @Test
    public void testIfPresent() {
        Optional<String> oString = Optional.of("Fikimiki");
        Assertions.assertFalse(() -> OptionalConsumerWithResult.of(oString, Double.class).ifPresent((s) -> s.equals("Kuku") ? 1.0d : null).getResult().isPresent()
        , "String was present but it's value was not Kuku - so returned optional shall be empty");
    }
}
