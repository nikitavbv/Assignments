package com.nikitavbv.assignments.algorithms.lab4;

public class Graph {

  private double[][] adj;

  public Graph(double[][] adj) {
    this.adj = adj;
  }

  double[][] adjacencyMatrix() {
    return adj;
  }
}
