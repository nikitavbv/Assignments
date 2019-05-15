package com.nikitavbv.assignments.algorithms.lab3.btree;

public class Pair<A, B> {

  private final A key;
  private final B value;

  Pair(A key, B value) {
    this.key = key;
    this.value = value;
  }

  public A key() {
    return key;
  }

  public B value() {
    return value;
  }
}
