import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class KindergartenGarden {
    private final String[] rows;
    private List<String> students;
    private static String[] defaultStudents = new String[] { "Alice", "Bob", "Charlie", "David", "Eve", "Fred", "Ginny",
            "Harriet", "Ileana", "Joseph", "Kincaid", "Larry" };
    public KindergartenGarden(String plants) {
        this(plants, defaultStudents);
    }
    public KindergartenGarden(String plants, String[] students) {
        rows = plants.split("\\n");
        this.students = Arrays.asList(students);
        Collections.sort(this.students);
    }
    public List<Plant> getPlantsOfStudent(String student) {
        List<Plant> result = new ArrayList<Plant>();
        int i = students.indexOf(student);
        for (String row : rows) {
            result.add(Plant.getPlant(row.charAt(i * 2)));
            result.add(Plant.getPlant(row.charAt(i * 2 + 1)));
        }
        return result;
    }
}
