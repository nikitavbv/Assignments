package com.nikitavbv.assignments.algorithms.lab2;

import java.util.Optional;

public interface MyHashTableInterface<K, V> {

  public void put(K key, V value);
  public Optional<V> get(K key);
  public boolean containsKey(K key);
  public V getOrDefault(K key, V defaultValue);
  public void remove(K key);
  public void clear();
  public int getSize();

  int getTotalCompares();
  int getTotalAccess();

}
