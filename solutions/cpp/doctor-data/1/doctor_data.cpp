#include "doctor_data.h"

namespace heaven {

Vessel::Vessel(std::string name, int generation,
               star_map::System current_system)
    : name(name), generation(generation), current_system(current_system) {}

Vessel Vessel::replicate(std::string new_name) {
  Vessel clone{new_name, generation + 1, current_system};
  return clone;
}

void Vessel::make_buster() { 
  ++busters; 
}

bool Vessel::shoot_buster() {
  if (busters) {
    --busters;
    return true;
  }
  return false;
}

std::string get_older_bob(const Vessel& vessel_1, const Vessel& vessel_2) {
  return vessel_1.generation <= vessel_2.generation 
         ? vessel_1.name 
         : vessel_2.name;
}

bool in_the_same_system(const Vessel& vessel_1, const Vessel& vessel_2) {
  return vessel_1.current_system == vessel_2.current_system;
}

}  // namespace heaven