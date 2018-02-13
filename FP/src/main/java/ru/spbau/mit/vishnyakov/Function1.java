package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;

/**
 * Interface that implements behaviour of function with one argument.
 * @param <T> type of an argument
 * @param <R> return value type
 */

public interface Function1<T, R> {

    /**
     * Applies function to the given argument.
     * @param t argument of a function.
     * @return result of application.
     */

    @NotNull R apply(T t);

    /**
     * Implements a composition of two function. Namely, g(f(x)).
     * @param before our function g.
     * @param <V> type of a composition.
     * @return result of a composition.
     */

    @NotNull
    default <V> Function1<T,V> compose(@NotNull Function1<? super R,? extends V> before) {
        return (f) -> before.apply(this.apply(f));
    }
}
