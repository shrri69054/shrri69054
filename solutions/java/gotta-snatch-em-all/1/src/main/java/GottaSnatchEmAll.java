import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GottaSnatchEmAll {

    public static Set<String> newCollection(List<String> cards) {
        return new HashSet<>(cards);
    }

    public static boolean addCard(String card, Set<String> collection) {
        return collection.add(card);
    }

    public static boolean canTrade(Set<String> myCollection, Set<String> theirCollection) {
        for (String card : myCollection) {
            if (!theirCollection.contains(card)) {
                for (String theirCard : theirCollection) {
                    if (!myCollection.contains(theirCard)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Set<String> commonCards(List<Set<String>> collections) {
        Set<String> common = new HashSet<>();
        if (!collections.isEmpty()) {
            common.addAll(collections.get(0));
            collections.forEach(common::retainAll);
        }
        return common;
    }

    public static Set<String> allCards(List<Set<String>> collections) {
        Set<String> all = new HashSet<>();
        collections.forEach(all::addAll);
        return all;
    }
}