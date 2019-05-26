package com.nikitavbv.assignments.algorithms.lab5;

import java.util.HashSet;
import java.util.Set;

public class Graph {

  private int[][] adjacencyMatrix;

  public Graph(int[][] adjacencyMatrix) {
    this.adjacencyMatrix = adjacencyMatrix;
  }

  public Set<Integer> connectedNodes(int node) {
    Set<Integer> result = new HashSet<>();
    for (int i = 0; i < nodeCount(); i++) {
      if (adjacencyMatrix[node][i] != 0) {
        result.add(i);
      }
    }
    return result;
  }

  public int[][] getAdjacencyMatrix() {
    return adjacencyMatrix;
  }

  public int nodeCount() {
    return adjacencyMatrix.length;
  }

}
