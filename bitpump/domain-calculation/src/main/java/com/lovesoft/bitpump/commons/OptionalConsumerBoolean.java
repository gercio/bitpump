package com.lovesoft.bitpump.commons;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class OptionalConsumerBoolean<T> {
    private OptionalConsumerWithResult<T, Boolean> consumer;

    private OptionalConsumerBoolean(Optional<T> optional) {
        this.consumer = OptionalConsumerWithResult.of(optional, Boolean.class);
    }

    public static <T> OptionalConsumerBoolean<T> of(Optional<T> optional) {
        return new OptionalConsumerBoolean<>(optional);
    }

    public OptionalConsumerBoolean<T> ifPresent(Function<T, Boolean> c) {
       consumer.ifPresent(c);
       return this;
    }

    public OptionalConsumerBoolean<T> ifNotPresent(Supplier<Boolean> s) {
       consumer.ifNotPresent(s);
       return this;
    }
    public boolean getBoolean() {
        return consumer.getResult().orElseThrow(() -> new IllegalStateException("Please use ifPresent or ifNotPresent before this method."));
    }
}
