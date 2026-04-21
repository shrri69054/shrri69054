#pragma once

#include <string>
#include <utility>
namespace robot_simulator {

// TODO: add your solution here
enum class Bearing {
    NORTH,
    EAST,
    SOUTH,
    WEST,
};
static constexpr int NUM_BEARING = 4;

class Robot {
  public:
    Robot(std::pair<int, int> pos = {0, 0}, Bearing bearing = Bearing::NORTH) : pos_(pos), bearing_(bearing) {}
    const std::pair<int, int> &get_position() const { return pos_; }
    const Bearing &get_bearing() const { return bearing_; }

    void turn_right();
    void turn_left();
    void advance();
    void execute_sequence(const std::string &);

  private:
    std::pair<int, int> pos_;
    Bearing bearing_;
};

} // namespace robot_simulator
