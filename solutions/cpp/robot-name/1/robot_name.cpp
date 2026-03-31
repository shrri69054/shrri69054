#include "robot_name.h"

namespace robot_name {

// TODO: add your solution here
robot::robot() {
    n.resize(5);
    reset();
}

std::string robot::name() const {
    return n;
}

void robot::reset() {
    do {
        int a = 'A' + (rand() % ('z' - 'a')); 
        int b = 'A' + (rand() % ('z' - 'a')); 
        int c = rand() % 1000;
        snprintf(n.data(), 6, "%c%c%03u", a, b, c);
    } while(existent.count(n)>0);
    existent.emplace(n);
}

}  // namespace robot_name
