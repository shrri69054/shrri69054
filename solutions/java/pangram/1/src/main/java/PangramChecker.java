public class PangramChecker {
    public boolean isPangram(String i) {
        return i.toLowerCase().replaceAll("[^a-z]","").chars().distinct().count()==26;
    }
}