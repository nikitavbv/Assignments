package com.nikitavbv.assignments.algorithms.lab3.btree;

public class KeyNotFoundException extends RuntimeException {

  private final int key;

  public KeyNotFoundException(int key) {
    this.key = key;
  }
}
