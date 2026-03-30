#include "matching_brackets.h"
namespace matching_brackets {
bool check(std::string str){
    std::vector<char> brackets;
    for (char c:str){
        if (c=='(' || c=='{' || c=='[') {
            brackets.push_back(c);
        } else if (c==')' || c=='}' || c==']'){
            if (brackets.empty()){return false;}
            
            char top = brackets.back();
            brackets.pop_back();
            if ((c == ')' && top != '(') ||
                (c == '}' && top != '{') ||
                (c == ']' && top != '[')) {
                return false;
            }
        }
    }
    return brackets.empty();
}
}  // namespace matching_brackets
