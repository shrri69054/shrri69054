#include "nucleotide_count.h"
#include <stdexcept>

namespace nucleotide_count {

std::map<char, int> count(const std::string& dna_chain) {
    std::map<char, int> nucleotide_map{{'A', 0}, {'C', 0}, {'G', 0}, {'T', 0}};

    for (char nucleotide : dna_chain) {
        switch (nucleotide) {
            case 'A': case 'C': case 'G': case 'T':
                nucleotide_map[nucleotide]++;
                break;
            default:
                throw std::invalid_argument("Invalid DNA string.");
        }
    }

    return nucleotide_map;
}

} // namespace nucleotide_count