import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;

public class Anagram {

    private Map<Character,Integer> root = new HashMap<>();
    private String lcRoot;

    public Anagram (String rootString) {
        lcRoot = rootString.toLowerCase();
        stringToMapCharCounts(rootString,root);

    }


    private void stringToMapCharCounts(String valueStr, Map<Character,Integer> values) {
        //if (!valueStr.toLowerCase().equals(lcRoot))
        valueStr.toLowerCase()
                .chars()
                .mapToObj(i ->  Character.valueOf ((char) i))
                .forEach(a-> {
                    int x = values.containsKey(a)?values.get(a)+1:1;
                    values.put(a,x);
                });
    }

    public List<String> match(List<String> proposals) {
        List<String> result = new ArrayList<>();
        for (String a: proposals) {
            Map<Character,Integer> b = new HashMap<>();
            stringToMapCharCounts(a,b);
            if (root.entrySet().containsAll(b.entrySet()) && !a.toLowerCase().equals(lcRoot)) {
                result.add(a);
            }
        }
        return result;
    }

}