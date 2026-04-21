import java.util.ArrayList;
import java.util.List;

public class LanguageList {
    private final List<String> languages = new ArrayList<>();

    // 1. Check if the list is empty
    public boolean isEmpty() {
        return languages.isEmpty(); // Returns true if the list is empty
    }

    // 2. Add a new language to the list
    public void addLanguage(String language) {
        languages.add(language); // Adds a language to the list
    }

    // 3. Remove a specific language from the list
    public void removeLanguage(String language) {
        languages.remove(language); // Removes the first occurrence of the language
    }

    // 4. Return the first language in the list
    public String firstLanguage() {
        if (languages.isEmpty()) {
            return null; // Return null if the list is empty
        }
        return languages.get(0); // Get the first language
    }

    // 5. Return the number of languages in the list
    public int count() {
        return languages.size(); // Returns the size of the list
    }

    // 6. Check if the list contains a specific language
    public boolean containsLanguage(String language) {
        return languages.contains(language); // Checks if the language is in the list
    }

    // 7. Check if the list contains "Java" or "Kotlin"
    public boolean isExciting() {
        return languages.contains("Java") || languages.contains("Kotlin"); // Returns true if the list contains Java or Kotlin
    }
}
