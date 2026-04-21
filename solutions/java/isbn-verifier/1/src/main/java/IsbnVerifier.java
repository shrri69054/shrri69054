class IsbnVerifier {

    private static final int X_CHAR_VAL = 10;
    private String isbn;

    boolean isValid(String stringToVerify) {
        this.isbn = stringToVerify.replaceAll("-", "");
        String[] isbnArr = isbn.split("");
        boolean isValid = true;

        if (isbnArr.length != 10) {
            isValid = false;
        }
        else {
            int curMultiple = 10;
            int formulaSum = 0;

            for (int i = 0; i < isbnArr.length; i++) {
                String curNumStr = isbnArr[i];
                int convertedStr = getInt(curNumStr, i);
                if (convertedStr == -1) {
                    isValid = false;
                    break;
                }
                formulaSum += convertedStr * curMultiple;
                curMultiple--;
            }

            if (formulaSum % 11 != 0) {
                isValid = false;
            }
        }

        return isValid;
    }

    private int getInt(String numChar, int numIdx) {
        int convertedNum = 0;

        if (numIdx == 9 && numChar.equals("X")) {
            convertedNum = 10;
        }
        else {
            try {
                convertedNum = Integer.parseInt(numChar);
            }
            catch (NumberFormatException e) {
                convertedNum = -1;
            }
        }

        return convertedNum;
    }
}