package com.nikitavbv.assignments.discretestructures.lab7.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {

  private int[][] adjacencyMatrix;
  private GraphEdge[] edges;
  private List<List<GraphEdge>> adj;

  public Graph(int[][] adjacencyMatrix) {
    this.adjacencyMatrix = adjacencyMatrix;
    this.edges = makeEdgeList().toArray(new GraphEdge[]{});
    this.adj = new ArrayList<>();
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      final int from = i;
      adj.add(Arrays.stream(edges).filter(edge -> edge.from() == from).collect(Collectors.toList()));
    }
  }

  private List<GraphEdge> makeEdgeList() {
    List<GraphEdge> result = new ArrayList<>();
    for (int i = 0; i < totalNodes(); i++) {
      for (int j = 0; j < totalNodes(); j++) {
        if (i == j) {
          continue;
        }

        if (adjacencyMatrix[i][j] != -1) {
          result.add(new GraphEdge(i, j, adjacencyMatrix[i][j]));
        }
      }
    }
    return result;
  }

  public int calcPathLength(List<Integer> route) {
    if (route.size() == 0) {
      return 0;
    }

    int dist = 0;
    int prevNode = route.get(0);
    for (int node : route) {
      if (adjacencyMatrix[prevNode][node] == -1) {
        throw new AssertionError("No edge between " + prevNode + " " + node + ", route: "
                + Arrays.toString(route.toArray()));
      }
      dist += adjacencyMatrix[prevNode][node];
      prevNode = node;
    }
    return dist;
  }

  public GraphEdge[] edges() {
    return edges;
  }

  List<GraphEdge> getEdgesByNode(int node) {
    return adj.get(node);
  }

  public int totalNodes() {
    return adjacencyMatrix().length;
  }

  int[][] adjacencyMatrix() {
    return adjacencyMatrix;
  }
}
