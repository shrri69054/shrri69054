#include "grade_school.h"

#include <algorithm>

namespace grade_school {

const std::vector<std::string> empty_vector{};

const std::map<int, std::vector<std::string>>& school::roster() const {
    return _roster;
}

const std::vector<std::string>& school::grade(int student_grade) const {
    auto it = _roster.find(student_grade);
    if (it != _roster.end()) {
        return it->second;
    }
    return empty_vector;
}

void school::add(const std::string& student, int student_grade) {
    auto it = _roster.find(student_grade);
    if (it != _roster.end()) {
        auto& students = it->second;
        // Insert in sorted order
        students.emplace(std::lower_bound(students.begin(), students.end(), student), student);
    } else {
        _roster[student_grade] = {student};
    }
}

} // namespace grade_school