import java.util.*;
import java.util.concurrent.*;

class ParallelLetterFrequency {

    private final String[] texts;

    // Constructor to initialize with the texts
    ParallelLetterFrequency(String[] texts) {
        this.texts = texts;
    }

    // Method to count the frequency of letters in parallel
    Map<Character, Integer> countLetters() {
        // Using ForkJoinPool to parallelize the task
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new LetterFrequencyTask(texts, 0, texts.length));
    }

    // RecursiveTask to handle the letter frequency counting
    static class LetterFrequencyTask extends RecursiveTask<Map<Character, Integer>> {

        private static final int THRESHOLD = 10; // Threshold to split tasks
        private final String[] texts;
        private final int start;
        private final int end;

        LetterFrequencyTask(String[] texts, int start, int end) {
            this.texts = texts;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Map<Character, Integer> compute() {
            if (end - start <= THRESHOLD) {
                return countLettersSequentially();
            } else {
                int mid = (start + end) / 2;
                LetterFrequencyTask leftTask = new LetterFrequencyTask(texts, start, mid);
                LetterFrequencyTask rightTask = new LetterFrequencyTask(texts, mid, end);

                // Fork the subtasks
                leftTask.fork();
                rightTask.fork();

                // Join the results
                Map<Character, Integer> leftResult = leftTask.join();
                Map<Character, Integer> rightResult = rightTask.join();

                // Combine the results
                return mergeResults(leftResult, rightResult);
            }
        }

        // Count letters sequentially for a range of texts
        private Map<Character, Integer> countLettersSequentially() {
            Map<Character, Integer> frequencyMap = new HashMap<>();
            for (int i = start; i < end; i++) {
                for (char c : texts[i].toLowerCase().toCharArray()) { // Convert to lowercase
                    if (Character.isLetter(c)) {
                        frequencyMap.merge(c, 1, Integer::sum);
                    }
                }
            }
            return frequencyMap;
        }

        // Merge two frequency maps
        private Map<Character, Integer> mergeResults(Map<Character, Integer> map1, Map<Character, Integer> map2) {
            Map<Character, Integer> mergedMap = new HashMap<>(map1);
            map2.forEach((key, value) -> mergedMap.merge(key, value, Integer::sum));
            return mergedMap;
        }
    }

    public static void main(String[] args) {
        String[] texts = {
                "Wilhelmus van Nassouwe",
                "ben ik, van Duitsen bloed",
                "den vaderland getrouwe",
                "blijf ik tot in den dood."
        };

        ParallelLetterFrequency plf = new ParallelLetterFrequency(texts);
        Map<Character, Integer> frequencyMap = plf.countLetters();

        // Print the frequency of each letter
        frequencyMap.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}