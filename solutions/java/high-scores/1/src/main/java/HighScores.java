import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class HighScores {

    private List<Integer> highScores;

    public HighScores(List<Integer> highScores) {
        this.highScores = new ArrayList<>(highScores);
    }

    List<Integer> scores() {
        return new ArrayList<>(highScores);
    }

    Integer latest() {
        return highScores.get(highScores.size() - 1);
    }

    Integer personalBest() {
        return Collections.max(highScores);
    }

    List<Integer> personalTopThree() {
        List<Integer> sortedScores = new ArrayList<>(highScores);
        Collections.sort(sortedScores, Collections.reverseOrder());
        return sortedScores.subList(0, Math.min(3, sortedScores.size()));
    }

}