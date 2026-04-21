import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Allergies {
    private int score;

    public Allergies (int score) {
        this.score = score;
    }

    public boolean isAllergicTo(Allergen allergen) {
        return (score & allergen.getScore()) != 0;
    }

    public List<Allergen> getList() {
        return Arrays.stream(Allergen.values())
                .filter(this::isAllergicTo)
                .collect(Collectors.toList());
    }
}
