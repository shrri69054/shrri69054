#include "circular_buffer.h"

namespace circular_buffer {

// TODO: add your solution here
template <typename T>
T circular_buffer<T>::read()
{
    if (m_count == 0) throw std::domain_error("cannot read empty buffer");
    T result = m_buffer[m_first];
    m_first = (m_first + 1) % m_len;
    --m_count;
    return result;
}

template <typename T>
void circular_buffer<T>::write(T entry)
{
    if (m_count >= m_len) throw std::domain_error("buffer is full");
    m_buffer[(m_first + m_count) % m_len] = entry;
    ++m_count;
}

template <typename T>
void circular_buffer<T>::clear()
{
    m_count = 0;
}

template <typename T>
void circular_buffer<T>::overwrite(T entry)
{
    m_buffer[(m_first + m_count) % m_len] = entry;
    if (m_count >= m_len) {
        m_first = (m_first + 1) % m_len;
    } else {
        ++m_count;
    }
}

} // namespace circular_buffer





