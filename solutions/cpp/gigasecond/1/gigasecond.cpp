#include "gigasecond.h"

namespace gigasecond {

using ptime = boost::posix_time::ptime;

// TODO: add your solution here
ptime advance(ptime start) {
    return start + boost::posix_time::seconds(1000000000LL);
}

}  // namespace gigasecond





