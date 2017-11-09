package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Collection class.
 */

public class Collection {

    /**
     * Method that applies a given function to each element of a list.
     * @param function to be applied.
     * @param collection on which function will be applied.
     * @param <R> initial type.
     * @param <T> result type.
     * @return list of "mapped" elements.
     */

    @NotNull
    public static <R, T> ArrayList<T> map(@NotNull Function1<? super R,? extends T> function, @NotNull Iterable<? extends R> collection) {
        ArrayList<T> list = new ArrayList<>();
        for (R element : collection) {
            list.add(function.apply(element));
        }
        return list;
    }

    /**
     * Returns a list of elements, such that f(a_i) == true.
     * @param p predicate
     * @param collection on which we will execute our function.
     * @param <R> type of a collection.
     * @return list of elements, which satisfy the predicate.
     */

    @NotNull
    public static <R> ArrayList<R> filter(@NotNull Predicate<? super R> p, @NotNull Iterable<? extends R> collection) {
        ArrayList<R> list = new ArrayList<>();
        for (R element : collection) {
            if (p.apply(element)) {
                list.add(element);
            }
        }
        return list;
    }

    /**
     * Method that adds element until the first element, such that f(a_i) == false.
     * @param p predicate
     * @param collection on which we will execute our function.
     * @param <R> type of a collection.
     * @return list of elements which were added.
     */

    @NotNull
    public static <R> ArrayList<R> takeWhile(@NotNull Predicate<? super R> p, @NotNull Iterable<? extends R> collection) {
        ArrayList<R> list = new ArrayList<>();
        for (R element : collection) {
            if (!p.apply(element)) {
                return list;
            }
            list.add(element);
        }
        return list;
    }

    /**
     * Method that adds element until the first element, such that f(a_i) == true.
     * @param p preficate
     * @param collection on which we will execute our function.
     * @param <R> type of a collection.
     * @return list of elements which were added.
     */

    @NotNull
    public static <R> ArrayList<R> takeUnless(@NotNull Predicate<? super R> p, @NotNull Iterable<? extends R> collection) {
        return takeWhile(p.not(), collection);
    }

    /**
     * Method which implements fold procedure from the left side.
     * @param function which will be applied.
     * @param accumulator where we will store the result.
     * @param collection on which we will apply.
     * @param <R> type of elements.
     * @param <T> result type.
     * @return result of left-side fold.
     */

    public static <R, T> T foldl(@NotNull Function2<? super T, ? super R, ? extends T> function, T accumulator, @NotNull Iterable<? extends R> collection) {
        for (R element : collection) {
            accumulator = function.apply(accumulator, element);
        }
        return accumulator;
    }

    /**
     * Method which implements fold procedure from the right side.
     * @param function which will be applied.
     * @param collection on which we will apply.
     * @param <R> type of elements.
     * @param <T> result type.
     * @return result of left-side fold.
     */

    public static final <R, T> T foldr(@NotNull Function2<? super R, ? super T, ? extends T> function, T accumulator, @NotNull Iterable<? extends R> collection) {
        ArrayList<R> list = new ArrayList<>();
        for (R element : collection) {
            list.add(element);
        }
        ListIterator<R> it = list.listIterator(list.size());
        while (it.hasPrevious()) {
            accumulator = function.apply(it.previous(), accumulator);
        }
        return accumulator;
    }
}
