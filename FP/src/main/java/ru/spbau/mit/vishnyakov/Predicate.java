package ru.spbau.mit.vishnyakov;

public interface Predicate<T> extends Function1<T, Boolean> {

    /**
     * Implements predicate which is always true.
     * @param <T> type of a predicate.
     * @return true.
     */

    static <T> Predicate<T> alwaysTrue() {
        return (T x) -> {
            return true;
        };
    }

    /**
     * Implements predicate which is always false.
     * @param <T> type of a predicate.
     * @return false.
     */

    static <T> Predicate<T> alwaysFalse() {
        return (T x) -> {
            return false;
        };
    }

    /**
     * Implements "not" predicate.
     * @return inversed value of a predicate.
     */

    default Predicate<T> not() {
        return (x) -> !this.apply(x);
    }

    /**
     * Implements logic and.
     * @param second predicate.
     * @return logic and of a two predicates.
     */

    default Predicate<T> and(Predicate<T> second) {
        return (x) -> this.apply(x) && second.apply(x);
    }

    /**
     * Implements logic or.
     * @param second predicate.
     * @return logic or of a two predicates.
     */

    default Predicate<T> or(Predicate<T> second) {
        return (x) -> this.apply(x) || second.apply(x);
    }
}
