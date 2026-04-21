#pragma once

#include <string>
#include <vector>

namespace lasagna_master {

struct amount {
    int noodles;
    double sauce;
};

int preparationTime(std::vector<std::string>& layers, int layerPreparationTime = 2);
amount quantities(std::vector<std::string>& layers);

void addSecretIngredient(std::vector<std::string>& personal_list,
                         const std::vector<std::string>& friend_list);

void addSecretIngredient(std::vector<std::string>& personal_list,
                         const std::string& secret);

std::vector<double> scaleRecipe(const std::vector<double> amounts, int portions);

}  // namespace lasagna_master