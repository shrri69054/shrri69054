import java.util.Arrays;

public class Transpose {
    String transpose(String input) {
        String[] rows = input.split("\n");
        int max = Arrays.stream(rows).mapToInt(String::length).max().orElse(0);
        String[] columns = new String[max];

        int last = rows.length;
        for (int i = 0; i < max; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < last; j++) {
                char cell = rows[j].length() > i ? rows[j].charAt(i) : ' ';
                sb.append(cell);
            }
            columns[i] = sb.toString();
            while (last > 0 && rows[last - 1].length() <= i + 1) {
                last--;
            }
        }

        return String.join("\n", columns);
    }
}