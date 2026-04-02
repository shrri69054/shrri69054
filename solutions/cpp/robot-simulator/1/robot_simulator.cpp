#include "robot_simulator.h"

namespace robot_simulator {

// TODO: add your solution here
void Robot::turn_right() { bearing_ = static_cast<Bearing>((static_cast<int>(bearing_) + 1) % NUM_BEARING); }
void Robot::turn_left()
{
    bearing_ = static_cast<Bearing>((static_cast<int>(bearing_) - 1 + NUM_BEARING) % NUM_BEARING);
}
void Robot::advance()
{
    auto &[x, y] = pos_;
    switch (bearing_) {
    case Bearing::NORTH: {
        ++y;
    } break;
    case Bearing::EAST: {
        ++x;
    } break;
    case Bearing::SOUTH: {
        --y;
    } break;
    case Bearing::WEST: {
        --x;
    } break;
    }
}
void Robot::execute_sequence(const std::string &sequence)
{
    for (char order : sequence) {
        switch (order) {
        case 'A': {
            advance();
        } break;
        case 'R': {
            turn_right();
        } break;
        case 'L': {
            turn_left();
        } break;
        }
    }
}

} // namespace robot_simulator





