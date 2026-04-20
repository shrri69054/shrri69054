import java.util.List;
import java.util.HashMap;
import java.util.function.IntBinaryOperator;

class ZebraPuzzle {
    class CheckError extends Exception {
    }
    
    List<String> color = List.of("red", "green", "yellow", "blue", "ivory");
    List<String> nation = List.of("Englishman", "Spaniard", "Ukrainian", "Norwegian", "Japanese");
    List<String> pet = List.of("dog", "snail", "fox", "horse", "zebra");
    List<String> beverage = List.of("coffee", "tea", "milk", "juice", "water");
    List<String> hobby = List.of("dancing", "painter", "reading", "football", "chess");
    List<List<String>> chosen = List.of(color, nation, pet, beverage, hobby);
    HashMap<String, Integer> house = new HashMap<String, Integer>();

    ZebraPuzzle() {
        dfs(0, 0);
    }

    void checkOnly(String inHouse, String outHouse) throws CheckError {
        for (List<String> category: chosen)
            if (category.contains(outHouse)) {
                for (String other: category)
                    if (house.containsKey(other) && house.get(inHouse) == house.get(other))
                        throw new CheckError();
                return;
            }
    }

    void check(String x, String y) throws CheckError {
        if (!house.containsKey(x) && !house.containsKey(y))
            return;
        else if (house.containsKey(x) && !house.containsKey(y))
            checkOnly(x, y);
        else if (!house.containsKey(x) && house.containsKey(y))
            checkOnly(y, x);
        else if (!house.get(x).equals(house.get(y)))
            throw new CheckError();
    }

    void check(String x, String y, IntBinaryOperator func) throws CheckError {
        if (!house.containsKey(x) || !house.containsKey(y))
            return;
        if (func.applyAsInt(house.get(x), house.get(y)) != 0)
            throw new CheckError();
    }

    boolean valid() {
        try {
            check("Englishman", "red");
            check("Spaniard", "dog");
            check("green", "coffee");
            check("Ukrainian", "tea");
            check("green", "ivory", (x, y) -> x - (y + 1));
            check("snail", "dancing");
            check("yellow", "painter");
            check("milk", "milk", (x, y) -> x - 2);
            check("Norwegian", "Norwegian", (x, y) -> (x));
            check("reading", "fox", (x, y) -> Math.abs(x - y) - 1);
            check("painter", "horse", (x, y) -> Math.abs(x - y) - 1);
            check("football", "juice");
            check("Japanese", "chess");
            check("Norwegian", "blue", (x, y) -> Math.abs(x - y) - 1);
        }
        catch (CheckError e) {
            return false;
        }
        return true;
    }

    boolean dfs(int depHouse, int depChosen) {
        if (depHouse == 5)
            return true;
        if (depChosen == 5)
            return dfs(depHouse + 1, 0);
        if (!valid())
            return false;
        for (String now: chosen.get(depChosen)) {
            if (house.containsKey(now))
                continue;
            house.put(now, depHouse);
            if (dfs(depHouse, depChosen + 1))
                return true;
            house.remove(now);
        }
        return false;
    }
    
    String getWaterDrinker() {
        for (String key: house.keySet())
            if (house.get("water").equals(house.get(key)) && nation.contains(key))
                return key;
        return null;
    }

    String getZebraOwner() {
        for (String key: house.keySet())
            if (house.get("zebra").equals(house.get(key)) && nation.contains(key))
                return key;
        return null;
    }
}