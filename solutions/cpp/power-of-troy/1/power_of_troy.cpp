#include "power_of_troy.h"

#include <utility>  // for std::swap

namespace troy {

void give_new_artifact(human& person, std::string artifact_name) {
    person.possession = std::make_unique<artifact>(artifact_name);
}

void exchange_artifacts(std::unique_ptr<artifact>& item1,
                        std::unique_ptr<artifact>& item2) {
    std::swap(item1, item2);
}

void manifest_power(human& person, std::string power_name) {
    person.own_power = std::make_shared<power>(power_name);
}

void use_power(human& influencer, human& influenced) {
    influenced.influenced_by = influencer.own_power;
}

int power_intensity(human& caster) {
    if (!caster.own_power) {
        return 0;
    }
    return caster.own_power.use_count();
}

}  // namespace troy