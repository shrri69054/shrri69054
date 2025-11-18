import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class LuhnValidator {

    static final int MINIMUM_DIGITS = 2;
    static final int LUHN_MOD_10 = 10;

    boolean isValid(String candidate) {

        // regex for non-digits and non-spaces
        Matcher candidateRegex = Pattern.compile("[^\\d\\s]").matcher(candidate);

        // if less than 2 digits or any non-digits, return false
        if (candidateRegex.find()) {

            return false;

        } else {

            // reverses string and filters out white space
            List<Integer> digitList = new StringBuilder(candidate)
                    .reverse() // reverses string
                    .chars() // converts to stream of char
                    .filter(Character::isDigit) // filters out white space
                    .mapToObj(d -> Character.getNumericValue(d)) // maps char to int
                    .collect(Collectors.toList());

            // checks after space filter for minimum digits
            if (digitList.size() >= MINIMUM_DIGITS) {

                int checkSum = 0;
                for (int d = 0; d < digitList.size(); d++) {

                    // adds digit then increments index to prepare for doubling
                    checkSum += digitList.get(d);

                    // checks if 
                    if (d + 1 < digitList.size()) {
                        d++;
                        // subtracts 9 if doubling yields more than 9
                        checkSum += (digitList.get(d) * 2 > 9)
                                ? (digitList.get(d) * 2 - 9)
                                : (digitList.get(d) * 2);
                    }
                    
                }
                
                // final check for Luhn Algorithm
                return checkSum % LUHN_MOD_10 == 0;
                
            } else {
                return false;
            }
        }
    }
}