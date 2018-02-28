package com.lovesoft.bitpump.support;

import java.util.Optional;

public class Holder<T> {
    private Optional<T> holding;
    public Holder(T holding) {
        setHolding(holding);
    }

    public static <T> Holder<T> of(T valueToHold) {
        return new Holder<>(valueToHold);
    }

    public static <T> Holder<T> empty() {
        return Holder.of(null);
    }

    public Optional<T> getHolding() {
        return holding;
    }

    public void setHolding(T holding) {
        this.holding = Optional.ofNullable(holding);
    }
}
