class BottleSong {

    String recite(int startBottles, int takeDown) {
        String[] num = {
                "No", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
        };
        String out = "";
        for (int i = 0; i < takeDown; i++) {
            String begin = String.format(
                    "%s green bottle%s hanging on the wall,\n",
                    num[startBottles - i], startBottles - i != 1 ? "s" : "");
            out += begin + begin;
            out += "And if one green bottle should accidentally fall,\n";
            out += String.format(
                    "There'll be %s green bottle%s hanging on the wall.\n",
                    num[startBottles - i - 1].toLowerCase(), startBottles - i - 1 != 1 ? "s" : "");
            if (i != takeDown - 1)
                out += "\n";
        }
        return out;
    }
}
