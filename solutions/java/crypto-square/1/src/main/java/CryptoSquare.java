class CryptoSquare {
    private final String normalizedText;

    CryptoSquare(String plaintext) {
        this.normalizedText = plaintext.replaceAll("[\\s\\p{Punct}]", "").toLowerCase();
    }

    String getCiphertext() {
        if (normalizedText.isEmpty()) {
            return "";
        }

        int length = normalizedText.length();
        int cols = (int) Math.ceil(Math.sqrt(length));
        int rows = (int) Math.ceil((double) length / cols);

        StringBuilder ciphertext = new StringBuilder();
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                int index = row * cols + col;
                if (index < length) {
                    ciphertext.append(normalizedText.charAt(index));
                } else {
                    ciphertext.append(' '); // Añadir espacio para completar
                }
            }
            if (col < cols - 1) {
                ciphertext.append(' '); // Espacio entre columnas
            }
        }
        return ciphertext.toString();
    }
}