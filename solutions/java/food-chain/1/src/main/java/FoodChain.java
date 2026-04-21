public class FoodChain {

    public String verse(int n) {

        StringBuilder sb = new StringBuilder();



        // first line, I know ... + noun

        sb.append(String.format("I know an old lady who swallowed a %s.\n", verseNoun(n)));



        // second line, extra bit, in all verses except 1 and 8

        switch (n) {

            case 2:

                sb.append("It wriggled and jiggled and tickled inside her.\n");

                break;

            case 3:

                sb.append("How absurd to swallow a bird!\n");

                break;

            case 4:

                sb.append("Imagine that, to swallow a cat!\n");

                break;

            case 5:

                sb.append("What a hog, to swallow a dog!\n");

                break;

            case 6:

                sb.append("Just opened her throat and swallowed a goat!\n");

                break;

            case 7:

                sb.append("I don't know how she swallowed a cow!\n");

                break;

        }



        // cascade, line 3 onward, grows in all verses except 8

        switch (n) {

            case 8:

                sb.append("She's dead, of course!");

                break;

            case 7:

                sb.append("She swallowed the cow to catch the goat.\n");

            case 6:

                sb.append("She swallowed the goat to catch the dog.\n");

            case 5:

                sb.append("She swallowed the dog to catch the cat.\n");

            case 4:

                sb.append("She swallowed the cat to catch the bird.\n");

            case 3:

                sb.append("She swallowed the bird to catch the spider that wriggled and jiggled and tickled inside her.\n");

            case 2:

                sb.append("She swallowed the spider to catch the fly.\n");

            case 1:

                sb.append("I don't know why she swallowed the fly. Perhaps she'll die.");

        }



        return sb.toString();

    }



    public String verses(int start, int end) {

        StringBuilder sb = new StringBuilder();

        for (int i=start; i<end; i++) {

            sb.append(verse(i));

            sb.append("\n\n");

        }

        sb.append(verse(end));

        return sb.toString();

    }



    private String verseNoun(int n) {

        switch (n) {

            case 1:

                return "fly";

            case 2:

                return "spider";

            case 3:

                return "bird";

            case 4:

                return "cat";

            case 5:

                return "dog";

            case 6:

                return "goat";

            case 7:

                return "cow";

            case 8:

                return "horse";

            default:

                throw new IllegalArgumentException();

        }

    }



}