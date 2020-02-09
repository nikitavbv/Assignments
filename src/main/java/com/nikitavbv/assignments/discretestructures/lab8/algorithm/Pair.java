package com.nikitavbv.assignments.discretestructures.lab8.algorithm;

import java.util.Objects;

public class Pair<A, B> {

  private final A first;
  private final B second;

  Pair(A first, B second) {
    this.first = first;
    this.second = second;
  }

  A first() {
    return first;
  }

  B second() {
    return second;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Pair)) return false;
    Pair<?, ?> pair = (Pair<?, ?>) o;
    return first.equals(pair.first) &&
            second.equals(pair.second);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second);
  }
}
