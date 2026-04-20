class PhoneNumber {

    private String number = "";
    
    PhoneNumber(String numberString) {
        this.number = numberString;
                number = number.replaceAll("[\\.\\+\\(\\)\\- ]", "");
        if (number.matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("letters not permitted");
        }
        if (number.matches(".*\\p{Punct}.*")) {
            throw new IllegalArgumentException("punctuations not permitted");
        }
        if (number.length() < 10) {
            throw new IllegalArgumentException("must not be fewer than 10 digits");
        }
        if (number.length() > 11) {
            throw new IllegalArgumentException("must not be greater than 11 digits");
        }
        if (number.length() == 11) {
            if (!number.startsWith("1")) {
                throw new IllegalArgumentException("11 digits must start with 1");
            } else {
                number = number.substring(1);
            }
        }
        if (number.startsWith("0")) {
            throw new IllegalArgumentException("area code cannot start with zero");
        }
        if (number.startsWith("1")) {
            throw new IllegalArgumentException("area code cannot start with one");
        }
        if (number.substring(3,4).equals("0")) {
            throw new IllegalArgumentException("exchange code cannot start with zero");
        }
        if (number.substring(3,4).equals("1")) {
            throw new IllegalArgumentException("exchange code cannot start with one");
        }
    }

    String getNumber() {
        return number;
    }
}