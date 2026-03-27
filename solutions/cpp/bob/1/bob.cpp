#include "bob.h"
#include <cctype>

namespace bob {

std::string normalize(const std::string& input)
{
    std::string output{""};
    for (const auto& ch : input)
    {
        if (std::isalnum(ch) || ch == '?') output += ch;
    }
    return output;
}

bool is_yelling(const std::string& input)
{
    for (const auto& ch : input)
    {
        if (std::isalpha(ch) && !std::isupper(ch)) return false;
    }
    return true;
}

bool is_asking(const std::string& input)
{
    return input[input.length() - 1] == '?';
}

bool is_words(const std::string& input)
{
    for (const auto& ch : input)
    {
        if (std::isalpha(ch)) return true;
    }
    return false;
}

std::string hey(const std::string& input)
{
    auto normalized = normalize(input);
    if (normalized.length() == 0) return "Fine. Be that way!";
    auto has_words = is_words(normalized);
    auto is_loud = has_words && is_yelling(normalized);
    auto is_question = is_asking(normalized);
    if (is_loud)
    {
        if (is_question) return "Calm down, I know what I'm doing!";
        return "Whoa, chill out!";
    }
    if (is_question) return "Sure.";
    return "Whatever.";
}

}  // namespace bob