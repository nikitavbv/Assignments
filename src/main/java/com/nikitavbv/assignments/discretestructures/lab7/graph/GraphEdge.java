package com.nikitavbv.assignments.discretestructures.lab7.graph;

class GraphEdge {

  private int from;
  private int to;
  private int weight;

  GraphEdge(int from, int to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  int from() {
    return from;
  }

  int to() {
    return to;
  }

  int weight() {
    return weight;
  }
}
