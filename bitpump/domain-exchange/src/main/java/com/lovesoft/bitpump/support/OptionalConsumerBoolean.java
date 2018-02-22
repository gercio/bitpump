package com.lovesoft.bitpump.support;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

public class OptionalConsumerBoolean<T> {
    private Optional<T> optional;
    private Optional<Boolean> result = Optional.empty();

    private OptionalConsumerBoolean(Optional<T> optional) {
        this.optional = optional;
    }

    public static <T> OptionalConsumerBoolean<T> of(Optional<T> optional) {
        return new OptionalConsumerBoolean<>(optional);
    }

    public OptionalConsumerBoolean<T> ifPresent(Function<T, Boolean> c) {
        if(optional.isPresent()) {
            result = Optional.of(c.apply(optional.get()));
        }
        return this;
    }

    public OptionalConsumerBoolean<T> ifNotPresent(BooleanSupplier s) {
        if (!optional.isPresent()) {
            result = Optional.of(s.getAsBoolean());
        }
        return this;
    }
    public boolean getBoolean() {
        return result.orElseThrow(() -> new IllegalStateException("Please use ifPresent or ifNotPresent before this method"));
    }
}
