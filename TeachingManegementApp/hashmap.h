/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef HASHMAP_H
#define HASHMAP_H

#include <functional>
#include <stdexcept>

template <typename K, typename V>
/**
 * @brief Hash table based implementation of the Map interface. This implementation provides all of the optional map operations, and permits null values and the null key. (The HashMap class is roughly equivalent to Hashtable, except that it is unsynchronized and permits nulls.) This class makes no guarantees as to the order of the map; in particular, it does not guarantee that the order will remain constant over time.
 */
class HashMap {
private:
    static const int DEFAULT_INITIAL_CAPACITY = 16;
    static const float DEFAULT_LOAD_FACTOR;

    class Entry {
    public:
        K key;
        V value;
        Entry* next;
        int hash;

        Entry(int h, const K& k, const V& v, Entry* n);
    };

    Entry** table;
    int size0;
    int capacity;
    float loadFactor;
    int threshold;

    void resize(int newCapacity);
    int hash(const K& key) const;
    int indexFor(int h, int length) const;
    void addEntry(int hash, const K& key, const V& value, int bucketIndex);

public:
    HashMap();
    HashMap(int initialCapacity, float loadFactor = DEFAULT_LOAD_FACTOR);
    ~HashMap();

    V put(const K& key, const V& value);
    V& get(const K& key) const;
    bool containsKey(const K& key) const;
    V& remove(const K& key);
    void clear();
    int size() const;
    bool isEmpty() const;

    class Iterator {
    private:
        const HashMap<K, V>& map;
        int index;
        Entry* current;

        void findNext();

    public:
        Iterator(const HashMap<K, V>& m);
        bool hasNext() const;
        Entry& next();
    };

    Iterator iterator() const;
};

template <typename K, typename V>
const float HashMap<K, V>::DEFAULT_LOAD_FACTOR = 0.75f;

template <typename K, typename V>
HashMap<K, V>::Entry::Entry(int h, const K& k, const V& v, Entry* n)
    : hash(h), key(k), value(v), next(n) {}

template <typename K, typename V>
HashMap<K, V>::HashMap() : HashMap(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR) {}

template <typename K, typename V>
HashMap<K, V>::HashMap(int initialCapacity, float loadFactor) {
    if (initialCapacity < 0) {
        throw std::invalid_argument("Illegal initial capacity");
        //throw new IllegalArgumentException();
    }
    if (loadFactor <= 0) {
        throw std::invalid_argument("Illegal load factor");
        //throw new IllegalArgumentException();
    }

    capacity = 1;
    while (capacity < initialCapacity) {
        capacity <<= 1;
    }

    this->loadFactor = loadFactor;
    threshold = (int)(capacity * loadFactor);
    table = new Entry*[capacity]();
    size0 = 0;
}

template <typename K, typename V>
HashMap<K, V>::~HashMap() {
    clear();
    delete[] table;
}

template <typename K, typename V>
void HashMap<K, V>::resize(int newCapacity) {
    Entry** newTable = new Entry*[newCapacity]();

    for (int i = 0; i < capacity; ++i) {
        Entry* e = table[i];
        while (e != nullptr) {
            Entry* next = e->next;
            int index = indexFor(e->hash, newCapacity);
            e->next = newTable[index];
            newTable[index] = e;
            e = next;
        }
    }

    delete[] table;
    table = newTable;
    capacity = newCapacity;
    threshold = (int)(capacity * loadFactor);
}

template <typename K, typename V>
/**
 * @brief Attempts to compute a mapping for the specified key and its current mapped value (or null if there is no current mapping). For example, to either create or append a String msg to a value mapping:
 * @param key key with which the specified value is to be associated
 * @return the new value associated with the specified key, or null if none
 */
int HashMap<K, V>::hash(const K& key) const {
    return std::hash<K>()(key);
}

template <typename K, typename V>
int HashMap<K, V>::indexFor(int h, int length) const {
    return h & (length - 1);
}

template <typename K, typename V>
void HashMap<K, V>::addEntry(int hash, const K& key, const V& value, int bucketIndex) {
    Entry* e = table[bucketIndex];
    table[bucketIndex] = new Entry(hash, key, value, e);
    if (size0++ >= threshold) {
        resize(2 * capacity);
    }
}

template <typename K, typename V>
/**
 * @brief Associates the specified value with the specified key in this map. If the map previously contained a mapping for the key, the old value is replaced.
 * @param key key with which the specified value is to be associated
 * @param value value to be associated with the specified key
 * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
 */
V HashMap<K, V>::put(const K& key, const V& value) {
    int h = hash(key);
    int i = indexFor(h, capacity);

    for (Entry* e = table[i]; e != nullptr; e = e->next) {
        if (e->hash == h && e->key == key) {
            V oldValue = e->value;
            e->value = value;
            return oldValue;
        }
    }

    addEntry(h, key, value, i);
    return value;
}

template <typename K, typename V>
/**
 * @brief Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
More formally, if this map contains a mapping from a key k to a value v such that (key==null ? k==null : key.equals(k)), then this method returns v; otherwise it returns null. (There can be at most one such mapping.)

A return value of null does not necessarily indicate that the map contains no mapping for the key; it's also possible that the map explicitly maps the key to null. The containsKey operation may be used to distinguish these two cases.
 * @param key the key whose associated value is to be returned
 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
 */
V& HashMap<K, V>::get(const K& key) const {
    int h = hash(key);
    for (Entry* e = table[indexFor(h, capacity)]; e != nullptr; e = e->next) {
        if (e->hash == h && e->key == key) {
            return e->value;
        }
    }
    throw std::out_of_range("Key not found");
}

template <typename K, typename V>
/**
 * @brief Returns true if this map contains a mapping for the specified key.
 * @param key The key whose presence in this map is to be tested
 * @return true if this map contains a mapping for the specified key.
 */
bool HashMap<K, V>::containsKey(const K& key) const {
    int h = hash(key);
    int i = indexFor(h, capacity);
    for (Entry* e = table[i]; e != nullptr; e = e->next) {
        if (e->hash == h && e->key == key) {
            return true;
        }
    }
    return false;
}

template <typename K, typename V>
/**
 * @brief Removes the mapping for the specified key from this map if present.
 * @param key key whose mapping is to be removed from the map
 * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
 */
V& HashMap<K, V>::remove(const K& key) {
    int h = hash(key);
    int i = indexFor(h, capacity);
    Entry* prev = nullptr;
    Entry* e = table[i];

    while (e != nullptr) {
        if (e->hash == h && e->key == key) {
            if (prev == nullptr) {
                table[i] = e->next;
            } else {
                prev->next = e->next;
            }
            V oldValue = e->value;
            delete e;
            size0--;
            return oldValue;
        }
        prev = e;
        e = e->next;
    }

    throw std::out_of_range("Key not found");
}

template <typename K, typename V>
/**
 * @brief Removes all of the mappings from this map. The map will be empty after this call returns.
 */
void HashMap<K, V>::clear() {
    for (int i = 0; i < capacity; ++i) {
        Entry* e = table[i];
        while (e != nullptr) {
            Entry* next = e->next;
            delete e;
            e = next;
        }
        table[i] = nullptr;
    }
    size0 = 0;
}

template <typename K, typename V>
/**
 * @brief Returns the number of key-value mappings in this map.
 * @return the number of key-value mappings in this map
 */
int HashMap<K, V>::size() const {
    return size0;
}

template <typename K, typename V>
/**
 * @brief Returns true if this map contains no key-value mappings.
 * @return true if this map contains no key-value mappings
 */
bool HashMap<K, V>::isEmpty() const {
    return size0 == 0;
}

// Iterator implementation
template <typename K, typename V>
HashMap<K, V>::Iterator::Iterator(const HashMap<K, V>& m)
    : map(m), index(0), current(map.table[0]) {
    if (current == nullptr) {
        findNext();
    }
}

template <typename K, typename V>
void HashMap<K, V>::Iterator::findNext() {
    while (current == nullptr && index < map.capacity - 1) {
        index++;
        current = map.table[index];
    }
}

template <typename K, typename V>
/**
 * @brief Returns true if the iteration has more elements. (In other words, returns true if next() would return an element rather than throwing an exception.)
 * @return true if the iteration has more elements
 */
bool HashMap<K, V>::Iterator::hasNext() const {
    return current != nullptr;
}

template <typename K, typename V>
/**
 * @brief Returns the next element in the iteration.
 * @return the next element in the iteration
 */
typename HashMap<K, V>::Entry& HashMap<K, V>::Iterator::next() {
    if (!hasNext()) {
        throw std::out_of_range("No more elements");
    }
    Entry* ret = current;
    current = current->next;
    if (current == nullptr) {
        findNext();
    }
    return *ret;
}

template <typename K, typename V>
typename HashMap<K, V>::Iterator HashMap<K, V>::iterator() const {
    return Iterator(*this);
}

#endif // HASHMAP_H
