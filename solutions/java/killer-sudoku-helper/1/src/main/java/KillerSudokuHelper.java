import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KillerSudokuHelper {
    List<List<Integer>> combinationsInCage(final int cageSum, final int cageSize) {
        return combinationsInCage(cageSum, cageSize, List.of());
    }

    List<List<Integer>> combinationsInCage(final int cageSum, final int cageSize, List<Integer> exclude) {
        return combinationsInCage(cageSum, cageSize, Set.copyOf(exclude), 1);
    }

    private List<List<Integer>> combinationsInCage(final int cageSum, final int cageSize, final Set<Integer> exclude, final int min) {
        if (cageSum < min) return List.of();
        if (cageSize == 1) {
            if (min > cageSum || exclude.contains(cageSum)) return List.of();
            return List.of(List.of(cageSum));
        }
        List<List<Integer>> combinations = new ArrayList<>();
        for (int i = min; i <= cageSum / cageSize; i++) {
            if (exclude.contains(i)) continue;
            for (List<Integer> combination : combinationsInCage(cageSum - i, cageSize - 1, exclude, i + 1)) {
                combinations.add(prepend(i, combination));
            }
        }
        return combinations;
    }

    private static <T> List<T> prepend(T item, List<T> list) {
        List<T> newList = new ArrayList<>(list.size() + 1);
        newList.add(item);
        newList.addAll(list);
        return List.copyOf(newList);
    }
}