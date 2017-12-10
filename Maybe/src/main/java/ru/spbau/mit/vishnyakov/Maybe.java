package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Maybe implementation.
 * @param <T> - represents type.
 */

public class Maybe<T> {

    /**
     * Represents storing value.
     */

    @Nullable
    private final T val;

    /**
     * Static field that represents Nothing.
     */

    @Nullable
    private static final Maybe NOTHING = new Maybe<>(null);

    /**
     * Constructor.
     * @param t - represents value.
     */

    private Maybe(@Nullable T t) {
        val = t;
    }

    /**
     * Creates new Maybe with some value.
     * @param t value to be stored.
     * @param <T> type of a value
     * @return new instance
     */

    @NotNull
    public static <T> Maybe<T> just(T t) {
        return new Maybe<>(t);
    }

    /**
     * Creates new Maybe without value.
     * @param <T> type
     * @return new instance
     */

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> Maybe<T> nothing() {
        return NOTHING;
    }

    /**
     * Returns value if it is exists, otherwise false.
     * @return value that is storing in instance
     * @throws MyException which occurs in case val equals null.
     */

    @Nullable
    public T get() throws MyException {
        if (val == null) {
            throw new MyException("There is no value!");
        }
        return val;
    }

    /**
     * Checks if value is present.
     * @return true if value is present, otherwise false.
     */

    public boolean isPresent() {
        return val != null;
    }

    /**
     * Applies function to a stored value.
     * @param mapper is a function, which we want to apply.
     * @param <U> type of result object.
     * @return new Maybe instance, as result of application.
     */

    @Nullable
    public <U> Maybe<U> map(@NotNull Function<? super T,  ? extends U> mapper) {
        if (!isPresent()) {
            return new Maybe<>(null);
        }
        return new Maybe<>(mapper.apply(val));
    }
}


