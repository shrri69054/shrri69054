import java.util.regex.Pattern;

class RunLengthEncoding {

    String encode(String data) {
        return Pattern.compile("(.)\\1+")
                .matcher(data)
                .replaceAll(matchResult ->
                        matchResult.group().length() + matchResult.group(1)
                );
    }

    String decode(String data) {
        return Pattern.compile("(\\d+)(.)")
                .matcher(data)
                .replaceAll(matchResult ->
                        matchResult.group(2).repeat(Integer.parseInt(matchResult.group(1)))
                );
    }

}