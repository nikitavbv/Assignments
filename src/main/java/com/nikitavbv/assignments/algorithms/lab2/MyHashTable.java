package com.nikitavbv.assignments.algorithms.lab2;

import java.util.Arrays;
import java.util.Optional;

public class MyHashTable<K, V> {

  private static final int DEFAULT_CAPACITY = 100;
  private static final boolean DEFAULT_RESIZABLE = true;

  private static final double LOAD_FACTOR_TO_RESIZE = 0.8;
  private static final int SCALE_FACTOR = 2;

  private K[] keyArr;
  private V[] valArr;
  private boolean[] removed;

  private int size = 0;
  private boolean resizable;

  MyHashTable(int capacity, boolean resizable) {
    this.resizable = resizable;
    // noinspection unchecked
    this.keyArr = (K[]) new Object[capacity];
    // noinspection unchecked
    this.valArr = (V[]) new Object[capacity];
    this.removed = new boolean[capacity];
    Arrays.fill(this.removed, false);
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
    while (keyArr[pos] != null && !this.removed[pos]) {
      pos = (pos + 1) % keyArr.length;
    }

    keyArr[pos] = key;
    valArr[pos] = value;
    removed[pos] = false;

    size++;
  }

  private void resize() {
    MyHashTable<K, V> temp = new MyHashTable<>();
    for (int i = 0; i < keyArr.length; i++) {
      if (keyArr[i] == null || this.removed[i]) {
        continue;
      }
      temp.put(keyArr[i], valArr[i]);
    }
    this.keyArr = temp.keyArr;
    this.valArr = temp.valArr;
    this.removed = temp.removed;
  }

  public Optional<V> get(K key) {
    int pos = Math.abs(key.hashCode()) % keyArr.length;
    while (keyArr[pos] != null) {
      if (keyArr[pos] == key && !this.removed[pos]) {
        return Optional.of(valArr[pos]);
      }
      pos = (pos + 1) % keyArr.length;
    }

    return Optional.empty();
  }

  public boolean containsKey(K key) {
    return get(key).isPresent();
  }

  public V getOrDefault(K key, V defaultValue) {
    return get(key).orElse(defaultValue);
  }

  public void remove(K key) {
    int pos = Math.abs(key.hashCode()) % keyArr.length;
    while (keyArr[pos] != null) {
      if (keyArr[pos] == key && !this.removed[pos]) {
        this.removed[pos] = true;
        return;
      }
      pos = (pos + 1) % keyArr.length;
    }
  }

  public void clear() {
    size = 0;
    // noinspection unchecked
    this.keyArr = (K[]) new Object[this.keyArr.length];
    // noinspection unchecked
    this.valArr = (V[]) new Object[this.valArr.length];
    this.removed = new boolean[this.removed.length];
    Arrays.fill(this.removed, false);
  }

  public int getSize() {
    return size;
  }

}
