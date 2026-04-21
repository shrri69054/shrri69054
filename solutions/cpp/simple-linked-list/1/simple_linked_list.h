#ifndef SIMPLE_LINKED_LIST_H
#define SIMPLE_LINKED_LIST_H

#include <cstddef>

namespace simple_linked_list {

class List {
public:
    List() = default;
    ~List();

    List(const List&) = delete;
    List& operator=(const List&) = delete;
    List(List&&) = delete;
    List& operator=(List&&) = delete;

    size_t size() const;
    void push(int entry);
    int pop();
    void reverse();

private:
    struct Element {
        Element(int data) : data{data} {}
        int data{};
        Element* next{nullptr};
    };

    Element* head{nullptr};
    size_t current_size{0};
};

}  // namespace simple_linked_list

#endif