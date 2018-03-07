package com.lovesoft.bitpump.calculation;

import com.google.common.base.Preconditions;
import com.lovesoft.bitpump.support.OptionalConsumerWithResult;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created 02.03.2018 20:44.
 */
public class Parameters {
    private HashMap<String, String> parameters = new HashMap<>();

    public void put(String paramName, String paramValue) {
        Preconditions.checkNotNull(paramName);
        parameters.put(paramName, paramValue);
    }

    public void put(String paramName, int paramValue) {
        Preconditions.checkNotNull(paramName);
        parameters.put(paramName, Integer.toString(paramValue));
    }

    public void put(String paramName, double paramValue) {
        Preconditions.checkNotNull(paramName);
        parameters.put(paramName, Double.toString(paramValue));
    }

    public String get(String paramName) {
        Preconditions.checkNotNull(paramName);
        return parameters.get(paramName);
    }

    public Optional<String> getOptional(String paramName) {
        return Optional.ofNullable(get(paramName));
    }

    public Optional<Long> getOptionalLong(String paramName) {
        try {
            return OptionalConsumerWithResult.of(getOptional(paramName), Long.class)
                                             .ifPresent(v -> Long.parseLong(v))
                                             .ifNotPresent(() -> null)
                                             .getResult();
        } catch (NumberFormatException upsss) {
            return Optional.empty();
        }
    }

    public Optional<Double> getOptionalDouble(String paramName) {
        try {
            return OptionalConsumerWithResult.of(getOptional(paramName), Double.class)
                                             .ifPresent(v -> Double.parseDouble(v))
                                             .ifNotPresent(() -> null)
                                             .getResult();
        } catch (NumberFormatException upsss) {
            return Optional.empty();
        }
    }

    @Override public String toString() {
        return "Parameters{" + parameters + '}';
    }
}
