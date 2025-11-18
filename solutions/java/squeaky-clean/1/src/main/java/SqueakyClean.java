import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqueakyClean {
    public static String clean(String identifier) {
        String step1 = identifier.replaceAll("\\s", "_");

        Matcher matcher = Pattern.compile("-(\\w)").matcher(step1);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        String step2 = sb.toString();

        String step3 = step2.replaceAll("4", "a")
                            .replaceAll("3", "e")
                            .replaceAll("0", "o")
                            .replaceAll("1", "l")
                            .replaceAll("7", "t");

        String step4 = step3.replaceAll("[^a-zA-Z_]", "");
        
        return step4;
    }
}