#pragma once

#include <stdexcept>
#include <vector>
namespace circular_buffer {

// TODO: add your solution here
template <typename T>
class circular_buffer {
  public:
    explicit circular_buffer(int len) : m_buffer(len), m_len(len), m_count(0), m_first(0) {}

    T read();
    void write(T entry);
    void clear();
    void overwrite(T entry);

  private:
    std::vector<T> m_buffer;
    int m_len;
    int m_count;
    int m_first;
};

template class circular_buffer<int>;
template class circular_buffer<std::string>;

} // namespace circular_buffer
