




#pragma once

#include <string>
#include <set>

namespace robot_name {

// TODO: add your solution here
class robot {
private:
    inline static std::set<std::string> existent = {};
    std::string n{};
public:
    robot();
    std::string name() const;
    void reset();
};

}  // namespace robot_name
