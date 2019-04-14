package com.nikitavbv.assignments.algorithms.lab2;

import java.util.Optional;

public class MyHashTable<K, V> {

  private static final int DEFAULT_CAPACITY = 100;

  private K[] keyArr;
  private V[] valArr;

  private int size = 0;

  MyHashTable(int capacity) {
    // noinspection unchecked
    this.keyArr = (K[]) new Object[capacity];
    // noinspection unchecked
    this.valArr = (V[]) new Object[capacity];
  }

  MyHashTable() {
    this(DEFAULT_CAPACITY);
  }

  public void put(K key, V value) {
    if (size == keyArr.length) throw new MyHashTableFullException();

    int pos = Math.abs(key.hashCode()) % keyArr.length;
    while (keyArr[pos] != null) {
      pos = (pos + 1) % keyArr.length;
    }

    keyArr[pos] = key;
    valArr[pos] = value;

    size++;
  }

  public Optional<V> get(K key) {
    int pos = Math.abs(key.hashCode()) % keyArr.length;
    while (keyArr[pos] != null) {
      if (keyArr[pos] == key) {
        return Optional.of(valArr[pos]);
      }
      pos = (pos + 1) % keyArr.length;
    }

    return Optional.empty();
  }

  public int getSize() {
    return size;
  }

}
