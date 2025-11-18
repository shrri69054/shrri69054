import java.util.*;
import java.io.*;
import java.util.stream.*;
class GrepTool {

    String grep(String pattern, List<String> flags, List<String> files) {
        StringBuilder result = new StringBuilder();
        boolean isPrependingFileName = files.size() > 1;
        for (int i = 0; i < files.size(); i++) {
            List<String> lines = getLines(files.get(i));
            String chunkResult = getLineMatch(files.get(i), lines, pattern, flags.contains("-x"), isPrependingFileName, flags.contains("-n"), flags.contains("-l"), flags.contains("-i"), flags.contains("-v"));
            if (!chunkResult.isEmpty()) {
                result.append(chunkResult).append("\n");
            }
        }
        return result.toString().trim();   
    }
    
    private List<String> getLines(String fileName) {
        List<String> result = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                result.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getLineMatch(String fileName, List<String> lines, String pattern, boolean isSearchingLineMatch, boolean isPrependingFileName, boolean isPrependingLineNum, boolean isReturningFileName, boolean isCaseInsensitive, boolean isInverting) {
        return lines.stream()
            .filter(line -> (isSearchingLineMatch ? doesEntireLineMatch(line, pattern, isCaseInsensitive) : doesPartMatch(line, pattern, isCaseInsensitive)) == !isInverting)
            .map(line -> formatLine(fileName, lines, line, isPrependingFileName, isPrependingLineNum, isReturningFileName))
            .distinct()
            .collect(Collectors.joining("\n"));
    }

    private boolean doesEntireLineMatch(String line, String pattern, boolean isCaseInsensitive) {
        return isCaseInsensitive ? line.toLowerCase().equals(pattern.toLowerCase()) : line.equals(pattern);
    }

    private boolean doesPartMatch(String line, String pattern, boolean isCaseInsensitive) {
        return isCaseInsensitive ? line.toLowerCase().contains(pattern.toLowerCase()) : line.contains(pattern);
    }

    private String formatLine(String fileName, List<String> lines, String line, boolean isPrependingFileName, boolean isPrependingLineNum, boolean isReturningFileName) {
        if (isReturningFileName) {
            return fileName;
        }
        String prefix1 = isPrependingFileName ? fileName + ":" : "";
        String prefix2 = isPrependingLineNum ? (lines.indexOf(line) + 1) + ":" : "";
        return prefix1 + prefix2 + line;
    }
}