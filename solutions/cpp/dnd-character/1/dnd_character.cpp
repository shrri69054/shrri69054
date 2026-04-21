




#include "dnd_character.h"

#include <algorithm>
#include <array>
#include <random>

namespace dnd_character {
int
modifier(int score)
{
	int x = score - 10;
	if (x < 0) {
		x -= 1;
	}
	return x / 2;
}

int
roll(std::mt19937 gen)
{
	std::uniform_int_distribution<> die(1, 6);
	return die(gen);
}

int
ability()
{
	std::random_device rd;
	std::mt19937 g(rd());
	std::array<int, 4> r {roll(g), roll(g), roll(g), roll(g)};
	int least = *std::min_element(r.begin(), r.end());
	return std::accumulate(r.begin(), r.end(), -least);
}
}  // namespace dnd_character
