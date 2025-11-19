import java.util.HashMap;
import java.util.Map;

class Tournament {
    private Map<String, int[]> table = new HashMap<>();

    void applyResults(String x) {
        for (String s : x.split("\n")) {
            String game[] = s.split(";");

            int a = game[2].equals("win") ? 1 : game[2].equals("loss") ? -1 : 0;
            table.putIfAbsent(game[0], new int[5]);
            table.putIfAbsent(game[1], new int[5]);

            update(game[0], a);
            update(game[1], -a);
        }
    }

    private void update(String s, int a) {
        int values[] = table.get(s);
        ++values[0];
        values[1] += a == 1 ? 1 : 0;
        values[2] += a == 0 ? 1 : 0;
        values[3] -= a == -1 ? -1 : 0;
        values[4] += 1 + (a == 1 ? 2 : a);
        table.put(s, values);
    }

    String printTable() {
        return table.entrySet().stream()
                .sorted(this::compare)
                .map(this::show)
                .reduce("Team                           | MP |  W |  D |  L |  P\n", String::concat);
    }

    private String show(Map.Entry<String, int[]> team) {
        StringBuilder res = new StringBuilder(String.format("%-30s", team.getKey()));
        for (int i : team.getValue())
            res.append(String.format(" | %2d", i));
        return res + "\n";
    }

    private int compare(Map.Entry<String, int[]> a, Map.Entry<String, int[]> b) {
        int c = Integer.compare(b.getValue()[4], a.getValue()[4]);
        return c == 0 ? a.getKey().compareTo(b.getKey()) : c;
    }

}