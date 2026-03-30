#ifndef NUCLEOTIDE_COUNT_H
#define NUCLEOTIDE_COUNT_H

#include <map>
#include <string>

namespace nucleotide_count {

// Counts nucleotides in a DNA string. Throws std::invalid_argument if any invalid character is found.
std::map<char, int> count(const std::string& dna_chain);

} // namespace nucleotide_count

#endif // NUCLEOTIDE_COUNT_H