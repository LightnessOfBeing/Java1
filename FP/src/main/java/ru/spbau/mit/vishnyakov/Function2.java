package ru.spbau.mit.vishnyakov;

/**
 * Interface that implements behaviour of function with two arguments.
 * @param <T> type of the first argument
 * @param <U> type of the second argument
 * @param <R> return value type
 */

public interface Function2<T, U, R> {

    /**
     * Applies function to the given arguments.
     * @param t argument of a function.
     * @param u
     * @return result of application.
     */

    R apply(T t, U u);

    /**
     * Implements a composition of two function. Namely, g(f(x)).
     * @param before our function g.
     * @param <V> type of a composition.
     * @return result of a composition.
     */

    default <V> Function2<T, U, V> compose(Function1<? super R, ? extends V> before) {
        return (f, s) -> before.apply(this.apply(f, s));
    }

    /**
     * returns partially applied function.
     * @param f first argument.
     * @return f(_, y).
     */

    default Function1<U, R> bind1(T f) {
        return (s) -> this.apply(f, s);
    }

    /**
     * returns partially applied function.
     * @param s second argument.
     * @return f(x, _).
     */

    default Function1<T, R> bind2(U s) {
        return (f) -> this.apply(f, s);
    }

    /**
     * Implements currying.
     * @return curried function.
     */

    default Function1<U, Function1<T, R>> curry() {
        return this::bind2;
    }
}
