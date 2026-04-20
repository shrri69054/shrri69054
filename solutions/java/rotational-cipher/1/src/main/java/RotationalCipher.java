class RotationalCipher {

    private int shiftKey;

    RotationalCipher(int shiftKey) {
        this.shiftKey = shiftKey;
    }

    int rot(int ch) {
        if (!Character.isLetter(ch)) {
            return ch;
        }
        char first = Character.isUpperCase(ch) ? 'A' : 'a';
        return first + (ch + shiftKey - first) % 26;
    }

    String rotate(String data) {
        return data.codePoints()
                .mapToObj(this::rot)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}