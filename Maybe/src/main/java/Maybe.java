import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Exception class for Maybe.
 */

class MyException extends Exception{

    /**
     * Constructor.
     * @param s - represents error message.
     */

    public  MyException(String s){
        super(s);
    }

}

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
     * Constructor.
     * @param t - represents value.
     */

    private Maybe(@Nullable T t){
        val = t;
    }

    /**
     * Creates new Maybe with some value.
     * @param t value to be stored.
     * @param <T> type of a value
     * @return new instance
     */

    @NotNull
    public static <T> Maybe<T> just(T t){
        return new Maybe<>(t);
    }

    /**
     * Creates new Maybe without value.
     * @param <T> type
     * @return new instance
     */

    public static <T> Maybe<T> nothing(){
        return new Maybe<>(null);
    }

    /**
     *
     * @return value that is storing in instance
     * @throws MyException
     */

    @Nullable
    public T get() throws MyException {
        if (val == null){
            throw new MyException("There is no value!");
        }
        return val;
    }

    public boolean isPresent(){
        return val != null;
    }

    @Nullable
    public <U> Maybe<U> map(@NotNull Function<? super T,  ? extends U> mapper){
        if (!isPresent()){
            return new Maybe<>(null);
        }

        return new Maybe<>(mapper.apply(val));
    }
}


