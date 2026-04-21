#pragma once

#include <vector>
#include <cstddef>

namespace list_ops {

template <typename T>
void append(std::vector<T> &l, const std::vector<T> &r)
{
    l.insert(l.end(), r.begin(), r.end());
}

template <typename T>
std::vector<T> concat(const std::vector<std::vector<T>> &l)
{
    std::vector<T> res;
    for (const auto &a : l) {
        for (const auto &b : a) {
            res.push_back(b);
        }
    }
    return res;
}

template <typename T, typename P>
std::vector<T> filter(const std::vector<T> &v, P pred)
{
    std::vector<T> res;
    for (const auto &a : v) {
        if (pred(a))
            res.push_back(std::move(a));
    }
    return res;
}

template <typename T>
size_t length(const std::vector<T> &v)
{
    return v.size();
}

template <typename T, typename P>
auto map(const std::vector<T> &v, P pred)
{
    using R = std::invoke_result_t<P, T>;
    std::vector<R> res;
    for (const auto &a : v) {
        res.push_back(pred(a));
    }
    return res;
}

template <typename T1, typename T2, typename F>
auto foldl(const std::vector<T1> &v, T2 a, F func)
{
    for (const auto &x : v) {
        a = func(a, x);
    }
    return a;
}

template <typename T1, typename T2, typename F>
auto foldr(const std::vector<T1> &v, T2 a, F func)
{
    for (auto it = v.rbegin(); it != v.rend(); ++it) {
        a = func(a, *it);
    }
    return a;
}

template <typename T>
std::vector<T> reverse(const std::vector<T> &v)
{
    std::vector<T> res;
    for (auto it = v.rbegin(); it != v.rend(); ++it) {
        res.push_back(*it);
    }
    return res;
}

}  // namespace list_ops
