#ifndef RNA_TRANSCRIPTION_H
#define RNA_TRANSCRIPTION_H

#include <string>
#include <string_view>

namespace rna_transcription
{
    // Converts a single DNA nucleotide to RNA
    char to_rna(char nucleotide);

    // Converts a DNA string to RNA string
    std::string to_rna(std::string_view dna);
}

#endif // RNA_TRANSCRIPTION_H