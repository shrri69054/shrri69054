class ArmstrongNumbers {

    boolean isArmstrongNumber(int numberToCheck) {
        // Convert number to string to easily get each digit and count the digits
        String numStr = Integer.toString(numberToCheck);
        int numDigits = numStr.length();
        int sum = 0;
        
        // Iterate through each digit
        for (char c : numStr.toCharArray()) {
            int digit = Character.getNumericValue(c);
            sum += Math.pow(digit, numDigits);  // Raise the digit to the power of the number of digits
        }

        // If the sum of the powered digits is equal to the original number, return true
        return sum == numberToCheck;
    }

}
