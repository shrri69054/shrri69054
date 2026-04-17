#pragma once

#include <memory>
#include <string>

namespace troy {

struct artifact {
    artifact(std::string name) : name(name) {}
    std::string name;
};

struct power {
    power(std::string effect) : effect(effect) {}
    std::string effect;
};

struct human {
    std::unique_ptr<artifact> possession;
    std::shared_ptr<power> own_power;
    std::shared_ptr<power> influenced_by;
};

void give_new_artifact(human& person, std::string artifact_name);

void exchange_artifacts(std::unique_ptr<artifact>& item1,
                        std::unique_ptr<artifact>& item2);

void manifest_power(human& person, std::string power_name);

void use_power(human& influencer, human& influenced);

int power_intensity(human& caster);

}  // namespace troy