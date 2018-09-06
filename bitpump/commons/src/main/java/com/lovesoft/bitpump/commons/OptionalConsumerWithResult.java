package com.lovesoft.bitpump.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class OptionalConsumerWithResult<T,K> {
    private Optional<T> optional;
    private Optional<K> result = Optional.empty();
    private static final Logger LOG = LoggerFactory.getLogger(OptionalConsumerWithResult.class);

    private OptionalConsumerWithResult(Optional<T> optional) {
        this.optional = optional;
    }

    public static <T,K> OptionalConsumerWithResult<T,K> of(Optional<T> optional, Class<K> clazz) {
        if(LOG.isTraceEnabled()) {
            LOG.trace("Class for optional {}.", clazz.getName());
        }
        return new OptionalConsumerWithResult<>(optional);
    }

    public OptionalConsumerWithResult<T,K> ifPresent(Function<T, K> c) {
        if(optional.isPresent()) {
            result = Optional.ofNullable(c.apply(optional.get()));
        }
        return this;
    }

    public OptionalConsumerWithResult<T,K> ifNotPresent(Supplier<K> s) {
        if (!optional.isPresent()) {
            result = Optional.ofNullable(s.get());
        }
        return this;
    }
    public Optional<K> getResult() {
        return result;
    }
}
