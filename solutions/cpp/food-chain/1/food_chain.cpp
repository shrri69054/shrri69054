#include "food_chain.h"
#include <string>
#include <string_view>
#include <array>
namespace food_chain {
    namespace {
        constexpr std::string_view animals[] = {
            "", "fly", "spider", "bird", "cat", "dog", "goat", "cow", "horse"
        };
        constexpr std::string_view second[] = {
          "", "", "It wriggled and jiggled and tickled inside her.\n",
          "How absurd to swallow a bird!\n", "Imagine that, to swallow a cat!\n", 
          "What a hog, to swallow a dog!\n", "Just opened her throat and swallowed a goat!\n",
          "I don't know how she swallowed a cow!\n"
        };
        constexpr std::string_view ending = 
            "I don't know why she swallowed the fly. Perhaps she'll die.\n";
        std::string build_beginning(int verse) {
            return "I know an old lady who swallowed a " + std::string(animals[verse]) + ".\n";
        }
        std::string build_verse(int number) {
            std::string verse;
            verse += build_beginning(number);
            if (animals[number] == "horse") { // end the song immediately
                verse += "She's dead, of course!\n";
                return verse;
            }
            // add the second part
            verse += second[number];
            // now add "she swallowed the n to catch the n-1" part ONLY if you're not the fly (n = 1)
            if (number > 1) {
                for (int i = number; i > 1; --i) {
                    verse += "She swallowed the " + std::string(animals[i]) + " to catch the " + std::string(animals[i - 1]);
                    if (animals[i - 1] == "spider") { // for the bird
                        verse += " that wriggled and jiggled and tickled inside her";
                    }
                    verse += ".\n";
                }
            } 
            // add the ending for the fly
            verse += ending;
            return verse;
        } // end of build_verse()
    } // anonymous namespace
    std::string verse(int verse) {
        return build_verse(verse);
    }
    std::string sing() {
        return verses(1, 8);
    }
    std::string verses(int start, int end) {
        std::string result;
        for (int i = start; i <= end; ++i) { // start and end inclusive
            result += verse(i); // call the verse() method
            // add a blank line in between verses
            result += "\n";
        }
        return result;
    }
}  // namespace food_chain





