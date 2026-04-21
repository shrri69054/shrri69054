#include "beer_song.h"

namespace beer_song {

// TODO: add your solution here
std::string verse(int beer)
{
    if (beer == 0) {
        return "No more bottles of beer on the wall, no more bottles of beer.\n"
               "Go to the store and buy some more, 99 bottles of beer on the wall.\n";
    }
    auto bottles = [](int n) { return n == 1 ? " bottle" : " bottles"; };
    std::string first_verse = std::to_string(beer) + bottles(beer) + " of beer on the wall, " + std::to_string(beer) +
                              bottles(beer) + " of beer.\n";
    std::string second_verse{};
    if (beer == 1) {
        second_verse = "Take it down and pass it around, no more bottles of beer on the wall.\n";
    } else {
        second_verse = "Take one down and pass it around, " + std::to_string(beer - 1) + bottles(beer - 1) +
                       " of beer on the wall.\n";
    }
    return first_verse + second_verse;
}

std::string sing(int begin, int end)
{
    std::string verses{verse(begin)};
    for (int beer = begin - 1; beer >= end; --beer) {
        verses += "\n" + verse(beer);
    }
    return verses;
}

} // namespace beer_song





