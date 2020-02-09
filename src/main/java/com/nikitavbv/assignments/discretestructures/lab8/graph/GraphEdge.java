package com.nikitavbv.assignments.discretestructures.lab8.graph;

import java.util.Objects;

public class GraphEdge {

  private final int from;
  private final int to;
  private final int capacity;

  public GraphEdge(int from, int to) {
    this(from, to, 0);
  }

  public GraphEdge(int from, int to, int capacity) {
    this.from = from;
    this.to = to;
    this.capacity = capacity;
  }

  public int capacity() {
    return capacity;
  }

  public int to() {
    return to;
  }

  public int from() {
    return from;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GraphEdge)) return false;
    GraphEdge graphEdge = (GraphEdge) o;
    return from == graphEdge.from &&
            to == graphEdge.to;
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to);
  }

  @Override
  public String toString() {
    return "GraphEdge{" +
            "from=" + from +
            ", to=" + to +
            ", capacity=" + capacity +
            '}';
  }
}
