package com.nikitavbv.assignments.algorithms.lab2;

import java.util.Optional;

public class MyHashTable<K, V> {

  private static final int DEFAULT_CAPACITY = 100;
  private static final boolean DEFAULT_RESIZABLE = true;

  private static final double LOAD_FACTOR_TO_RESIZE = 0.8;
  private static final int SCALE_FACTOR = 2;

  private K[] keyArr;
  private V[] valArr;

  private int size = 0;
  private boolean resizable;

  MyHashTable(int capacity, boolean resizable) {
    this.resizable = resizable;
    // noinspection unchecked
    this.keyArr = (K[]) new Object[capacity];
    // noinspection unchecked
    this.valArr = (V[]) new Object[capacity];
  }

  MyHashTable(boolean resizable) {
    this(DEFAULT_CAPACITY, resizable);
  }

  MyHashTable() {
    this(DEFAULT_RESIZABLE);
  }

  public void put(K key, V value) {
    if (size >= keyArr.length * LOAD_FACTOR_TO_RESIZE) {
      this.resize();
    }
    if (size == keyArr.length) throw new MyHashTableFullException();

    int pos = Math.abs(key.hashCode()) % keyArr.length;
    while (keyArr[pos] != null) {
      pos = (pos + 1) % keyArr.length;
    }

    keyArr[pos] = key;
    valArr[pos] = value;

    size++;
  }

  private void resize() {
    MyHashTable<K, V> temp = new MyHashTable<>();
    for (int i = 0; i < keyArr.length; i++) {
      if (keyArr[i] == null) {
        continue;
      }
      temp.put(keyArr[i], valArr[i]);
    }
    this.keyArr = temp.keyArr;
    this.valArr = temp.valArr;
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
