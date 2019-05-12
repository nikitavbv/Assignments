package com.nikitavbv.assignments.discretestructures.lab7.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BellmanFordAlgorithm implements GraphAlgorithm {

  private int[] predecessor;

  @Override
  public String name() {
    return "Bellman-Ford algorithm";
  }

  @Override
  public void findAllPaths(Graph graph) {
    for (int i = 0; i < graph.totalNodes(); i++) {
      calculateDistances(graph, i);
    }
  }

  @Override
  public List<Integer> findShortestPath(Graph graph, int from, int to, boolean traceRoute) {
    calculateDistances(graph, from);

    if (!traceRoute) {
      return Collections.emptyList();
    }

    List<Integer> route = new ArrayList<>();
    int cursor = to;
    while (cursor != from) {
      route.add(0, cursor);
      cursor = predecessor[cursor];
    }
    route.add(0, from);

    return route;
  }

  private void calculateDistances(Graph graph, int from) {
    int[][] adjacencyMatrix = graph.adjacencyMatrix();
    int[] distanceTo = new int[adjacencyMatrix.length];
    predecessor = new int[adjacencyMatrix.length];
    Arrays.fill(distanceTo, Integer.MAX_VALUE);
    Arrays.fill(predecessor, -1);

    distanceTo[from] = 0;

    Queue<Integer> verticesToRelax = new LinkedList<>();
    verticesToRelax.add(from);
    while (!verticesToRelax.isEmpty()) {
      int v = verticesToRelax.poll();
      for (GraphEdge e : graph.getEdgesByNode(v)) {
        int w = e.to();
        if (distanceTo[w] > distanceTo[v] + e.weight()) {
          distanceTo[w] = distanceTo[v] + e.weight();
          // edgeTo[w] = e
          predecessor[w] = e.from();
          if (!verticesToRelax.contains(w)) {
            verticesToRelax.add(w);
          }
        }
      }
    }

    /*for (int verticle = 0; verticle < adjacencyMatrix.length; verticle++) {
      for (GraphEdge edge : graph.edges()) {
        int u = edge.from();
        int v = edge.to();
        if (distanceTo[u] + adjacencyMatrix[u][v] < distanceTo[v] && distanceTo[u] != Integer.MAX_VALUE) {
          distanceTo[v] = distanceTo[u] + adjacencyMatrix[u][v];
          predecessor[v] = u;
        }
      }
    }*/

    /*for (int u = 0; u < adjacencyMatrix.length; u++) {
      for (int v = 0; v < adjacencyMatrix[u].length; v++) {
        if (u == v || adjacencyMatrix[u][v] == -1) {
          continue;
        }

        if (distanceTo[u] + adjacencyMatrix[u][v] < distanceTo[v]) {
          // throw new AssertionError("Negative weight cycle detected!");
        }
      }
    }*/
  }
}
