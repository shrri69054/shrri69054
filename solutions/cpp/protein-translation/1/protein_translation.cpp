#include "protein_translation.h"
#include <unordered_map>

namespace protein_translation {

std::unordered_map<std::string, std::string> codons{
    {"AUG","Methionine"},
    {"UUU","Phenylalanine"},{"UUC","Phenylalanine"},
    {"UUA","Leucine"},{"UUG","Leucine"},
    {"UCU","Serine"},{"UCC","Serine"},{"UCA","Serine"},{"UCG","Serine"},
    {"UAU","Tyrosine"},{"UAC","Tyrosine"},
    {"UGU","Cysteine"},{"UGC","Cysteine"},
    {"UGG","Tryptophan"},
    {"UAA","STOP"},{"UAG","STOP"},{"UGA","STOP"}
};

std::vector<std::string> proteins(std::string sequence) {
    std::vector<std::string> result;

    for (std::size_t i = 0; i < sequence.size(); i += 3) {
        std::string frame = sequence.substr(i, 3);

        if (codons[frame] == "STOP") {
            return result;
        }

        result.emplace_back(codons[frame]);
    }

    return result;
}

}  // namespace protein_translation