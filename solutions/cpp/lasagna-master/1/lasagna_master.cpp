#include "lasagna_master.h"

namespace lasagna_master {

int preparationTime(std::vector<std::string>& layers, int layerPreparationTime) {
    return layers.size() * layerPreparationTime;
}

amount quantities(std::vector<std::string>& layers) {
    int noodles = 0;
    double sauce = 0.0;

    for (const std::string& layer : layers) {
        if (layer == "noodles") {
            noodles += 50;
        } else if (layer == "sauce") {
            sauce += 0.2;
        }
    }

    return {noodles, sauce};
}

void addSecretIngredient(std::vector<std::string>& personal_list,
                         const std::vector<std::string>& friend_list) {
    personal_list.back() = friend_list.back();
}

void addSecretIngredient(std::vector<std::string>& personal_list,
                         const std::string& secret) {
    personal_list.back() = secret;
}

std::vector<double> scaleRecipe(const std::vector<double> amounts, int portions) {
    std::vector<double> newAmounts;

    for (double amount : amounts) {
        newAmounts.push_back((amount / 2) * portions);
    }

    return newAmounts;
}

}  // namespace lasagna_master