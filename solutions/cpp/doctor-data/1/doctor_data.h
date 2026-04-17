#pragma once

#include <string>

namespace star_map {
enum class System {
  BetaHydri,
  Sol,
  EpsilonEridani,
  AlphaCentauri,
  DeltaEridani,
  Omicron2Eridani
};
}

namespace heaven {

struct Vessel {
  Vessel(std::string name, int generation,
         star_map::System current_system = star_map::System::Sol);

  Vessel replicate(std::string new_name);
  void make_buster();
  bool shoot_buster();

  std::string name{};
  int generation{};
  star_map::System current_system{};
  int busters{};
};

std::string get_older_bob(const Vessel& vessel_1, const Vessel& vessel_2);
bool in_the_same_system(const Vessel& vessel_1, const Vessel& vessel_2);

}  // namespace heaven