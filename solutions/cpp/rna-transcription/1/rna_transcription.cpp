#include "rna_transcription.h"
#include <stdexcept>

namespace rna_transcription
{
    char to_rna(char nucleotide)
    {
        if (nucleotide == 'G') return 'C';
        if (nucleotide == 'C') return 'G';
        if (nucleotide == 'T') return 'A';
        if (nucleotide == 'A') return 'U';
        throw std::invalid_argument("Unknown nucleotide");
    }

    std::string to_rna(std::string_view dna)
    {
        std::string rna;
        rna.reserve(dna.size()); // Optional: improves performance
        for (auto nucleotide : dna)
            rna += to_rna(nucleotide);
        return rna;
    }
}