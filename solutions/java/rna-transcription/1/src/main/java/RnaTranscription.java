public class RnaTranscription {

    public String transcribe(String dnaStrand) {
        StringBuilder result = new StringBuilder();
        for (char c : dnaStrand.toCharArray()) {
            switch (c) {
                case 'A':
                    result.append('U');
                    break;
                case 'G':
                    result.append('C');
                    break;
                case 'C':
                    result.append('G');
                    break;
                case 'T':
                    result.append('A');
                    break;
            }
        }
        return result.toString();
    }
}