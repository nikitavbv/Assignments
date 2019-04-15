package com.nikitavbv.assignments.algorithms.lab2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

public class MyLinkedListHashTable<K, V> implements MyHashTableInterface<K, V> {

  private static final int DEFAULT_CAPACITY = 100;
  private static final boolean DEFAULT_RESIZABLE = true;

  private static final double LOAD_FACTOR_TO_RESIZE = 0.8;
  private static final int SCALE_FACTOR = 2;

  private Node front = null;

  private int size = 0;
  private int capacity = 0;
  private boolean resizable;

  private int totalCompares = 0;
  private int totalAccess = 0;

  private class Node {
    K key;
    V value;
    boolean removed = false;
    Node next;
  }

  MyLinkedListHashTable(int capacity, boolean resizable) {
    this.resizable = resizable;
    this.capacity = capacity;
  }

  MyLinkedListHashTable(boolean resizable) {
    this(DEFAULT_CAPACITY, resizable);
  }

  MyLinkedListHashTable() {
    this(DEFAULT_RESIZABLE);
  }

  public void put(K key, V value) {
    if (size >= capacity * LOAD_FACTOR_TO_RESIZE && resizable) {
      this.resize();
    }
    if (size == capacity) throw new MyHashTableFullException();

    int pos = Math.abs(key.hashCode()) % capacity;
    if (front == null) {
      front = new Node();
    }
    Node nodeAtPos = front;
    for (int i = 0; i < pos; i++) {
      if (nodeAtPos.next == null) {
        nodeAtPos.next = new Node();
      }
      nodeAtPos = nodeAtPos.next;
    }

    while (nodeAtPos.key != null && !nodeAtPos.removed) {
      pos = (pos + 1) % capacity;
      if (pos == 0) {
        nodeAtPos = front;
      } else {
        if (nodeAtPos.next == null) {
          nodeAtPos.next = new Node();
        }
        nodeAtPos = nodeAtPos.next;
      }
    }

    nodeAtPos.key = key;
    nodeAtPos.value = value;
    nodeAtPos.removed = false;

    size++;
  }

  private void resize() {
    MyLinkedListHashTable<K, V> temp = new MyLinkedListHashTable<>(this.capacity * SCALE_FACTOR, this.resizable);
    Node cursor = front;
    while (cursor != null) {
      if (!cursor.removed) {
        temp.put(cursor.key, cursor.value);
      }
      cursor = cursor.next;
    }

    this.front = temp.front;
    this.capacity = this.capacity * SCALE_FACTOR;
  }

  public Optional<V> get(K key) {
    Node cursor = front;
    while (cursor != null) {
      totalAccess++;
      totalCompares++;
      if (!cursor.removed && cursor.key.equals(key)) {
        return Optional.of(cursor.value);
      }
      cursor = cursor.next;
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
    Node cursor = front;
    while (cursor != null) {
      if (!cursor.removed && cursor.key.equals(key)) {
        cursor.removed = true;
      }
      cursor = cursor.next;
    }
  }

  public void clear() {
    size = 0;
    this.front = new Node();
  }

  public int getSize() {
    return size;
  }

  public void printTo(OutputStream out) throws IOException {
    out.write("[".getBytes());
    Node cursor = front;
    for (int i = 0; i < capacity; i++) {
      if (i > 0) {
        out.write(',');
      }
      if (cursor == null || cursor.removed) {
        continue;
      }
      out.write((cursor.key.toString() + ": " + cursor.value.toString()).getBytes());
      cursor = cursor.next;
    }
    out.write("]".getBytes());
    out.write('\n');
  }

  public int getTotalCompares() {
    return totalCompares;
  }

  public int getTotalAccess() {
    return totalAccess;
  }

}
