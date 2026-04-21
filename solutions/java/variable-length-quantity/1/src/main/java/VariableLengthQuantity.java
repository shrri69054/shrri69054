import java.util.ArrayList;
import java.util.List;

class VariableLengthQuantity {

    List<String> encode(List<Long> numbers) {
        List<String> ret = new ArrayList<>();

        for(int i = 0; i < numbers.size(); i++) {
            List<String> set = encode(numbers.get(i));
            for(String s : set)
                ret.add(s);
        }

        return ret;
    }

    List<String> encode(Long number) {
        List<String> ret = new ArrayList<>();

        char[] bits = Long.toBinaryString(number).toCharArray();
        String str = "";
        boolean isFirst = true;
        int counter = 0;
        for(int i = bits.length - 1; i >= 0; i--) {
            counter++;
            str = bits[i] + str;

            if(i == 0) {
                while(counter != 7) {
                    str = "0" + str;
                    counter++;
                }
            }

            if(counter == 7) {
                str = isFirst ? "0" + str : "1" + str;
                str = "0x" + Long.toHexString(Long.parseUnsignedLong(str, 2));
                ret.add(0, str);
                str = "";
                counter = 0;
                isFirst = false;
            }
        }

        return ret;
    }

    List<String> decode(List<Long> bytes) {
        List<String> ret = new ArrayList<>();

        List<Long> group = new ArrayList<>();
        boolean groupValid = false;
        for(int i = 0; i < bytes.size(); i++) {
            groupValid = false;
            group.add(bytes.get(i));
            if(bytes.get(i) < 128L) {
                groupValid = true;
                ret.add(decodeGroup(group));
                group = new ArrayList<>();
            }
        }
        if(groupValid)
            return ret;

        throw new IllegalArgumentException("Invalid variable-length quantity encoding");
    }

    String decodeGroup(List<Long> bytes) {
        String ret = "0x";

        String binary = "";
        for(int i = 0; i < bytes.size(); i++) {
            String num = Long.toBinaryString(bytes.get(i));
            if(num.length() == 8)
                num = num.substring(1);
            while(num.length() != 7)
                num += "0";
            binary += num;
        }

        return ret + Long.toHexString(Long.parseUnsignedLong(binary, 2));
    }
}