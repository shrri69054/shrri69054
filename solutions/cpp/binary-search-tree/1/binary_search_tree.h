#pragma once

#include <memory>
namespace binary_search_tree {

// TODO: add your solution here
template <typename T>
class binary_tree {
  public:
    binary_tree(const T &data, binary_tree<T> *parent = nullptr) : data_(data), parent_(parent) {}
    const T &data() const { return data_; }
    const std::unique_ptr<binary_tree<T>> &left() const { return left_; }
    const std::unique_ptr<binary_tree<T>> &right() const { return right_; }
    void insert(const T &val)
    {
        if (val <= data_) {
            if (left_) {
                left_->insert(val);
            } else {
                left_ = std::make_unique<binary_tree<T>>(val, this);
            }
        } else {
            if (right_) {
                right_->insert(val);
            } else {
                right_ = std::make_unique<binary_tree<T>>(val, this);
            }
        }
    }
    struct iterator {
        binary_tree<T> *current_;
        iterator operator++()
        {
            if (!current_) return *this;
            if (current_->right_) {
                current_ = current_->right_.get();
                while (current_->left_) {
                    current_ = current_->left_.get();
                }
            } else {
                while (current_->parent_ && current_ == current_->parent_->right_.get()) {
                    current_ = current_->parent_;
                }
                current_ = current_->parent_;
            }
            return *this;
        }
        bool operator!=(const iterator &other) const { return current_ != other.current_; }
        const T &operator*() const { return current_->data_; }
    };
    iterator begin()
    {
        binary_tree<T> *ptr = this;
        while (ptr->left_) {
            ptr = ptr->left_.get();
        }
        return iterator{ptr};
    }
    iterator end() { return iterator{nullptr}; }

  private:
    T data_;
    binary_tree<T> *parent_;
    std::unique_ptr<binary_tree<T>> left_;
    std::unique_ptr<binary_tree<T>> right_;
};

} // namespace binary_search_tree
