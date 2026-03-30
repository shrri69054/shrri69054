#ifndef GRADE_SCHOOL_H
#define GRADE_SCHOOL_H

#include <map>
#include <vector>
#include <string>

namespace grade_school {

class school {
private:
    std::map<int, std::vector<std::string>> _roster{};

public:
    const std::map<int, std::vector<std::string>>& roster() const;
    void add(const std::string& student, int student_grade);
    const std::vector<std::string>& grade(int student_grade) const;
};

} // namespace grade_school

#endif // GRADE_SCHOOL_H