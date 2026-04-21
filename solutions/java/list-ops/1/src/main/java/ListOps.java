import java.util.List;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

class ListOps {

    static <T> List<T> append(List<T> list1, List<T> list2) {
        final List<T> result = new ArrayList<>(list1);
        for (final T item : list2) {
            result.add(item);
        }        
        return result;
    }

    static <T> List<T> concat(List<List<T>> listOfLists) {
        final List<T> result = new ArrayList<>();
        for (final List<T> sublist : listOfLists) {
            for (final T item : sublist) {
                result.add(item);
            }
        }        
        return result;
    }

    static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        final List<T> result = new ArrayList<>();
        for (final T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }        
        return result;
    }

    static <T> int size(List<T> list) {
        int size = 0;
        for (final T item : list) {
            size += 1;
        }
        return size;
    }

    static <T, U> List<U> map(List<T> list, Function<T, U> transform) {
        final List<U> result = new ArrayList<>();
        for (final T item : list) {
            result.add(transform.apply(item));
        }
        return result;
    }

    static <T> List<T> reverse(List<T> list) {
        final List<T> result = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }        
        return result;
    }

    static <T, U> U foldLeft(List<T> list, U initial, BiFunction<U, T, U> f) {
        U result = initial;
        for (final T item : list) {
            result = f.apply(result, item);
        }        
        return result;
    }

    static <T, U> U foldRight(List<T> list, U initial, BiFunction<T, U, U> f) {
        U result = initial;
        for (final T item : reverse(list)) {
            result = f.apply(item, result);
        }        
        return result;
    }

    private ListOps() {
        // No instances.
    }

}