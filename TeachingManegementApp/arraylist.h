/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef ARRAYLIST_H
#define ARRAYLIST_H
#pragma once
#include <algorithm>
#include <iostream>
#include <stdexcept>

template <typename T>
class ArrayList {
private:
    T* data;
    int capacity;
    int currentSize;

    /**
     * @brief resize automatically grow the \l{data} to 1.5 times its original size
     */
    void resize() {
        int newCapacity = capacity + (capacity >> 1);
        T* newData = new T[newCapacity];
        for (int i = 0; i < currentSize; i++) {
            newData[i] = data[i];
        }
        delete[] data;
        data = newData;
        capacity = newCapacity;
    }

public:
    /**
     * @brief Constructs an empty list with an initial capacity of ten.
     */
    ArrayList() : capacity(10), currentSize(0) {
        data = new T[capacity];
    }
    template <typename Compare>
    /**
     * @brief Sorts this list according to the order induced by the specified Comparator.
All elements in this list must be mutually comparable using the specified comparator (that is, c.compare(e1, e2) must not throw a ClassCastException for any elements e1 and e2 in the list).

If the specified comparator is null then all elements in this list must implement the Comparable interface and the elements' natural ordering should be used.

This list must be modifiable, but need not be resizable.
     * @param compare the Comparator used to compare list elements. A null value indicates that the elements' natural ordering should be used
     */
    void sort(Compare compare) {
        std::sort(data, data + currentSize, compare);
    }
    /*
    ~ArrayList() {
        delete[] data;
    }
   */
    /**
     * @brief Appends the specified element to the end of this list.
     * @param element element to be appended to this list
     */
    void add(const T& element) {
        if (currentSize >= capacity) {
            resize();
        }
        data[currentSize++] = element;
    }
    /**
     * @brief Returns the element at the specified position in this list.
     * @param index index of the element to return
     * @return the element at the specified position in this list
     */
    T get(int index) const {
        if (index < 0 || index >= currentSize) {
            throw std::out_of_range("Index out of bounds");
        }
        return data[index];
    }
    /**
     * @brief Removes the element at the specified position in this list. Shifts any subsequent elements to the left (subtracts one from their indices).
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     */
    T remove(int index) {
        if (index >= currentSize) {
            throw std::out_of_range("Index out of bounds");
        }
        T removedElement = data[index];
        for (int i = index; i < currentSize - 1; ++i) {
            data[i] = data[i + 1];
        }
        --currentSize;
        return removedElement;
    }
    /**
     * @brief Returns true if this list contains the specified element. More formally, returns true if and only if this list contains at least one element e such that (o==null ? e==null : o.equals(e)).
     * @param element element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    bool contains(const T element) {
        for (int i = 0; i < currentSize - 1; ++i) {
            if(data[i] == element) {
                return true;
            }
        }
        return false;
    }
    /**
     * @brief Returns the number of elements in this list.
     * @return the number of elements in this list
     */
    int size() {
        return currentSize;
    }
    /**
     * @brief Removes all of the elements from this list. The list will be empty after this call returns.
     */
    void clear() {
        currentSize = 0;
    }
    /**
     * @brief Returns a deep copy of this ArrayList instance. (The elements themselves areb copied.)
     * @param other 要拷贝的源 ArrayList
     * @return ArrayList& 返回当前对象的引用（支持链式赋值）
     */
    ArrayList<T>& operator=(const ArrayList<T>& other) {
        if (this == &other) {  // 检查自赋值
            return *this;
        }

        // 1. 释放当前对象的旧内存
        delete[] data;

        // 2. 分配新内存，并拷贝数据
        capacity = other.capacity;
        currentSize = other.currentSize;
        data = new T[capacity];
        for (int i = 0; i < currentSize; ++i) {
            data[i] = other.data[i];  // 调用 T 的赋值运算符（如果 T 是对象）
        }

        return *this;  // 返回当前对象的引用
    }
};
#endif // ARRAYLIST_H
