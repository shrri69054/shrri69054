import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class WordSearcher {

    Map<String, Optional<WordLocation>> search(Set<String> words, char[][] grid) {

        Map<String, Optional<WordLocation>> res = new HashMap<>();

        // generate possible directions
        Pair directions[] = new Pair[8];

        for (int i = 0; i < 9; i++)
            directions[i < 5 ? i : i - 1] = new Pair(i / 3 - 1, i % 3 - 1);

        for (String s : words) {
            res.put(s, Optional.empty());
            // find match for first character
            for (int x = 0; x < grid.length; x++)
                for (int y = 0; y < grid[0].length; y++)
                    if (s.charAt(0) == grid[x][y]) {
                        int len = s.length();
                        for (Pair pt : directions) {
                            int _x = x, _y = y;
                            StringBuilder ss = new StringBuilder(String.valueOf(grid[_x][_y]));

                            for (int i = 1; i < len; i++) {
                                _x += pt.getX();
                                _y += pt.getY();
                                if (_x < 0 || _y < 0 || _x >= grid.length || _y >= grid[_x].length)
                                    break;
                                if (grid[_x][_y] != s.charAt(i))
                                    break;
                                ss.append(grid[_x][_y]);
                            }
                            if (ss.length() == len && s.equals(ss.toString()))
                                res.put(s, Optional.of(new WordLocation(new Pair(y+1, x+1), new Pair(_y+1, _x+1))));
                        }
                    }
        }
        return res;
    }

}