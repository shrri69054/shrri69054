#include "hamming.h"
#include <stdexcept>

namespace hamming {

std::size_t compute(std::string const& a, std::string const& b) {
	if(a.length() != b.length())
		throw std::domain_error("Length not equal");
	std::size_t counter = 0;
	for(std::size_t i = 0; i<a.length(); ++i){
		if(a[i] != b[i])
			++counter;
	}
	return counter;
}
}  // namespace hamming
